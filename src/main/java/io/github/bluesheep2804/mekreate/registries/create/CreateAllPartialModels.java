package io.github.bluesheep2804.mekreate.registries.create;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import io.github.bluesheep2804.mekreate.Mekreate;


public class CreateAllPartialModels {
    public static final PartialModel

    INFUSER_TOP = block("mechanical_infuser/top"),
    INFUSER_BOTTOM = block("mechanical_infuser/bottom")
    ;

    private static PartialModel block(String path) {
        return PartialModel.of(Mekreate.rl("block/" + path));
    }

    public static void init() {}
}
