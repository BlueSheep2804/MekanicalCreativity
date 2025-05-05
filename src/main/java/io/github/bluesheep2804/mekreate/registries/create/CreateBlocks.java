package io.github.bluesheep2804.mekreate.registries.create;

import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.bluesheep2804.mekreate.block.infuser.MechanicalInfuserBlock;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static io.github.bluesheep2804.mekreate.Mekreate.REGISTRATE;

public class CreateBlocks {
    public static final BlockEntry<MechanicalInfuserBlock> MECHANICAL_INFUSER = REGISTRATE
            .block("mechanical_infuser", MechanicalInfuserBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK))
            .simpleItem()
            .register();

    public static void register() {}
}
