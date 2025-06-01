package io.github.bluesheep2804.mekanicalcreativity;

import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.text.ILangEntry;
import net.minecraft.Util;

@NothingNullByDefault
public enum MekanicalCreativityLang implements ILangEntry {
    DESCRIPTION_INFUSE_EXTRACTOR("description", "infuse_extractor"),
    ;

    private final String key;

    MekanicalCreativityLang(String type, String path) {
        this(Util.makeDescriptionId(type, MekanicalCreativity.rl(path)));
    }

    MekanicalCreativityLang(String key) {
        this.key = key;
    }

    @Override
    public String getTranslationKey() {
        return key;
    }
}
