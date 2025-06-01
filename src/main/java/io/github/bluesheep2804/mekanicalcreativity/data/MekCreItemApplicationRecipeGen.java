package io.github.bluesheep2804.mekanicalcreativity.data;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import io.github.bluesheep2804.mekanicalcreativity.registries.registrate.MekCreCreateBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

import static com.simibubi.create.AllTags.forgeItemTag;
import static io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativity.rl;

public final class MekCreItemApplicationRecipeGen extends ProcessingRecipeGen {
    GeneratedRecipe OSMIUM = woodCasingIngredient("osmium", () -> Ingredient.of(forgeItemTag("ingots/osmium")), MekCreCreateBlocks.OSMIUM_CASING::get);

    public MekCreItemApplicationRecipeGen(PackOutput pOutput) {
        super(pOutput);
    }

    private GeneratedRecipe woodCasingIngredient(String type, Supplier<Ingredient> ingredient, Supplier<ItemLike> output) {
        create(rl(type + "_casing_from_log"), b -> b.require(AllTags.AllItemTags.STRIPPED_LOGS.tag)
                .require(ingredient.get())
                .output(output.get()));
        return create(rl(type + "_casing_from_wood"), b -> b.require(AllTags.AllItemTags.STRIPPED_WOOD.tag)
                .require(ingredient.get())
                .output(output.get()));
    }

    @Override
    protected AllRecipeTypes getRecipeType() {
        return AllRecipeTypes.ITEM_APPLICATION;
    }
}
