package io.github.bluesheep2804.mekanicalcreativity.data;

import com.simibubi.create.AllBlocks;
import io.github.bluesheep2804.mekanicalcreativity.registries.create.CreateBlocks;
import io.github.bluesheep2804.mekanicalcreativity.registries.create.CreateItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

import static com.simibubi.create.AllTags.forgeItemTag;
import static io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativity.rl;

public class MekCreStandardRecipeProvider extends RecipeProvider {
    public MekCreStandardRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CreateBlocks.MECHANICAL_INFUSER.get())
                .define('O', forgeItemTag("plates/osmium"))
                .define('M', CreateBlocks.OSMIUM_CASING)
                .define('|', AllBlocks.SHAFT)
                .define('I', getMekanismItem("metallurgic_infuser"))
                .define('S', AllBlocks.SPOUT)
                .pattern("OMO").pattern("|I|").pattern(" S ")
                .unlockedBy("has_osmium_casing", has(CreateBlocks.OSMIUM_CASING))
                .save(pWriter, rl("mechanical_infuser"));
    }

    private Item getItem(ResourceLocation resourceLocation) {
        return ForgeRegistries.ITEMS.getValue(resourceLocation);
    }

    private Item getMekanismItem(String id) {
        return getItem(ResourceLocation.fromNamespaceAndPath("mekanism", id));
    }
}
