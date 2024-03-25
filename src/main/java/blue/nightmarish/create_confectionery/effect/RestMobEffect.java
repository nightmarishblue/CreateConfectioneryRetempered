package blue.nightmarish.create_confectionery.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RestMobEffect extends MobEffect {
    public static final double DELETE_RADIUS = 32D;
    public static final double CLOUD_SPREAD = 0.5D;
    public static final double CLOUD_SPEED = 0.1D;

    public RestMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -2119568);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int pAmplifier) {
        if (entity.level().isClientSide()) return;
        ServerLevel level = (ServerLevel) entity.level();
        List<Phantom> nearbyEntities = level.getEntitiesOfClass(Phantom.class, new AABB(entity.blockPosition()).inflate(DELETE_RADIUS));
        for (Phantom mob : nearbyEntities) {
            RandomSource random = mob.getRandom();
            level.sendParticles(ParticleTypes.CLOUD, mob.getX(), mob.getY(), mob.getZ(), random.nextInt(4) + 1,
                    random.nextDouble() * CLOUD_SPREAD, random.nextDouble() * CLOUD_SPREAD, random.nextDouble() * CLOUD_SPREAD,
                    random.nextDouble() * CLOUD_SPEED);
            level.playSound(null, mob.blockPosition(), SoundEvents.PHANTOM_FLAP, SoundSource.HOSTILE, 0.5F, 1F);
            mob.teleportRelative(0D, 320D, 0D);
            mob.discard();
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
