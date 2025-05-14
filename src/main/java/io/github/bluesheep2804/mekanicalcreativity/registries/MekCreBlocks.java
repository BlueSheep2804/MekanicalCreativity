package io.github.bluesheep2804.mekanicalcreativity.registries;

import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.bluesheep2804.mekanicalcreativity.block.infuser.MechanicalInfuserBlock;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativity.REGISTRATE;

public class MekCreBlocks {
    public static final BlockEntry<MechanicalInfuserBlock> MECHANICAL_INFUSER = REGISTRATE
            .block("mechanical_infuser", MechanicalInfuserBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p
                    .noOcclusion()
                    .mapColor(MapColor.WOOD)
            )
            .transform(axeOrPickaxe())
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .item(AssemblyOperatorBlockItem::new)
            .transform(customItemModel())
            .register();

    public static final BlockEntry<CasingBlock> OSMIUM_CASING = REGISTRATE
            .block("osmium_casing", CasingBlock::new)
            .properties(p -> p.mapColor(MapColor.WOOD))
            .transform(BuilderTransformers.casing(() -> MekCreSpriteShifts.OSMIUM_CASING))
            .register();

    public static void register() {}
}
