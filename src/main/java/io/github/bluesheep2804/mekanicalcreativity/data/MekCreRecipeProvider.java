package io.github.bluesheep2804.mekanicalcreativity.data;

import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class MekCreRecipeProvider extends RecipeProvider {
    static final List<ProcessingRecipeGen> GENERATORS = new ArrayList<>();

    public MekCreRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {}

    public static void registerAllProcessing(DataGenerator gen, PackOutput output) {
        GENERATORS.add(new MekCreItemApplicationRecipeGen(output));

        gen.addProvider(true, new DataProvider() {
            @Override
            public String getName() {
                return "Mekanical Creativity's Processing Recipes";
            }

            @Override
            public CompletableFuture<?> run(CachedOutput pOutput) {
                return CompletableFuture.allOf(GENERATORS.stream()
                        .map(gen -> gen.run(pOutput))
                        .toArray(CompletableFuture[]::new));
            }
        });
    }
}
