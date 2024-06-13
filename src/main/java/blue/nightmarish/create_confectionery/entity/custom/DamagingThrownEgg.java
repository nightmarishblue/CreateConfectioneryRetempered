package blue.nightmarish.create_confectionery.entity.custom;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class DamagingThrownEgg extends ThrownEgg {
    public DamagingThrownEgg(EntityType<? extends ThrownEgg> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public DamagingThrownEgg(Level pLevel, LivingEntity pShooter) {
        super(pLevel, pShooter);
    }

    public DamagingThrownEgg(Level pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
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
}
