package io.github.bluesheep2804.mekanicalcreativity.config;

import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;

public class MekCreConfig {
    private MekCreConfig() {}
    public static final MachineConfig machine = new MachineConfig();

    public static void registerConfigs(ModLoadingContext modLoadingContext) {
        ModContainer modContainer = modLoadingContext.getContainer();
        MekCreConfigHelper.registerConfig(modContainer, machine);
    }
}
