package io.github.bluesheep2804.mekreate.block.infuser;

import com.simibubi.create.content.kinetics.base.ShaftVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;

public class MechanicalInfuserBlockVisual extends ShaftVisual<MechanicalInfuserBlockEntity> implements SimpleDynamicVisual {

    public MechanicalInfuserBlockVisual(VisualizationContext context, MechanicalInfuserBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);
    }

    @Override
    public void beginFrame(Context ctx) {}
}
