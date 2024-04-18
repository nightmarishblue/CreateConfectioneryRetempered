package blue.nightmarish.create_confectionery.block;

import blue.nightmarish.create_confectionery.network.packet.ClientboundCaramelParticleEvent;
import blue.nightmarish.create_confectionery.registry.CCBlocks;
import blue.nightmarish.create_confectionery.registry.CCSounds;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CaramelBlock extends HalfTransparentBlock {
    private static final double SLIDE_STARTS_WHEN_VERTICAL_SPEED_IS_AT_LEAST = 0.13D;
    private static final double MIN_FALL_SPEED_TO_BE_CONSIDERED_SLIDING = 0.08D;
    private static final double THROTTLE_SLIDE_SPEED_TO = 0.05D;
//    private static final int SLIDE_ADVANCEMENT_CHECK_INTERVAL = 20;
    protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);

    private static final int NUM_JUMP_PARTICLES = 10;
    private static final int NUM_SLIDE_PARTICLES = 5;

    public CaramelBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    private static boolean doesEntityDoSlideEffects(Entity pEntity) {
        return pEntity instanceof LivingEntity || pEntity instanceof AbstractMinecart || pEntity instanceof PrimedTnt || pEntity instanceof Boat;
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        pEntity.playSound(CCSounds.CARAMEL_BLOCK_SLIDE.get(), 1.0F, 1.0F);
        if (!pLevel.isClientSide) {
            ClientboundCaramelParticleEvent.broadcastCaramelParticles(pEntity, NUM_JUMP_PARTICLES);
//            pLevel.broadcastEntityEvent(pEntity, (byte) 54); // send a message to start spawning particles
        }
        if (pEntity.causeFallDamage(pFallDistance, 0.2F, pLevel.damageSources().fall())) {
            pEntity.playSound(this.soundType.getFallSound(), this.soundType.getVolume() * 0.5F, this.soundType.getPitch() * 0.75F);
        }
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (this.isSlidingDown(pPos, pEntity)) {
            this.doSlideMovement(pEntity);
            this.maybeDoSlideEffects(pLevel, pEntity);
        }
        super.entityInside(pState, pLevel, pPos, pEntity);
    }

    private boolean isSlidingDown(BlockPos pPos, Entity pEntity) {
        if (pEntity.onGround()) {
            return false;
        } else if (pEntity.getY() > pPos.getY() + 0.9375D - 1.0E-7D) {
            return false;
        } else if (pEntity.getDeltaMovement().y >= -MIN_FALL_SPEED_TO_BE_CONSIDERED_SLIDING) {
            return false;
        } else {
            double d0 = Math.abs(pPos.getX() + 0.5D - pEntity.getX());
            double d1 = Math.abs(pPos.getZ() + 0.5D - pEntity.getZ());
            double d2 = 0.4375D + (pEntity.getBbWidth() / 2.0F);
            return d0 + 1.0E-7D > d2 || d1 + 1.0E-7D > d2;
        }
    }

    private void doSlideMovement(Entity pEntity) {
        Vec3 vec3 = pEntity.getDeltaMovement();
        if (vec3.y < -SLIDE_STARTS_WHEN_VERTICAL_SPEED_IS_AT_LEAST) {
            double d0 = -THROTTLE_SLIDE_SPEED_TO / vec3.y;
            pEntity.setDeltaMovement(new Vec3(vec3.x * d0, -THROTTLE_SLIDE_SPEED_TO, vec3.z * d0));
        } else {
            pEntity.setDeltaMovement(new Vec3(vec3.x, -THROTTLE_SLIDE_SPEED_TO, vec3.z));
        }
        pEntity.resetFallDistance();
    }

    private void maybeDoSlideEffects(Level pLevel, Entity pEntity) {
        if (doesEntityDoSlideEffects(pEntity)) {
            if (pLevel.random.nextInt(5) == 0) {
                pEntity.playSound(CCSounds.CARAMEL_BLOCK_SLIDE.get(), 1.0F, 1.0F);
            }
            if (!pLevel.isClientSide && pLevel.random.nextInt(5) == 0) {
                ClientboundCaramelParticleEvent.broadcastCaramelParticles(pEntity, NUM_SLIDE_PARTICLES);
            }
        }
    }

    public static void showParticles(Entity pEntity, int pParticleCount) {
        if (pEntity.level().isClientSide) {
            BlockState blockstate = CCBlocks.CARAMEL_BLOCK.get().defaultBlockState();

            for(int i = 0; i < pParticleCount; ++i) {
                pEntity.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), pEntity.getX(),
                        pEntity.getY(), pEntity.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
