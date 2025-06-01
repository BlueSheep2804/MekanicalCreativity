package io.github.bluesheep2804.mekanicalcreativity.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativityLang;
import io.github.bluesheep2804.mekanicalcreativity.registries.MekCreBlocks;
import mekanism.api.providers.IBlockProvider;
import mekanism.api.providers.IItemProvider;
import mekanism.api.text.IHasTranslationKey;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.attribute.AttributeGui;
import mekanism.common.util.RegistryUtils;
import net.minecraft.Util;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.function.BiConsumer;

public class MekCreLanguageProvider {
    public static void addTranslations(RegistrateLangProvider provider) {
        BiConsumer<String, String> langConsumer = provider::add;
        provideDefaultLang("interface", langConsumer);

        addTiered(
                provider,
                MekCreBlocks.BASIC_INFUSE_EXTRACTOR,
                MekCreBlocks.ADVANCED_INFUSE_EXTRACTOR,
                MekCreBlocks.ELITE_INFUSE_EXTRACTOR,
                MekCreBlocks.ULTIMATE_INFUSE_EXTRACTOR,
                MekCreBlocks.CREATIVE_INFUSE_EXTRACTOR,
                "Infuse Extractor"
        );
        provider.add(MekanicalCreativityLang.DESCRIPTION_INFUSE_EXTRACTOR.getTranslationKey(), "A machine that converts items into substances that can be infused.");
    }

    // The add was taken from Mekanism
    // https://github.com/mekanism/Mekanism/blob/95b03e838aa94128596db42154a1527048372999/src/datagen/main/java/mekanism/client/lang/BaseLanguageProvider.java#L72
    private static void add(RegistrateLangProvider provider, IHasTranslationKey key, String value) {
        if (key instanceof IBlockProvider blockProvider) {
            Block block = blockProvider.getBlock();
            if (Attribute.matches(block, AttributeGui.class, attribute -> !attribute.hasCustomName())) {
                provider.add(Util.makeDescriptionId("container", RegistryUtils.getName(block)), value);
            }
        }
        provider.add(key.getTranslationKey(), value);
    }

    private static void addTiered(RegistrateLangProvider provider, IItemProvider basic, IItemProvider advanced, IItemProvider elite, IItemProvider ultimate, IItemProvider creative, String name) {
        add(provider, basic, "Basic " + name);
        add(provider, advanced, "Advanced " + name);
        add(provider, elite, "Elite " + name);
        add(provider, ultimate, "Ultimate " + name);
        add(provider, creative, "Creative " + name);
    }

    // The provideDefaultLang was taken from Create.
    // https://github.com/Creators-of-Create/Create/blob/46c9bc590ea0da17b9f61eecc37c26d4987570bd/src/main/java/com/simibubi/create/infrastructure/data/CreateDatagen.java#L75
    private static void provideDefaultLang(String fileName, BiConsumer<String, String> consumer) {
        String path = "assets/mekanical_creativity/lang/default/" + fileName + ".json";
        JsonElement jsonElement = FilesHelper.loadJsonResource(path);
        if (jsonElement == null) {
            throw new IllegalStateException(String.format("Could not find default lang file: %s", path));
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            consumer.accept(key, value);
        }
    }
}
