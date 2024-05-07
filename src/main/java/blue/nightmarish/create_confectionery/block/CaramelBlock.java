package blue.nightmarish.create_confectionery.block;

import blue.nightmarish.create_confectionery.registry.CCBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CaramelBlock extends HalfTransparentBlock {
//    public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);

    public CaramelBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

//    @Override
//    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
//        return SHAPE;
//    }

    @Override
    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
        if (pAdjacentBlockState.is(CCBlocks.CARAMEL_SLAB.get()))
            return pAdjacentBlockState.getValue(SlabBlock.TYPE) == SlabType.DOUBLE;
        else
            return super.skipRendering(pState, pAdjacentBlockState, pSide);
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
