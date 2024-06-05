package blue.nightmarish.create_confectionery.entity.ai;

import blue.nightmarish.create_confectionery.entity.Prankster;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public abstract class PrankGoal extends Goal {
    public <T extends Mob & Prankster> PrankGoal(T entity) {
        this.mob = entity;
    }

    private final Prankster mob;
    <T extends Mob & Prankster> T mob() {
        return (T) this.mob;
    }

    abstract Prankster.Prank prankType();

    boolean validPrank() {
        return this.mob.shouldPrank(this.prankType());
    }

    @Override
    public boolean canUse() {
        return this.validPrank();
    }
}
