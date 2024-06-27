package blue.nightmarish.create_confectionery.entity.custom;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class DamagingThrownEgg extends ThrownEgg {
    public DamagingThrownEgg(Level pLevel, LivingEntity pShooter) {
        super(pLevel, pShooter);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();

        Entity shooter = this.getOwner();
        DamageSource source;
        source = this.damageSources().thrown(this, shooter);
        if (shooter instanceof LivingEntity living) {
            living.setLastHurtMob(entity);
        }

        if (entity.hurt(source, 0.5f)) {
            if (entity.getType() == EntityType.ENDERMAN)
                return;
        }

        super.onHitEntity(pResult);
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            if (this.random.nextInt(16) == 0) {
                int i = 1;
                if (this.random.nextInt(64) == 0) {
                    i = 4;
                }

                for(int j = 0; j < i; ++j) {
                    Chicken chicken = EntityType.CHICKEN.create(this.level());
                    if (chicken != null) {
                        chicken.setAge(-24000);
                        chicken.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                        this.level().addFreshEntity(chicken);
                    }
                }
            }

            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }
}
