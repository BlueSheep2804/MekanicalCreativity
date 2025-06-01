package io.github.bluesheep2804.mekanicalcreativity.block.extractor;

import io.github.bluesheep2804.mekanicalcreativity.mixin.InfusionInventorySlotMixin;
import io.github.bluesheep2804.mekanicalcreativity.tier.InfuseExtractorTier;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.infuse.IInfusionTank;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.recipes.ItemStackToInfuseTypeRecipe;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.cache.OneInputCachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.prefab.BlockTile;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.slot.InputInventorySlot;
import mekanism.common.inventory.slot.chemical.InfusionInventorySlot;
import mekanism.common.inventory.warning.WarningTracker;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.ISingleRecipeLookupHandler.ItemRecipeLookupHandler;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.tile.component.TileComponentConfig;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.prefab.TileEntityProgressMachine;
import mekanism.common.util.MekanismUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class InfuseExtractorBlockEntity extends TileEntityProgressMachine<ItemStackToInfuseTypeRecipe> implements ItemRecipeLookupHandler<ItemStackToInfuseTypeRecipe> {
    private static final List<RecipeError> TRACKED_ERRORS = List.of(
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.NOT_ENOUGH_INPUT
    );

    public IInfusionTank infusionTank;
    private final IOutputHandler<@NotNull InfusionStack> outputHandler;
    private final IInputHandler<@NotNull ItemStack> inputHandler;

    InputInventorySlot inputSlot;
    InfusionInventorySlot outputSlot;

    public InfuseExtractorBlockEntity(BlockRegistryObject<BlockTile.BlockTileModel<InfuseExtractorBlockEntity, BlockTypeTile<InfuseExtractorBlockEntity>>, InfuseExtractorItemBlock> block, BlockPos pos, BlockState state) {
        super(block, pos, state, TRACKED_ERRORS, 1000);
        InfuseExtractorTier tier = Attribute.getTier(block, InfuseExtractorTier.class);
        baseTicksRequired = tier.getProcessingTicks();
        ticksRequired = baseTicksRequired;

        configComponent = new TileComponentConfig(this, TransmissionType.ITEM, TransmissionType.INFUSION, TransmissionType.ENERGY);
        configComponent.setupInputConfig(TransmissionType.ITEM, inputSlot);
        configComponent.setupOutputConfig(TransmissionType.ITEM, outputSlot, RelativeSide.BOTTOM);
        configComponent.setupOutputConfig(TransmissionType.INFUSION, infusionTank, RelativeSide.BOTTOM);

        ejectorComponent = new TileComponentEjector(this);
        ejectorComponent.setOutputData(configComponent, TransmissionType.INFUSION);

        inputHandler = InputHelper.getInputHandler(inputSlot, RecipeError.NOT_ENOUGH_INPUT);
        outputHandler = OutputHelper.getOutputHandler(infusionTank, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
    }

    @NotNull
    @Override
    public IChemicalTankHolder<InfuseType, InfusionStack, IInfusionTank> getInitialInfusionTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        ChemicalTankHelper<InfuseType, InfusionStack, IInfusionTank> builder = ChemicalTankHelper.forSideInfusionWithConfig(this::getDirection, this::getConfig);
        builder.addTank(infusionTank = ChemicalTankBuilder.INFUSION.output(1000, listener));
        return builder.build();
    }

    @NotNull
    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener, IContentsListener recipeCacheListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this::getDirection, this::getConfig);
        builder.addSlot(inputSlot = InputInventorySlot.at(this::containsRecipe, recipeCacheListener, 26, 36))
                .tracksWarnings(slot -> slot.warning(WarningTracker.WarningType.NO_MATCHING_RECIPE, getWarningCheck(RecipeError.NOT_ENOUGH_INPUT)));
        Predicate<ItemStack> insertPredicate = InfusionInventorySlot.getDrainInsertPredicate(infusionTank, InfusionInventorySlot::getCapability);
        builder.addSlot(outputSlot = InfusionInventorySlotMixin.init(infusionTank, () -> null, insertPredicate.negate(), insertPredicate, (stack) -> stack.getCapability(Capabilities.INFUSION_HANDLER).isPresent(), listener, 152, 56));
        outputSlot.setSlotOverlay(SlotOverlay.PLUS);
        return builder.build();
    }

    @Override
    protected void onUpdateServer() {
        super.onUpdateServer();
        outputSlot.drainTank();
        recipeCacheLookupMonitor.updateAndProcess();
    }

    @NotNull
    @Override
    public IMekanismRecipeTypeProvider<ItemStackToInfuseTypeRecipe, InputRecipeCache.SingleItem<ItemStackToInfuseTypeRecipe>> getRecipeType() {
        return MekanismRecipeType.INFUSION_CONVERSION;
    }

    @Nullable
    @Override
    public ItemStackToInfuseTypeRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(inputHandler);
    }

    @NotNull
    @Override
    public CachedRecipe<ItemStackToInfuseTypeRecipe> createNewCachedRecipe(@NotNull ItemStackToInfuseTypeRecipe recipe, int cacheIndex) {
        return OneInputCachedRecipe.itemToChemical(recipe, recheckAllRecipeErrors, inputHandler, outputHandler)
                .setErrorsChanged(this::onErrorsChanged)
                .setCanHolderFunction(() -> MekanismUtils.canFunction(this))
                .setActive(this::setActive)
                .setRequiredTicks(this::getTicksRequired)
                .setOnFinish(this::markForSave)
                .setOperatingTicksChanged(this::setOperatingTicks);
    }
}
