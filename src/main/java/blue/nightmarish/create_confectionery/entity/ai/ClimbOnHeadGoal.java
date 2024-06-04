package blue.nightmarish.create_confectionery.entity.ai;

import blue.nightmarish.create_confectionery.entity.custom.GingerbreadManEntity;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class ClimbOnHeadGoal extends Goal {
    public static final int SIT_MIN_DURATION = 20 * 15;
    private final GingerbreadManEntity entity;
    private ServerPlayer player;
    private boolean isSittingOnHead;
    private int sitTicks;

    public ClimbOnHeadGoal(GingerbreadManEntity gingerbreadMan) {
        this.entity = gingerbreadMan;
    }

    @Override
    public boolean canUse() {
        if (!this.entity.shouldPrank())
            return false;
        LivingEntity entity = this.entity.getTarget();
        if (entity instanceof ServerPlayer serverPlayer) {
            boolean playerViable = !serverPlayer.isSpectator() && !serverPlayer.getAbilities().flying && !serverPlayer.isInWater() && !serverPlayer.isInPowderSnow;
            return playerViable;
        }
        return false;
    }

    @Override
    public void start() {
        this.player = (ServerPlayer) this.entity.getTarget();
        this.isSittingOnHead = false;
        this.sitTicks = 0;
    }

    @Override
    public void stop() {
        this.entity.stopRiding();
        player.connection.send(new ClientboundSetPassengersPacket(this.player));
        this.entity.resetPrankDuration();
    }

    @Override
    public void tick() {
        // if we're already sitting, just tick the counter
        if (this.isSittingOnHead) {
            this.sitTicks++;
            return;
        }

        if (this.entity.isLeashed())
            return; // don't start sitting if we already are, or if we're leashed
        if (!this.entity.getBoundingBox().intersects(this.player.getBoundingBox()))
            return;

        this.isSittingOnHead = this.entity.startRiding(this.player, true);
        if (this.isSittingOnHead)
            player.connection.send(new ClientboundSetPassengersPacket(this.player));
    }

    @Override
    public boolean isInterruptable() {
        return !this.isSittingOnHead && this.sitTicks > this.adjustedTickDelay(SIT_MIN_DURATION);
    }
}
