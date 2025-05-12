package io.github.bluesheep2804.mekanicalcreativity.block.infuser;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.block.IBE;
import io.github.bluesheep2804.mekanicalcreativity.registries.create.CreateBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MechanicalInfuserBlock extends HorizontalKineticBlock implements IRotate, IBE<MechanicalInfuserBlockEntity> {
    public MechanicalInfuserBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction prefferedSide = getPreferredHorizontalFacing(context);
        if (prefferedSide != null)
            return defaultBlockState().setValue(HORIZONTAL_FACING, prefferedSide);
        return super.getStateForPlacement(context);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState blockState) {
        return blockState.getValue(HORIZONTAL_FACING).getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == state.getValue(HORIZONTAL_FACING).getAxis();
    }

    @Override
    public Class<MechanicalInfuserBlockEntity> getBlockEntityClass() {
        return MechanicalInfuserBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends MechanicalInfuserBlockEntity> getBlockEntityType() {
        return CreateBlockEntities.MECHANICAL_INFUSER.get();
    }
}
