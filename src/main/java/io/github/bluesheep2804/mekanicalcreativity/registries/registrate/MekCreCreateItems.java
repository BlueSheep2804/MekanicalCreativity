package io.github.bluesheep2804.mekanicalcreativity.registries.registrate;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.simibubi.create.AllTags.AllItemTags.PLATES;
import static com.simibubi.create.AllTags.forgeItemTag;
import static io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativity.REGISTRATE;

public class MekCreCreateItems {
    public static final ItemEntry<Item> OSMIUM_SHEET = taggedIngredient("osmium_sheet", forgeItemTag("plates/osmium"), PLATES.tag);

    @SafeVarargs
    private static ItemEntry<Item> taggedIngredient(String name, TagKey<Item>... tags) {
        return REGISTRATE.item(name, Item::new)
                .tag(tags)
                .register();
    }

    public static void register() {}
}
