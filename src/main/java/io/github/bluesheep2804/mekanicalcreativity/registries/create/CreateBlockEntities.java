package io.github.bluesheep2804.mekanicalcreativity.registries.create;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import io.github.bluesheep2804.mekanicalcreativity.block.infuser.MechanicalInfuserBlockEntity;
import io.github.bluesheep2804.mekanicalcreativity.block.infuser.MechanicalInfuserBlockRenderer;
import io.github.bluesheep2804.mekanicalcreativity.block.infuser.MechanicalInfuserBlockVisual;

import static io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativity.REGISTRATE;

public class CreateBlockEntities {
    public static final BlockEntityEntry<MechanicalInfuserBlockEntity> MECHANICAL_INFUSER = REGISTRATE
            .blockEntity("mechanical_infuser", MechanicalInfuserBlockEntity::new)
            .visual(() -> MechanicalInfuserBlockVisual::new)
            .validBlocks(CreateBlocks.MECHANICAL_INFUSER)
            .renderer(() -> MechanicalInfuserBlockRenderer::new)
            .register();

    public static void register() {}
}
