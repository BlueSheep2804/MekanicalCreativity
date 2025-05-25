package io.github.bluesheep2804.mekanicalcreativity.block.extractor;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import io.github.bluesheep2804.mekanicalcreativity.tier.InfuseExtractorTier;
import mekanism.api.Action;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.infuse.IInfusionHandler;
import mekanism.api.chemical.infuse.IInfusionTank;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.recipes.ItemStackToInfuseTypeRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InfuseExtractorBlockEntity extends SmartBlockEntity {
    public InfuseExtractorTier tier;
    int processingTicks = -1;
    RecipeType<ItemStackToInfuseTypeRecipe> infusionConversionRecipeType = (RecipeType<ItemStackToInfuseTypeRecipe>) ForgeRegistries.RECIPE_TYPES.getValue(
            ResourceLocation.fromNamespaceAndPath("mekanism", "infusion_conversion")
    );
    public ItemStackHandler inventory;
    public LazyOptional<IItemHandler> capability;
    public IInfusionTank infusionTank;
    public LazyOptional<IInfusionHandler> infusionCapability;

    private InfuseExtractorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, InfuseExtractorTier tier) {
        super(type, pos, state);
        this.tier = tier;
        inventory = new ItemStackHandler(1);
        capability = LazyOptional.of(() -> inventory);
        infusionTank = ChemicalTankBuilder.INFUSION.input(2000, infuseType -> true, this::setChanged);
        infusionCapability = LazyOptional.of(InfusionHandler::new);
    }

    public static InfuseExtractorBlockEntity basic(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        return new InfuseExtractorBlockEntity(type, pos, state, InfuseExtractorTier.BASIC);
    }

    public static InfuseExtractorBlockEntity advanced(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        return new InfuseExtractorBlockEntity(type, pos, state, InfuseExtractorTier.ADVANCED);
    }

    public static InfuseExtractorBlockEntity elite(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        return new InfuseExtractorBlockEntity(type, pos, state, InfuseExtractorTier.ELITE);
    }

    public static InfuseExtractorBlockEntity ultimate(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        return new InfuseExtractorBlockEntity(type, pos, state, InfuseExtractorTier.ULTIMATE);
    }

    public static InfuseExtractorBlockEntity creative(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        return new InfuseExtractorBlockEntity(type, pos, state, InfuseExtractorTier.CREATIVE);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    @Override
    public void tick() {
        super.tick();

        if (getRecipe().isPresent()) {
            if (processingTicks < 0)
                processingTicks = tier.getProcessingTicks();

            processingTicks--;
        } else
            processingTicks = -1;

        if (processingTicks == 0)
            process();

        // 自動搬出
        if (!infusionTank.isEmpty())
            autoExtract();
    }

    private void process() {
        Optional<ItemStackToInfuseTypeRecipe> recipe = getRecipe();
        if (recipe.isEmpty())
            return;

        InfusionStack output = recipe.get().getOutput(inventory.getStackInSlot(0));

        if (!infusionTank.isEmpty() && !infusionTank.isTypeEqual(output))  // 同じ気体か否か
            return;

        if (infusionTank.getStored() + output.getAmount() > infusionTank.getCapacity())  // 容量オーバー
            return;

        if (infusionTank.isEmpty())
            infusionTank.setStack(output);
        else
            infusionTank.growStack(output.getAmount(), Action.EXECUTE);

        inventory.getStackInSlot(0).shrink(1);
    }

    private void autoExtract() {
        BlockPos below = worldPosition.below();
        var be = Objects.requireNonNull(level).getBlockEntity(below);
        if (be == null)
            return;

        be.getCapability(CapabilityManager.get(new CapabilityToken<IInfusionHandler>() {}), Direction.UP)
                .ifPresent(target -> {
                    InfusionStack remaining = target.insertChemical(infusionTank.getStack(), Action.EXECUTE);
                    if (remaining.isEmpty()) {
                        infusionTank.setEmpty();
                    } else {
                        infusionTank.setStack(remaining);
                    }
                });
    }

    @Override
    public void invalidate() {
        super.invalidate();
        infusionCapability.invalidate();
    }

    @Override
    public void remove() {
        super.remove();
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        compound.putInt("ProcessingTicks", processingTicks);
        compound.put("Inventory", inventory.serializeNBT());
        compound.put("InfusionTank", infusionTank.serializeNBT());
        super.write(compound, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        processingTicks = compound.getInt("ProcessingTicks");
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        infusionTank.deserializeNBT(compound.getCompound("InfusionTank"));
        super.read(compound, clientPacket);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (isItemHandlerCap(cap))
            return capability.cast();

        if (cap == CapabilityManager.get(new CapabilityToken<IInfusionHandler>() {}))
            return infusionCapability.cast();
        return super.getCapability(cap, side);
    }

    private Optional<ItemStackToInfuseTypeRecipe> getRecipe() {
        return getRecipe(inventory.getStackInSlot(0));
    }

    public Optional<ItemStackToInfuseTypeRecipe> getRecipe(ItemStack input) {
        List<ItemStackToInfuseTypeRecipe> infusionConversionRecipes = level.getRecipeManager()
                .getAllRecipesFor(infusionConversionRecipeType);
        Optional<ItemStackToInfuseTypeRecipe> recipe = infusionConversionRecipes.stream()
                .filter(it -> it.test(input))
                .findFirst();
        return recipe;
    }

    private class InfusionHandler implements IInfusionHandler.IMekanismInfusionHandler {
        @NotNull
        @Override
        public List<IInfusionTank> getChemicalTanks(@Nullable Direction side) {
            return List.of(infusionTank);
        }

        @Override
        public void onContentsChanged() {}
    }
}
