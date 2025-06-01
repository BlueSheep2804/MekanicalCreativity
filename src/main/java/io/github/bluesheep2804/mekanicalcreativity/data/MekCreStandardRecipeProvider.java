package io.github.bluesheep2804.mekanicalcreativity.data;

import com.simibubi.create.AllBlocks;
import io.github.bluesheep2804.mekanicalcreativity.registries.MekCreBlocks;
import io.github.bluesheep2804.mekanicalcreativity.registries.registrate.MekCreCreateBlocks;
import mekanism.api.tier.BaseTier;
import mekanism.common.item.block.machine.ItemBlockMachine;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MekCreCreateBlocks.MECHANICAL_INFUSER.get())
                .define('O', forgeItemTag("plates/osmium"))
                .define('M', MekCreCreateBlocks.OSMIUM_CASING)
                .define('|', AllBlocks.SHAFT)
                .define('I', getMekanismItem("metallurgic_infuser"))
                .define('S', AllBlocks.SPOUT)
                .pattern("OMO").pattern("|I|").pattern(" S ")
                .unlockedBy("has_osmium_casing", has(MekCreCreateBlocks.OSMIUM_CASING))
                .save(pWriter, rl("mechanical_infuser"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MekCreBlocks.BASIC_INFUSE_EXTRACTOR)
                .define('F', Blocks.FURNACE)
                .define('G', Blocks.GLASS)
                .define('R', forgeItemTag("dusts/redstone"))
                .define('I', forgeItemTag("ingots/iron"))
                .define('O', forgeItemTag("ingots/osmium"))
                .pattern(" G ").pattern("ROR").pattern("IFI")
                .unlockedBy("has_furnace", has(Blocks.FURNACE))
                .save(pWriter, rl("basic_infuse_extractor"));

        upgradeRecipe(
                pWriter,
                MekCreBlocks.ADVANCED_INFUSE_EXTRACTOR.asItem(),
                MekCreBlocks.BASIC_INFUSE_EXTRACTOR.asItem(),
                mekanismItemTag("alloys/infused"),
                forgeItemTag("circuits/advanced"),
                forgeItemTag("ingots/osmium")
        );
        upgradeRecipe(
                pWriter,
                MekCreBlocks.ELITE_INFUSE_EXTRACTOR.asItem(),
                MekCreBlocks.ADVANCED_INFUSE_EXTRACTOR.asItem(),
                mekanismItemTag("alloys/reinforced"),
                forgeItemTag("circuits/elite"),
                forgeItemTag("ingots/gold")
        );
        upgradeRecipe(
                pWriter,
                MekCreBlocks.ULTIMATE_INFUSE_EXTRACTOR.asItem(),
                MekCreBlocks.ELITE_INFUSE_EXTRACTOR.asItem(),
                mekanismItemTag("alloys/atomic"),
                forgeItemTag("circuits/ultimate"),
                forgeItemTag("gems/diamond")
        );
    }

    private void upgradeRecipe(Consumer<FinishedRecipe> pWriter, ItemBlockMachine output, ItemBlockMachine base, TagKey<Item> alloy, TagKey<Item> circuit, TagKey<Item> ingot) {
        BaseTier tier = output.getTier().getBaseTier();
        BaseTier baseTier = base.getTier().getBaseTier();
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, output)
                .define('A', alloy)
                .define('C', circuit)
                .define('I', ingot)
                .define('B', base)
                .pattern("ACA").pattern("IBI").pattern("ACA")
                .unlockedBy("has_" + baseTier.getLowerName(), has(base))
                .save(pWriter, rl(tier.getLowerName() + "_infuse_extractor"));
    }

    private Item getItem(ResourceLocation resourceLocation) {
        return ForgeRegistries.ITEMS.getValue(resourceLocation);
    }

    private Item getMekanismItem(String id) {
        return getItem(ResourceLocation.fromNamespaceAndPath("mekanism", id));
    }

    private TagKey<Item> mekanismItemTag(String path) {
        return TagKey.create(ForgeRegistries.Keys.ITEMS, ResourceLocation.fromNamespaceAndPath("mekanism", path));
    }
}
