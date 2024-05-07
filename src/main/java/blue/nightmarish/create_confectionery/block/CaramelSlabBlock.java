package blue.nightmarish.create_confectionery.block;

import blue.nightmarish.create_confectionery.registry.CCBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CaramelSlabBlock extends SlabBlock {
//    protected static final VoxelShape BOTTOM_AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D);
//    protected static final VoxelShape TOP_AABB = Block.box(1.0D, 8.0D, 1.0D, 15.0D, 15.0D, 15.0D);

    public CaramelSlabBlock(Properties pProperties) {
        super(pProperties);
    }

//    @Override
//    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
//        SlabType slabtype = pState.getValue(TYPE);
//        return switch (slabtype) {
//            case DOUBLE -> CaramelBlock.SHAPE;
//            case TOP -> TOP_AABB;
//            default -> BOTTOM_AABB;
//        };
//    }

    @Override
    public boolean skipRendering(BlockState pState, BlockState pAdjacentState, Direction pDirection) {
        SlabType thisType = pState.getValue(TYPE);
        if (pAdjacentState.is(this))
            return thisType == pAdjacentState.getValue(TYPE);
        else if (pAdjacentState.is(CCBlocks.CARAMEL_BLOCK.get()))
            return thisType == SlabType.DOUBLE;
        else
            return super.skipRendering(pState, pAdjacentState, pDirection);
    }

//    @Override
//    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
//        this.squelchOnFall(pLevel, pEntity, pFallDistance, this.soundType);
//    }
//
//    @Override
//    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
//        this.slowTouchingEntity(pLevel, pPos, pEntity);
//        super.entityInside(pState, pLevel, pPos, pEntity);
//    }
}
