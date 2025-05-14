package io.github.bluesheep2804.mekanicalcreativity.data;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import io.github.bluesheep2804.mekanicalcreativity.registries.MekCreItems;
import net.minecraft.data.PackOutput;

import static com.simibubi.create.AllTags.forgeItemTag;
import static io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativity.rl;

public class MekCrePressingRecipeGen extends ProcessingRecipeGen {
    GeneratedRecipe OSMIUM = create(
            rl("osmium_ingot"),
            b -> b.require(forgeItemTag("ingots/osmium"))
                    .output(MekCreItems.OSMIUM_SHEET)
    );

    public MekCrePressingRecipeGen(PackOutput output) {
        super(output);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.PRESSING;
    }
}
