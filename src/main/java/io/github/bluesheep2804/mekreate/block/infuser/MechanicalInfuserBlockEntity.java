package io.github.bluesheep2804.mekreate.block.infuser;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour;
import com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour.ProcessingResult;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour.TransportedResult;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.infuse.IInfusionHandler;
import mekanism.api.chemical.infuse.IInfusionTank;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.recipes.MetallurgicInfuserRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour.ProcessingResult.HOLD;
import static com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour.ProcessingResult.PASS;

public class MechanicalInfuserBlockEntity extends KineticBlockEntity {
    public IInfusionTank inputInfusionTank;
    public LazyOptional<IInfusionHandler> infusionCapability;
    private BeltProcessingBehaviour beltProcessing;
    public int processingTicks;
    private float previousSpeed = 0;
    private final RecipeType<MetallurgicInfuserRecipe> metallurgicRecipeType = (RecipeType<MetallurgicInfuserRecipe>) ForgeRegistries.RECIPE_TYPES.getValue(ResourceLocation.fromNamespaceAndPath("mekanism", "metallurgic_infusing"));

    public MechanicalInfuserBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        inputInfusionTank = ChemicalTankBuilder.INFUSION.input(1000, infuseType -> true, this::setChanged);
        infusionCapability = LazyOptional.of(() -> (IInfusionHandler) inputInfusionTank);
        processingTicks = -1;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviors) {
        beltProcessing = new BeltProcessingBehaviour(this)
                .whenItemEnters(this::onItemReceived)
                .whileItemHeld(this::whenItemHeld);
        behaviors.add(beltProcessing);
    }

    public ProcessingResult onItemReceived(TransportedItemStack transported, TransportedItemStackHandlerBehaviour handler) {
        if (isVirtual())
            return PASS;
        Optional<MetallurgicInfuserRecipe> recipe = getRecipe(transported.stack, inputInfusionTank.getStack());
        if (recipe.isEmpty())
            return PASS;
        if (inputInfusionTank.isEmpty())
            return HOLD;
        if (!recipe.get().getChemicalInput().test(inputInfusionTank.getStack()))
            return PASS;
        processingTicks = -1;
        return HOLD;
    }

    public ProcessingResult whenItemHeld(TransportedItemStack transported, TransportedItemStackHandlerBehaviour handler) {
        if (processingTicks > 0)
            return HOLD;
        if (isVirtual())
            return PASS;
        Optional<MetallurgicInfuserRecipe> recipe = getRecipe(transported.stack, inputInfusionTank.getStack());
        if (recipe.isEmpty())
            return PASS;
        if (inputInfusionTank.isEmpty())
            return HOLD;

        InfusionStack infusionStack = inputInfusionTank.getStack();
        if (!recipe.get().getChemicalInput().test(infusionStack))
            return HOLD;

        if (processingTicks == -1) {
            processingTicks = calculateProcessingSpeed();
            notifyUpdate();
            return HOLD;
        }

        ItemStack output = recipe.get().getOutput(transported.stack, infusionStack);
        if (!output.isEmpty()) {
            transported.clearFanProcessingData();
            List<TransportedItemStack> outputList = new ArrayList<>();
            TransportedItemStack held = null;
            TransportedItemStack result = transported.copy();
            result.stack = output;
            if (!transported.stack.isEmpty()) {
                held = transported.copy();
                held.stack.shrink((int) recipe.get().getItemInput().getNeededAmount(held.stack));
            }
            outputList.add(result);
            handler.handleProcessingOnItem(
                    transported,
                    TransportedResult.convertToAndLeaveHeld(outputList, held)
            );
            infusionStack.shrink(recipe.get().getChemicalInput().getNeededAmount(infusionStack));
        }

        notifyUpdate();
        return HOLD;
    }

    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide) return;

        if (getSpeed() == 0)
            return;

        if (processingTicks >= 0) {
            processingTicks--;
        }
        notifyUpdate();
    }

    @Override
    public void remove() {
        infusionCapability.invalidate();
        super.remove();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        compound.putInt("ProcessingTicks", processingTicks);
        compound.put("InputInfusionTank", inputInfusionTank.serializeNBT());
        super.write(compound, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        processingTicks = compound.getInt("ProcessingTicks");
        inputInfusionTank.deserializeNBT(compound.getCompound("InputInfusionTank"));
        super.read(compound, clientPacket);
    }

    public int calculateProcessingSpeed(float speed) {
        return (int) (128 / Math.abs(speed) * 20);
    }

    public int calculateProcessingSpeed() {
        return calculateProcessingSpeed(getSpeed());
    }

    @Override
    public float calculateStressApplied() {
        return 8;
    }

    @Override
    public void onSpeedChanged(float prevSpeed) {
        if (processingTicks == -1) return;
        if (getSpeed() == 0) {
            previousSpeed = prevSpeed;
        } else {
            float rate = Math.abs(previousSpeed) / Math.abs(getSpeed());
            processingTicks = (int) Mth.clamp(processingTicks / rate, 1, calculateProcessingSpeed());
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityManager.get(new CapabilityToken<IInfusionHandler>() {}) && side != Direction.DOWN)
            return infusionCapability.cast();
        return super.getCapability(cap, side);
    }

    private Optional<MetallurgicInfuserRecipe> getRecipe(ItemStack inputItem, InfusionStack inputInfusionStack) {
        List<MetallurgicInfuserRecipe> metallurgicInfuserRecipes = Objects.requireNonNull(level).getRecipeManager()
                .getAllRecipesFor(metallurgicRecipeType);
        Optional<MetallurgicInfuserRecipe> recipe = metallurgicInfuserRecipes.stream()
                .filter(it -> it.test(inputItem, inputInfusionStack))
                .findFirst();
        return recipe;
    }
}
