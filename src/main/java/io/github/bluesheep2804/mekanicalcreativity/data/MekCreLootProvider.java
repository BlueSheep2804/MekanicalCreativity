package io.github.bluesheep2804.mekanicalcreativity.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class MekCreLootProvider extends LootTableProvider {
    public MekCreLootProvider(PackOutput pOutput, Set<ResourceLocation> pRequiredTables, List<SubProviderEntry> pSubProviders) {
        super(pOutput, pRequiredTables, List.of(
                new SubProviderEntry(MekCreBlockLootSubProvider::new, LootContextParamSets.BLOCK)
        ));
    }
}
