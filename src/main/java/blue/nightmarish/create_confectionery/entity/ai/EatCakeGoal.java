package blue.nightmarish.create_confectionery.entity.ai;

import blue.nightmarish.create_confectionery.entity.custom.GingerbreadManEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Predicate;

public class EatCakeGoal extends Goal {
    private static final int GOAL_DURATION = 20 * 10;
    private static final Predicate<BlockState> IS_CAKE = BlockStatePredicate.forBlock(Blocks.CAKE);

    private final GingerbreadManEntity mob;
    private final Level level;
    private Path cake; // the closest reachable cake position
    private int goalTick;

    public EatCakeGoal(GingerbreadManEntity pMob) {
        this.mob = pMob;
        this.level = this.mob.level();
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    boolean isCake(BlockPos pos) {
        return IS_CAKE.test(this.level.getBlockState(pos));
    }

    boolean validCake(BlockPos pos) {
        if (!isCake(pos))
            return false;
        Path path = this.mob.getNavigation().createPath(pos, 0);
        return path != null && path.canReach();
    }

    //
    boolean searchForCake() {
        Optional<BlockPos> closestCake = BlockPos.findClosestMatch(this.mob.blockPosition(), 16, 16, this::validCake);
        if (closestCake.isEmpty())
            return false;
        this.cake = this.mob.getNavigation().createPath(closestCake.get(), 0);
        return true;
    }

    @Override
    public boolean canUse() {
        if (!this.mob.shouldPrank() || this.mob.getRandom().nextInt(8) != 0)
            return false;
        return this.searchForCake();
    }

    @Override
    public void start() {
        // move to the cake
        this.mob.getNavigation().stop();
        this.mob.getNavigation().moveTo(this.cake, 1f);
        // set a time limit so we don't sit here forever
        this.goalTick = this.adjustedTickDelay(GOAL_DURATION);
    }

    @Override
    public void stop() {
        // reset all variables
        this.mob.getNavigation().stop();
        this.cake = null;
        this.goalTick = 0;
    }

    @Override
    public boolean canContinueToUse() {
        // stop if we run out of time, or if the cake is destroyed
        return this.goalTick > 0 && this.mob.shouldPrank();
    }

    @Override
    public void tick() {
        this.goalTick = Math.max(0, this.goalTick - 1);

        // check if we're done moving
        if (this.cake.notStarted() || !this.cake.isDone())
            return;

        BlockPos pos = this.mob.blockPosition();
        // check if we're in cake - if not, stop
        if (!IS_CAKE.test(this.level.getBlockState(pos))) {
            this.goalTick = 0;
            return;
        }

        this.eatCake(pos);
        this.goalTick = 0;
    }

    void eatCake(BlockPos pos) {
        // stop if we can't grief
        if (!ForgeEventFactory.getMobGriefingEvent(this.level, this.mob))
            return;

        BlockState state = this.level.getBlockState(pos);
        // make sure it is in fact, cake
        if (!IS_CAKE.test(state))
            return;

        int i = state.getValue(BlockStateProperties.BITES);
        this.level.gameEvent(this.mob, GameEvent.EAT, pos);
        this.level.destroyBlock(pos, false);
        if (i < 6) {
            this.level.setBlock(pos, state.setValue(BlockStateProperties.BITES, i + 1), 1 | 2);
            if (this.mob.getRandom().nextInt(2) == 0) // if there's still cake, maybe nibble more...
                this.mob.ate();
        } else {
            this.level.gameEvent(this.mob, GameEvent.BLOCK_DESTROY, pos);
            this.mob.ate();
        }
    }
}
