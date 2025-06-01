package io.github.bluesheep2804.mekanicalcreativity.registries;

import io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativityLang;
import io.github.bluesheep2804.mekanicalcreativity.block.extractor.InfuseExtractorBlockEntity;
import io.github.bluesheep2804.mekanicalcreativity.content.blocktype.BlockShapes;
import io.github.bluesheep2804.mekanicalcreativity.content.blocktype.MekCreMachine;
import io.github.bluesheep2804.mekanicalcreativity.tier.InfuseExtractorTier;
import mekanism.common.block.attribute.AttributeTier;
import mekanism.common.block.attribute.AttributeUpgradeSupport;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;

import java.util.function.Supplier;

public class MekCreBlockTypes {
    public static final MekCreMachine<InfuseExtractorBlockEntity> BASIC_INFUSE_EXTRACTOR = createInfuseExtractor(InfuseExtractorTier.BASIC, () -> MekCreBlockEntityTypes.BASIC_INFUSE_EXTRACTOR);
    public static final MekCreMachine<InfuseExtractorBlockEntity> ADVANCED_INFUSE_EXTRACTOR = createInfuseExtractor(InfuseExtractorTier.ADVANCED, () -> MekCreBlockEntityTypes.ADVANCED_INFUSE_EXTRACTOR);
    public static final MekCreMachine<InfuseExtractorBlockEntity> ELITE_INFUSE_EXTRACTOR = createInfuseExtractor(InfuseExtractorTier.ELITE, () -> MekCreBlockEntityTypes.ELITE_INFUSE_EXTRACTOR);
    public static final MekCreMachine<InfuseExtractorBlockEntity> ULTIMATE_INFUSE_EXTRACTOR = createInfuseExtractor(InfuseExtractorTier.ULTIMATE, () -> MekCreBlockEntityTypes.ULTIMATE_INFUSE_EXTRACTOR);
    public static final MekCreMachine<InfuseExtractorBlockEntity> CREATIVE_INFUSE_EXTRACTOR = createInfuseExtractor(InfuseExtractorTier.CREATIVE, () -> MekCreBlockEntityTypes.CREATIVE_INFUSE_EXTRACTOR);

    private static <TILE extends InfuseExtractorBlockEntity> MekCreMachine<TILE> createInfuseExtractor(InfuseExtractorTier tier, Supplier<TileEntityTypeRegistryObject<TILE>> tile) {
        return MekCreMachine.MachineBuilder
                .createMachine(tile, MekanicalCreativityLang.DESCRIPTION_INFUSE_EXTRACTOR)
                .with(new AttributeTier<>(tier))
                .withGui(() -> MekCreContainerTypes.INFUSE_EXTRACTOR)
                .without(AttributeUpgradeSupport.class)
                .withCustomShape(BlockShapes.INFUSE_EXTRACTOR)
                .build();
    }
}
