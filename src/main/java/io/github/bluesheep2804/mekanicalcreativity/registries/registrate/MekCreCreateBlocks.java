package io.github.bluesheep2804.mekanicalcreativity.registries.registrate;

import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.bluesheep2804.mekanicalcreativity.block.infuser.MechanicalInfuserBlock;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativity.REGISTRATE;

public class MekCreCreateBlocks {
    public static final BlockEntry<MechanicalInfuserBlock> MECHANICAL_INFUSER = REGISTRATE
            .block("mechanical_infuser", MechanicalInfuserBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p
                    .noOcclusion()
                    .mapColor(MapColor.WOOD)
            )
            .transform(axeOrPickaxe())
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .item(AssemblyOperatorBlockItem::new)
            .transform(customItemModel())
            .register();

//    public static final BlockEntry<InfuseExtractorBlock> BASIC_INFUSE_EXTRACTOR = infuseExtractor(InfuseExtractorTier.BASIC, InfuseExtractorBlock::basic);
//    public static final BlockEntry<InfuseExtractorBlock> ADVANCED_INFUSE_EXTRACTOR = infuseExtractor(InfuseExtractorTier.ADVANCED, InfuseExtractorBlock::advanced);
//    public static final BlockEntry<InfuseExtractorBlock> ELITE_INFUSE_EXTRACTOR = infuseExtractor(InfuseExtractorTier.ELITE, InfuseExtractorBlock::elite);
//    public static final BlockEntry<InfuseExtractorBlock> ULTIMATE_INFUSE_EXTRACTOR = infuseExtractor(InfuseExtractorTier.ULTIMATE, InfuseExtractorBlock::ultimate);
//    public static final BlockEntry<InfuseExtractorBlock> CREATIVE_INFUSE_EXTRACTOR = infuseExtractor(InfuseExtractorTier.CREATIVE, InfuseExtractorBlock::creative);

    public static final BlockEntry<CasingBlock> OSMIUM_CASING = REGISTRATE
            .block("osmium_casing", CasingBlock::new)
            .properties(p -> p.mapColor(MapColor.WOOD))
            .transform(BuilderTransformers.casing(() -> MekCreCreateSpriteShifts.OSMIUM_CASING))
            .register();

//    public static BlockEntry<InfuseExtractorBlock> infuseExtractor(InfuseExtractorTier tier, NonNullFunction<BlockBehaviour.Properties, InfuseExtractorBlock> factory) {
//        return REGISTRATE
//                .block(tier.getBaseTier().getLowerName() + "_infuse_extractor", factory)
//                .initialProperties(SharedProperties::stone)
//                .properties(p -> p
//                        .noOcclusion()
//                        .mapColor(MapColor.WOOD)
//                )
//                .transform(pickaxeOnly())
//                .blockstate(infuseExtractorBlockstate(tier.getBaseTier().getLowerName()))
//                .item(InfuseExtractorItemBlock::new)
//                .transform(b -> b.model(MekCreBlocks::infuseExtractorItemModel).build())
//                .register();
//    }

//    private static NonNullBiConsumer<DataGenContext<Block, InfuseExtractorBlock>, RegistrateBlockstateProvider> infuseExtractorBlockstate(String tier) {
//        return (ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models().getExistingFile(prov.modLoc("block/infuse_extractor/" + tier)));
//    }
//
//    private static ItemModelBuilder infuseExtractorItemModel(DataGenContext<Item, InfuseExtractorItemBlock> ctx, RegistrateItemModelProvider prov) {
//        return prov.withExistingParent(prov.name(() -> ctx.get().asItem()), rl("block/infuse_extractor/" + ctx.get().getTier().getBaseTier().getLowerName()));
//    }

    public static void register() {}
}
