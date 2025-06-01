package io.github.bluesheep2804.mekanicalcreativity.block.infuser;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import io.github.bluesheep2804.mekanicalcreativity.registries.registrate.MekCreCreatePartialModels;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;

public class MechanicalInfuserBlockRenderer extends KineticBlockEntityRenderer<MechanicalInfuserBlockEntity> {
    public MechanicalInfuserBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    static final PartialModel[] BITS = {
            MekCreCreatePartialModels.INFUSER_TOP,
            MekCreCreatePartialModels.INFUSER_BOTTOM
    };

    @Override
    protected void renderSafe(MechanicalInfuserBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

        float squeeze = getSqueeze(be, partialTicks);

        ms.pushPose();
        for (PartialModel bit : BITS) {
            ms.translate(0, 6 * squeeze / 32f, 0);
            CachedBuffers.partial(bit, be.getBlockState())
                    .light(light)
                    .renderInto(ms, buffer.getBuffer(RenderType.solid()));
        }
        ms.popPose();
    }

    private float getSqueeze(MechanicalInfuserBlockEntity be, float partialTicks) {
        int processingTicks = be.processingTicks;
        float processingPT = processingTicks - partialTicks;
        int processingSpeed = be.calculateProcessingSpeed();
        float processingRate = processingPT / processingSpeed;

        float squeeze;
        if (processingTicks < 0)
            squeeze = 0;
        else if (processingRate > 0.7)  // 1 ~ 0.7
            squeeze = 0;
        else if (processingRate > 0.4)  // 0.7 ~ 0.4
            squeeze = Mth.lerp(Mth.clamp((processingRate - 0.4f) / (0.7f - 0.4f), 0, 1), -1, 0);
        else if (processingRate > 0.05)  // 0.4 ~ 0.05
            squeeze = -1;
        else  // 0.05 ~ 0
            squeeze = Mth.lerp(processingRate * 20, 0, -1);
        return Mth.clamp(squeeze, -1, 0);
    }
}
