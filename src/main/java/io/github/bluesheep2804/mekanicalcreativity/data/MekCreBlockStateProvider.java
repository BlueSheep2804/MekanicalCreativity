package io.github.bluesheep2804.mekanicalcreativity.data;

import io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativity;
import io.github.bluesheep2804.mekanicalcreativity.registries.MekCreBlocks;
import io.github.bluesheep2804.mekanicalcreativity.tier.InfuseExtractorTier;
import mekanism.common.block.attribute.Attribute;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MekCreBlockStateProvider extends BlockStateProvider {
    private final List<Block> infuseExtractor = List.of(
            MekCreBlocks.BASIC_INFUSE_EXTRACTOR.getBlock(),
            MekCreBlocks.ADVANCED_INFUSE_EXTRACTOR.getBlock(),
            MekCreBlocks.ELITE_INFUSE_EXTRACTOR.getBlock(),
            MekCreBlocks.ULTIMATE_INFUSE_EXTRACTOR.getBlock(),
            MekCreBlocks.CREATIVE_INFUSE_EXTRACTOR.getBlock()
    );

    public MekCreBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MekanicalCreativity.MODID, exFileHelper);
    }

    @NotNull
    @Override
    public String getName() {
        return "Block state provider: " + MekanicalCreativity.MODID;
    }

    @Override
    protected void registerStatesAndModels() {
        for (Block block : infuseExtractor) {
            InfuseExtractorTier tier = Attribute.getTier(block, InfuseExtractorTier.class);
            ModelFile.ExistingModelFile model = models().getExistingFile(MekanicalCreativity.rl("block/infuse_extractor/" + tier.getBaseTier().getLowerName()));
            this.horizontalBlock(block, model);
            this.simpleBlockItem(block, model);
        }
    }
}
