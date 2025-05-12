package io.github.bluesheep2804.mekreate.registries.create;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import io.github.bluesheep2804.mekreate.block.infuser.MechanicalInfuserBlockEntity;
import io.github.bluesheep2804.mekreate.block.infuser.MechanicalInfuserBlockRenderer;
import io.github.bluesheep2804.mekreate.block.infuser.MechanicalInfuserBlockVisual;

import static io.github.bluesheep2804.mekreate.Mekreate.REGISTRATE;

public class CreateBlockEntities {
    public static final BlockEntityEntry<MechanicalInfuserBlockEntity> MECHANICAL_INFUSER = REGISTRATE
            .blockEntity("mechanical_infuser", MechanicalInfuserBlockEntity::new)
            .visual(() -> MechanicalInfuserBlockVisual::new)
            .validBlocks(CreateBlocks.MECHANICAL_INFUSER)
            .renderer(() -> MechanicalInfuserBlockRenderer::new)
            .register();

    public static void register() {}
}
