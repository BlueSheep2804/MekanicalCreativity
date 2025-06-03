package io.github.bluesheep2804.mekanicalcreativity.config;

import io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativity;
import mekanism.common.config.IMekanismConfig;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class MekCreConfigHelper {
    private MekCreConfigHelper() {
    }

    public static final Path CONFIG_DIR = FMLPaths.getOrCreateGameRelativePath(FMLPaths.CONFIGDIR.get().resolve(MekanicalCreativity.MODID));

    public static void registerConfig(ModContainer modContainer, IMekanismConfig config) {
        MekCreModConfig modConfig = new MekCreModConfig(modContainer, config);
        if (config.addToContainer()) {
            modContainer.addConfig(modConfig);
        }
    }
}
