package blue.nightmarish.create_confectionery.entity.ai;

import blue.nightmarish.create_confectionery.entity.Prankster;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.pathfinder.Path;

public class ClimbOnHeadGoal extends PrankGoal {
    public static final int SIT_MIN_DURATION = 20 * 15;
    private ServerPlayer player;
    private boolean isSittingOnHead;
    private int goalTicks;

    public <T extends Mob & Prankster> ClimbOnHeadGoal(T entity) {
        super(entity);
    }

    @Override
    Prankster.Prank prankType() {
        return Prankster.Prank.CLIMB_HEAD;
    }

    @Override
    public boolean canUse() {
        if (!super.canUse())
            return false;
        LivingEntity target = this.mob().getTarget();
        if (target instanceof ServerPlayer serverPlayer) {
            boolean playerViable = !serverPlayer.isSpectator() && !serverPlayer.getAbilities().flying && !serverPlayer.isInWater() && !serverPlayer.isInPowderSnow;
            return playerViable;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.goalTicks < SIT_MIN_DURATION;
    }

    @Override
    public void start() {
        this.player = (ServerPlayer) this.mob().getTarget(); // store the player

        // move towards the player
        Path path = this.mob().getNavigation().createPath(this.player, 0);
        this.mob().getNavigation().moveTo(path, 1);

        // set control variables
        this.isSittingOnHead = false;
        this.goalTicks = 0;
    }

    @Override
    public void stop() {
        this.mob().stopRiding();
        this.mob().getNavigation().stop();
        player.connection.send(new ClientboundSetPassengersPacket(this.player));
        this.mob().resetPrankDuration();
    }

    @Override
    public void tick() {
        this.goalTicks++;

        if (this.mob().isLeashed() || this.isSittingOnHead)
            return; // don't start sitting if we already are, or if we're leashed
        if (!this.mob().getBoundingBox().intersects(this.player.getBoundingBox()))
            return;

        if (this.isSittingOnHead = this.mob().startRiding(this.player, true))
            player.connection.send(new ClientboundSetPassengersPacket(this.player));
    }

    @Override
    public boolean isInterruptable() {
        return !this.isSittingOnHead;
    }
}
