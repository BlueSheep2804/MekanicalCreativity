package io.github.bluesheep2804.mekanicalcreativity.tier;

import mekanism.api.tier.BaseTier;
import mekanism.api.tier.ITier;
import mekanism.common.config.value.CachedIntValue;

public enum InfuseExtractorTier implements ITier {
    BASIC(BaseTier.BASIC, 80),
    ADVANCED(BaseTier.ADVANCED, 40),
    ELITE(BaseTier.ELITE, 20),
    ULTIMATE(BaseTier.ULTIMATE, 5),
    CREATIVE(BaseTier.CREATIVE, 1);

    private final BaseTier baseTier;
    private final int processingTicks;
    private CachedIntValue processingTickReference;

    InfuseExtractorTier(BaseTier baseTier, int processingTicks) {
        this.baseTier = baseTier;
        this.processingTicks = processingTicks;
    }

    @Override
    public BaseTier getBaseTier() {
        return baseTier;
    }

    public int getProcessingTicks() {
        return processingTickReference == null ? processingTicks : processingTickReference.getOrDefault();
    }

    public void setConfigReference(CachedIntValue processingTickReference) {
        this.processingTickReference = processingTickReference;
    }
}
