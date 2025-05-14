package io.github.bluesheep2804.mekanicalcreativity.registries;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import io.github.bluesheep2804.mekanicalcreativity.MekanicalCreativity;


public class MekCrePartialModels {
    public static final PartialModel

    INFUSER_TOP = block("mechanical_infuser/top"),
    INFUSER_BOTTOM = block("mechanical_infuser/bottom")
    ;

    private static PartialModel block(String path) {
        return PartialModel.of(MekanicalCreativity.rl("block/" + path));
    }

    public static void init() {}
}
