package io.github.bluesheep2804.mekanicalcreativity.registries;

import io.github.bluesheep2804.mekanicalcreativity.block.extractor.InfuseExtractorBlockEntity;
import io.github.bluesheep2804.mekanicalcreativity.block.extractor.InfuseExtractorItemBlock;
import mekanism.api.tier.ITier;
import mekanism.common.block.attribute.AttributeTier;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.resource.BlockResourceInfo;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativity.MODID;

public class MekCreBlocks {
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(MODID);

    public static final BlockRegistryObject<BlockTileModel<InfuseExtractorBlockEntity, BlockTypeTile<InfuseExtractorBlockEntity>>, InfuseExtractorItemBlock> BASIC_INFUSE_EXTRACTOR = registerInfuseExtractor(MekCreBlockTypes.BASIC_INFUSE_EXTRACTOR);
    public static final BlockRegistryObject<BlockTileModel<InfuseExtractorBlockEntity, BlockTypeTile<InfuseExtractorBlockEntity>>, InfuseExtractorItemBlock> ADVANCED_INFUSE_EXTRACTOR = registerInfuseExtractor(MekCreBlockTypes.ADVANCED_INFUSE_EXTRACTOR);
    public static final BlockRegistryObject<BlockTileModel<InfuseExtractorBlockEntity, BlockTypeTile<InfuseExtractorBlockEntity>>, InfuseExtractorItemBlock> ELITE_INFUSE_EXTRACTOR = registerInfuseExtractor(MekCreBlockTypes.ELITE_INFUSE_EXTRACTOR);
    public static final BlockRegistryObject<BlockTileModel<InfuseExtractorBlockEntity, BlockTypeTile<InfuseExtractorBlockEntity>>, InfuseExtractorItemBlock> ULTIMATE_INFUSE_EXTRACTOR = registerInfuseExtractor(MekCreBlockTypes.ULTIMATE_INFUSE_EXTRACTOR);
    public static final BlockRegistryObject<BlockTileModel<InfuseExtractorBlockEntity, BlockTypeTile<InfuseExtractorBlockEntity>>, InfuseExtractorItemBlock> CREATIVE_INFUSE_EXTRACTOR = registerInfuseExtractor(MekCreBlockTypes.CREATIVE_INFUSE_EXTRACTOR);

    private static BlockRegistryObject<BlockTileModel<InfuseExtractorBlockEntity, BlockTypeTile<InfuseExtractorBlockEntity>>, InfuseExtractorItemBlock> registerInfuseExtractor(BlockTypeTile<InfuseExtractorBlockEntity> type) {
        ITier tier = type.get(AttributeTier.class).tier();
        return registerTieredBlock(
                tier,
                "_infuse_extractor",
                () -> new BlockTileModel<>(type, properties -> properties.mapColor(BlockResourceInfo.STEEL.getMapColor())),
                InfuseExtractorItemBlock::new
        );
    }

    private static <BLOCK extends Block, ITEM extends BlockItem> BlockRegistryObject<BLOCK, ITEM> registerTieredBlock(ITier tier, String suffix, Supplier<? extends BLOCK> blockSupplier, Function<BLOCK, ITEM> itemCreator) {
        return BLOCKS.register(tier.getBaseTier().getLowerName() + suffix, blockSupplier, itemCreator);
    }
}
