package io.github.bluesheep2804.mekanicalcreativity.registries.registrate;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import io.github.bluesheep2804.mekanicalcreativity.block.infuser.MechanicalInfuserBlockEntity;
import io.github.bluesheep2804.mekanicalcreativity.block.infuser.MechanicalInfuserBlockRenderer;
import io.github.bluesheep2804.mekanicalcreativity.block.infuser.MechanicalInfuserBlockVisual;

import static io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativity.REGISTRATE;

public class MekCreCreateBlockEntities {
    public static final BlockEntityEntry<MechanicalInfuserBlockEntity> MECHANICAL_INFUSER = REGISTRATE
            .blockEntity("mechanical_infuser", MechanicalInfuserBlockEntity::new)
            .visual(() -> MechanicalInfuserBlockVisual::new)
            .validBlocks(MekCreCreateBlocks.MECHANICAL_INFUSER)
            .renderer(() -> MechanicalInfuserBlockRenderer::new)
            .register();

//    public static final BlockEntityEntry<InfuseExtractorBlockEntity> BASIC_INFUSE_EXTRACTOR = infuseExtractor(MekCreBlocks.BASIC_INFUSE_EXTRACTOR, InfuseExtractorBlockEntity::basic);
//    public static final BlockEntityEntry<InfuseExtractorBlockEntity> ADVANCED_INFUSE_EXTRACTOR = infuseExtractor(MekCreBlocks.ADVANCED_INFUSE_EXTRACTOR, InfuseExtractorBlockEntity::advanced);
//    public static final BlockEntityEntry<InfuseExtractorBlockEntity> ELITE_INFUSE_EXTRACTOR = infuseExtractor(MekCreBlocks.ELITE_INFUSE_EXTRACTOR, InfuseExtractorBlockEntity::elite);
//    public static final BlockEntityEntry<InfuseExtractorBlockEntity> ULTIMATE_INFUSE_EXTRACTOR = infuseExtractor(MekCreBlocks.ULTIMATE_INFUSE_EXTRACTOR, InfuseExtractorBlockEntity::ultimate);
//    public static final BlockEntityEntry<InfuseExtractorBlockEntity> CREATIVE_INFUSE_EXTRACTOR = infuseExtractor(MekCreBlocks.CREATIVE_INFUSE_EXTRACTOR, InfuseExtractorBlockEntity::creative);
//
//    public static BlockEntityEntry<InfuseExtractorBlockEntity> infuseExtractor(BlockEntry<InfuseExtractorBlock> block, BlockEntityBuilder.BlockEntityFactory<InfuseExtractorBlockEntity> factory) {
//        return REGISTRATE
//                .blockEntity(block.getId().getPath(), factory)
//                .validBlocks(block)
//                .register();
//    }

    public static void register() {}
}
