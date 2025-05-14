package io.github.bluesheep2804.mekanicalcreativity.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.function.BiConsumer;

import static io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativity.REGISTRATE;

public class MekanicalCreativityDataGen {
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();

        gen.addProvider(event.includeServer(), new MekCreStandardRecipeProvider(output));

        if (event.includeServer()) {
            MekCreRecipeProvider.registerAllProcessing(gen, output);
        }

        REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add;

            provideDefaultLang("interface", langConsumer);
        });
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
