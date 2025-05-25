package io.github.bluesheep2804.mekanicalcreativity.tier;

import mekanism.api.tier.BaseTier;
import mekanism.api.tier.ITier;

public enum InfuseExtractorTier implements ITier {
    BASIC(BaseTier.BASIC, 80),
    ADVANCED(BaseTier.ADVANCED, 40),
    ELITE(BaseTier.ELITE, 20),
    ULTIMATE(BaseTier.ULTIMATE, 5),
    CREATIVE(BaseTier.CREATIVE, 1);

    private final BaseTier baseTier;
    private final int processingTicks;

    InfuseExtractorTier(BaseTier baseTier, int processingTicks) {
        this.baseTier = baseTier;
        this.processingTicks = processingTicks;
    }

    @Override
    public BaseTier getBaseTier() {
        return baseTier;
    }

    public int getProcessingTicks() {
        return processingTicks;
    }
}
