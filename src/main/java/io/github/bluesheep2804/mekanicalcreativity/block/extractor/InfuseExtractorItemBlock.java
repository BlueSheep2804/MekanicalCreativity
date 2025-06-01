package io.github.bluesheep2804.mekanicalcreativity.block.extractor;

import io.github.bluesheep2804.mekanicalcreativity.tier.InfuseExtractorTier;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.prefab.BlockTile;
import mekanism.common.item.block.machine.ItemBlockMachine;

public class InfuseExtractorItemBlock extends ItemBlockMachine {
    public InfuseExtractorItemBlock(BlockTile<?, ?> block) {
        super(block);
    }

    @Override
    public InfuseExtractorTier getTier() {
        return Attribute.getTier(getBlock(), InfuseExtractorTier.class);
    }
}
