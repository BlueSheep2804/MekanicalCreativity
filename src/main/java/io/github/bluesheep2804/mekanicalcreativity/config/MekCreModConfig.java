package io.github.bluesheep2804.mekanicalcreativity.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativity;
import mekanism.common.config.IMekanismConfig;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.config.ConfigFileTypeHandler;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.util.function.Function;

public class MekCreModConfig extends ModConfig {
    public static final MekCreConfigFileTypeHandler MEKCRE_TOML = new MekCreConfigFileTypeHandler();

    private final IMekanismConfig mekanicalCreativityConfig;

    public MekCreModConfig(ModContainer modContainer, IMekanismConfig config) {
        super(config.getConfigType(), config.getConfigSpec(), modContainer, MekanicalCreativity.MODID + "/" + config.getFileName() + ".toml");
        this.mekanicalCreativityConfig = config;
    }

    @Override
    public ConfigFileTypeHandler getHandler() {
        return MEKCRE_TOML;
    }

    public void clearCache(ModConfigEvent event) {
        mekanicalCreativityConfig.clearCache(event instanceof ModConfigEvent.Unloading);
    }

    private static class MekCreConfigFileTypeHandler extends ConfigFileTypeHandler {
        private static Path getPath(Path configBasePath) {
            if (configBasePath.endsWith("serverconfig")) {
                return FMLPaths.CONFIGDIR.get();
            }
            return configBasePath;
        }

        @Override
        public Function<ModConfig, CommentedFileConfig> reader(Path configBasePath) {
            return super.reader(getPath(configBasePath));
        }

        @Override
        public void unload(Path configBasePath, ModConfig config) {
            super.unload(getPath(configBasePath), config);
        }
    }
}
