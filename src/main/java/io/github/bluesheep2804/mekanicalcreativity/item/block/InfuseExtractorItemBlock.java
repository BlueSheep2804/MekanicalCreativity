package io.github.bluesheep2804.mekanicalcreativity.item.block;

import io.github.bluesheep2804.mekanicalcreativity.block.extractor.InfuseExtractorBlock;
import io.github.bluesheep2804.mekanicalcreativity.tier.InfuseExtractorTier;

public class InfuseExtractorItemBlock extends MekLikeItemBlock<InfuseExtractorBlock> {
    public InfuseExtractorItemBlock(InfuseExtractorBlock pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public InfuseExtractorTier getTier() {
        return getBlock().getTier();
    }
}
