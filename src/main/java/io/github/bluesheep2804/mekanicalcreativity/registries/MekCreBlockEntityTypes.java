package io.github.bluesheep2804.mekanicalcreativity.registries;

import io.github.bluesheep2804.mekanicalcreativity.block.extractor.InfuseExtractorBlockEntity;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;

import static io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativity.MODID;

public class MekCreBlockEntityTypes {
    public static final TileEntityTypeDeferredRegister TILE_ENTITY_TYPES = new TileEntityTypeDeferredRegister(MODID);

    public static final TileEntityTypeRegistryObject<InfuseExtractorBlockEntity> BASIC_INFUSE_EXTRACTOR = TILE_ENTITY_TYPES
            .register(MekCreBlocks.BASIC_INFUSE_EXTRACTOR, (pos, state) -> new InfuseExtractorBlockEntity(MekCreBlocks.BASIC_INFUSE_EXTRACTOR, pos, state));
    public static final TileEntityTypeRegistryObject<InfuseExtractorBlockEntity> ADVANCED_INFUSE_EXTRACTOR = TILE_ENTITY_TYPES
            .register(MekCreBlocks.ADVANCED_INFUSE_EXTRACTOR, (pos, state) -> new InfuseExtractorBlockEntity(MekCreBlocks.ADVANCED_INFUSE_EXTRACTOR, pos, state));
    public static final TileEntityTypeRegistryObject<InfuseExtractorBlockEntity> ELITE_INFUSE_EXTRACTOR = TILE_ENTITY_TYPES
            .register(MekCreBlocks.ELITE_INFUSE_EXTRACTOR, (pos, state) -> new InfuseExtractorBlockEntity(MekCreBlocks.ELITE_INFUSE_EXTRACTOR, pos, state));
    public static final TileEntityTypeRegistryObject<InfuseExtractorBlockEntity> ULTIMATE_INFUSE_EXTRACTOR = TILE_ENTITY_TYPES
            .register(MekCreBlocks.ULTIMATE_INFUSE_EXTRACTOR, (pos, state) -> new InfuseExtractorBlockEntity(MekCreBlocks.ULTIMATE_INFUSE_EXTRACTOR, pos, state));
    public static final TileEntityTypeRegistryObject<InfuseExtractorBlockEntity> CREATIVE_INFUSE_EXTRACTOR = TILE_ENTITY_TYPES
            .register(MekCreBlocks.CREATIVE_INFUSE_EXTRACTOR, (pos, state) -> new InfuseExtractorBlockEntity(MekCreBlocks.CREATIVE_INFUSE_EXTRACTOR, pos, state));
}
