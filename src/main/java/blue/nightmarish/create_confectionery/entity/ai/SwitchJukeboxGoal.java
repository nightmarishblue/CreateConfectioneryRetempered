package blue.nightmarish.create_confectionery.entity.ai;

import blue.nightmarish.create_confectionery.entity.Prankster;
import blue.nightmarish.create_confectionery.entity.custom.GingerbreadManEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.EnumSet;

public class SwitchJukeboxGoal extends PrankGoal {
    private static final int GOAL_DURATION = 20 * 10;

    private final Level level;
    private Path jukebox; // the closest reachable jukebox position
    private int goalTick;
    private final ItemStack newDisc; // the disc this prankster will put into jukeboxes

    public <T extends Mob & Prankster> SwitchJukeboxGoal(T pMob, ItemStack disc) {
        super(pMob);
        this.level = this.mob().level();
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
        this.newDisc = disc;
    }

    @Override
    Prankster.Prank prankType() {
        return Prankster.Prank.SWITCH_JUKEBOX;
    }

    // checks whether or not the mob has a jukebox that is currently playing
    boolean listeningToJukebox() {
        BlockPos jukeboxPos = this.mob().getJukebox();
        if (jukeboxPos == null) return false;
        BlockEntity blockEntity = this.level.getBlockEntity(jukeboxPos);
        if (blockEntity instanceof JukeboxBlockEntity jukeboxBlockEntity && jukeboxBlockEntity.isRecordPlaying()) {
            this.jukebox = this.mob().getNavigation().createPath(jukeboxPos, 1);
            return true;
        }
        return false;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.mob().getRandom().nextInt(16) == 0 && !this.mob().goodBeats() && this.listeningToJukebox();
    }

    @Override
    public void start() {
        // move to the jukebox
        this.mob().getNavigation().stop();
        this.mob().getNavigation().moveTo(this.jukebox, 1D);
        // set a time limit so we don't sit here forever
        this.goalTick = this.adjustedTickDelay(GOAL_DURATION);
    }

    @Override
    public void stop() {
        // reset all variables
        this.mob().getNavigation().stop();
        this.jukebox = null;
        this.goalTick = 0;
    }

    @Override
    public boolean canContinueToUse() {
        // stop if we run out of time, or if the cake is destroyed
        return this.goalTick > 0;
    }

    @Override
    public boolean isInterruptable() {
        return this.goalTick == 0;
    }

    @Override
    public void tick() {
        this.goalTick = Math.max(0, this.goalTick - 1);

        // check if we're done moving
        if (this.jukebox.notStarted() || !this.jukebox.isDone())
            return;

        // check if the prankster is still listening to a jukebox, and that we're next to the jukebox - if not, stop
        BlockPos pos = this.mob().getJukebox();
        if (pos == null || !pos.closerToCenterThan(this.mob().position(), 1.5)) {
            this.goalTick = 0;
            return;
        }

        this.switchRecord(pos);
        this.goalTick = 0;
    }

    // swap the record currently in the jukebox with the Bright Side
    void switchRecord(BlockPos pos) {
        BlockState state = this.level.getBlockState(pos);
        if (!state.is(Blocks.JUKEBOX))
            return;

        BlockEntity block = this.level.getBlockEntity(pos);
        if (block instanceof JukeboxBlockEntity jukeboxEntity) {
            if (!jukeboxEntity.isRecordPlaying())
                return;

            ItemStack record = jukeboxEntity.getFirstItem();
            if (!record.is(GingerbreadManEntity.BAD_DISCS))
                return; // do not replace the disc if he likes it

            // if we can grief, the item will be destroyed - otherwise it's popped out of the jukebox like normal
            if (ForgeEventFactory.getMobGriefingEvent(this.level, this.mob())) {
                jukeboxEntity.removeFirstItem();
                tossEtherealItem(pos, record);
            }
            else {
                jukeboxEntity.popOutRecord();
            }

            this.level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL);
            jukeboxEntity.setFirstItem(newDisc.copy());
            this.level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(this.mob(), state));
            this.mob().resetPrankDuration();
        }
    }

    // toss an item randomly - it will disappear very quickly, and can't be picked up
    void tossEtherealItem(BlockPos pos, ItemStack record) {
        Vec3 vec3 = Vec3.atLowerCornerWithOffset(pos, 0.5D, 1.01D, 0.5D).offsetRandom(this.level.random, 0.7F);
        ItemEntity tossedItem = new ItemEntity(this.level, vec3.x(), vec3.y(), vec3.z(), record);
        tossedItem.lifespan = 20;
        tossedItem.setNeverPickUp();
        this.level.addFreshEntity(tossedItem);
    }
}
