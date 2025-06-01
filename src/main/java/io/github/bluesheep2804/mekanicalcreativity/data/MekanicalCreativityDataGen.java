package io.github.bluesheep2804.mekanicalcreativity.data;

import com.tterrag.registrate.providers.ProviderType;
import io.github.bluesheep2804.mekanicalcreativity.registries.MekCreBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.Collections;

import static io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativity.REGISTRATE;

public class MekanicalCreativityDataGen {
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();

        gen.addProvider(event.includeServer(), new MekCreStandardRecipeProvider(output));
        gen.addProvider(event.includeServer(), new MekCreLootProvider(output, Collections.emptySet(), Collections.emptyList()));
        gen.addProvider(event.includeClient(), new MekCreBlockStateProvider(output, event.getExistingFileHelper()));

        if (event.includeServer()) {
            MekCreRecipeProvider.registerAllProcessing(gen, output);
        }

        REGISTRATE.addDataGenerator(ProviderType.LANG, MekCreLanguageProvider::addTranslations);

        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, provider -> {
            provider.addTag(BlockTags.MINEABLE_WITH_PICKAXE)
                    .add(
                            MekCreBlocks.BASIC_INFUSE_EXTRACTOR.getBlock(),
                            MekCreBlocks.ADVANCED_INFUSE_EXTRACTOR.getBlock(),
                            MekCreBlocks.ELITE_INFUSE_EXTRACTOR.getBlock(),
                            MekCreBlocks.ULTIMATE_INFUSE_EXTRACTOR.getBlock(),
                            MekCreBlocks.CREATIVE_INFUSE_EXTRACTOR.getBlock()
                    );
        });
    }

}
