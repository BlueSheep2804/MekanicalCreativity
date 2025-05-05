package io.github.bluesheep2804.mekreate.block.infuser;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.block.IBE;
import io.github.bluesheep2804.mekreate.registries.create.CreateBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MechanicalInfuserBlock extends DirectionalKineticBlock implements IRotate, IBE<MechanicalInfuserBlockEntity> {
    public MechanicalInfuserBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState blockState) {
        return Direction.Axis.X;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return true;
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
