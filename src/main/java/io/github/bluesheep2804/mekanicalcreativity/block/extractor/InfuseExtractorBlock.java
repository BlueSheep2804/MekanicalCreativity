package io.github.bluesheep2804.mekanicalcreativity.block.extractor;

import com.simibubi.create.foundation.block.IBE;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import io.github.bluesheep2804.mekanicalcreativity.registries.MekCreBlockEntities;
import io.github.bluesheep2804.mekanicalcreativity.tier.InfuseExtractorTier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class InfuseExtractorBlock extends Block implements IBE<InfuseExtractorBlockEntity> {
    private final InfuseExtractorTier tier;
    private final BlockEntityEntry<InfuseExtractorBlockEntity> blockEntity;

    private InfuseExtractorBlock(Properties properties, InfuseExtractorTier tier, BlockEntityEntry<InfuseExtractorBlockEntity> blockEntity) {
        super(properties);
        this.tier = tier;
        this.blockEntity = blockEntity;
    }

    public InfuseExtractorTier getTier() {
        return tier;
    }

    public static InfuseExtractorBlock basic(Properties properties) {
        return new InfuseExtractorBlock(properties, InfuseExtractorTier.BASIC, MekCreBlockEntities.BASIC_INFUSE_EXTRACTOR);
    }

    public static InfuseExtractorBlock advanced(Properties properties) {
        return new InfuseExtractorBlock(properties, InfuseExtractorTier.ADVANCED, MekCreBlockEntities.ADVANCED_INFUSE_EXTRACTOR);
    }

    public static InfuseExtractorBlock elite(Properties properties) {
        return new InfuseExtractorBlock(properties, InfuseExtractorTier.ELITE, MekCreBlockEntities.ELITE_INFUSE_EXTRACTOR);
    }

    public static InfuseExtractorBlock ultimate(Properties properties) {
        return new InfuseExtractorBlock(properties, InfuseExtractorTier.ULTIMATE, MekCreBlockEntities.ULTIMATE_INFUSE_EXTRACTOR);
    }

    public static InfuseExtractorBlock creative(Properties properties) {
        return new InfuseExtractorBlock(properties, InfuseExtractorTier.CREATIVE, MekCreBlockEntities.CREATIVE_INFUSE_EXTRACTOR);
    }

    @Override
    public Class<InfuseExtractorBlockEntity> getBlockEntityClass() {
        return InfuseExtractorBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends InfuseExtractorBlockEntity> getBlockEntityType() {
        return blockEntity.get();
    }
}
