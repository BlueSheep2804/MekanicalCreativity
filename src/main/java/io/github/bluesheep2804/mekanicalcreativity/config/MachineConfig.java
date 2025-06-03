package io.github.bluesheep2804.mekanicalcreativity.config;

import io.github.bluesheep2804.mekanicalcreativity.tier.InfuseExtractorTier;
import mekanism.common.config.BaseMekanismConfig;
import mekanism.common.config.value.*;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class MachineConfig extends BaseMekanismConfig {
    private final ForgeConfigSpec configSpec;

    MachineConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Machine Config.").push("machine");
        addInfuseExtractorCategory(builder);
        configSpec = builder.build();
    }

    private void addInfuseExtractorCategory(ForgeConfigSpec.Builder builder) {
        builder.comment("Infuse Extractor Settings").push("infuse_extractor");
        for (InfuseExtractorTier tier : InfuseExtractorTier.values()) {
            String tierName = tier.getBaseTier().getSimpleName();
            CachedIntValue processingTick = CachedIntValue.wrap(this, builder.comment("Conversion time for " + tierName + " Infuse Extractor (in ticks).")
                    .defineInRange(tierName.toLowerCase() + "ProcessingTick", tier.getProcessingTicks(), 1, Integer.MAX_VALUE));
            tier.setConfigReference(processingTick);
        }
        builder.pop();
    }

    @Override
    public String getFileName() {
        return "machine";
    }

    @Override
    public ForgeConfigSpec getConfigSpec() {
        return configSpec;
    }

    @Override
    public ModConfig.Type getConfigType() {
        return ModConfig.Type.SERVER;
    }

    @Override
    public boolean addToContainer() {
        return false;
    }
}
