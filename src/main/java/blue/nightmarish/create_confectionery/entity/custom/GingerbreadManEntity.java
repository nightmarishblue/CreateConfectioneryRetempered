package blue.nightmarish.create_confectionery.entity.custom;

import blue.nightmarish.create_confectionery.CCUtils;
import blue.nightmarish.create_confectionery.data.CCItemTagGenerator;
import blue.nightmarish.create_confectionery.entity.Prankster;
import blue.nightmarish.create_confectionery.entity.ai.ClimbOnHeadGoal;
import blue.nightmarish.create_confectionery.entity.ai.EatCakeGoal;
import blue.nightmarish.create_confectionery.entity.ai.SwitchJukeboxGoal;
import blue.nightmarish.create_confectionery.network.clientbound.ClientboundGingerbreadManData;
import blue.nightmarish.create_confectionery.registry.CCItems;
import blue.nightmarish.create_confectionery.registry.CCSounds;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GingerbreadManEntity extends AbstractGolem implements RangedAttackMob, Prankster {
    // what it counts as when you gnaw on a gingerbread man
    public static final ItemStack CONSUME_ITEM = new ItemStack(CCItems.GINGERBREAD.get());
    // the particles that come out when you take a bite of this mob
    public static final ItemStack EATEN_PARTICLES = new ItemStack(CCItems.GINGERBREAD_MAN.get());
    public static final TagKey<Item> FOOD_ITEMS = CCItemTagGenerator.GINGERBREAD_MAN_FOODS;
    public static final TagKey<Item> BAD_DISCS = CCItemTagGenerator.BAD_DISCS;
//    public static final TagKey<Item> TAME_ITEMS = ItemTags.create(new ResourceLocation(CreateConfectionery.MOD_ID, "gingerbread_man_tame_items"));
//    public static final Set<FluidType> CAN_SWIM_IN = Set.of(AllFluids.CHOCOLATE.getType(), ForgeMod.MILK_TYPE.get(),
//            CCFluidTypes.DARK_CHOCOLATE_TYPE.get());

    // stats to track whether this guy should be mischievous
    public static int MIN_PRANK_DELAY = 20 * 60 * 2;
    private int nextPrankTime; // the time to perform this mob's next prank
    private Prank nextPrankType;

    // stats to check whether this guy should party
    private BlockPos jukebox;
    private ItemStack record = ItemStack.EMPTY; // the record this entity is listening to

    public GingerbreadManEntity(EntityType<GingerbreadManEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setMaxUpStep(0.6F);
        this.xpReward = 0;
        this.resetPrankDuration();
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        // 1. this is client side and we can't do anything but guess the outcome
        if (this.level().isClientSide) {
//            boolean flag = this.isOwnedBy(pPlayer) || this.isTame() || this.isFood(itemstack) && !this.isTame();
            if (this.canEat(itemstack)) {
                this.repairParticles(itemstack);
                return InteractionResult.CONSUME;
            }
            return InteractionResult.PASS;
        }

        // 2. that was food you just gave it; try to feed it
        if (this.canEat(itemstack)) {
            FoodProperties foodProps = itemstack.getFoodProperties(this);
            this.heal(foodProps != null ? foodProps.getNutrition() : 0.5F); // since this thing can eat sugar, we want a fallback
            this.level().playSound(null, this.blockPosition(), CCSounds.GINGERBREAD_MAN_REPAIR.get(), SoundSource.NEUTRAL, 0.5F, 1.5F);

            if (!pPlayer.getAbilities().instabuild) itemstack.shrink(1);

            this.gameEvent(GameEvent.EAT, this);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(pPlayer, pHand);
    }

    // throw an egg - and maybe other things too
    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        Projectile egg = new DamagingThrownEgg(this.level(), this);
        double targetBodyY = pTarget.getEyeY() - 1.1D;
        double relativeX = pTarget.getX() - this.getX();
        double relativeY = targetBodyY - egg.getY();
        double relativeZ = pTarget.getZ() - this.getZ();
        double extra = Math.sqrt(relativeX * relativeX + relativeZ * relativeZ) * 0.2D;
        egg.shoot(relativeX, relativeY + extra, relativeZ, 1.6F, 8F);
        this.playSound(SoundEvents.EGG_THROW, 1F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(egg);
        if (!(pTarget instanceof Enemy)) {
            this.resetPrankDuration();
            this.setTarget(null);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void repairParticles(ItemStack stack) {
        if (!this.canEat(stack)) return;
        this.spawnItemParticles(stack);
    }

    @OnlyIn(Dist.CLIENT)
    public void spawnItemParticles(ItemStack stack) {
        Vec3 pos = this.position().add(0D, 0.25D, 0D);
        int num = this.random.nextInt(4) + 4;
        for (int i = 0; i < num; i++) {
            Vec3 motion = CCUtils.offsetVec3(0.1f, this.random)
                    .multiply(2D, 0D, 2D)
                    .add(0D, num == 1 ? 0.125D : 0.25D, 0D);
            this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack),
                    pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
        }
    }

    @Override
    public void ate() {
        super.ate();
        this.heal(1f);
        this.level().playSound(null, this.blockPosition(), SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 0.5F, 1.5F);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pAmount <= 0) return super.hurt(pSource, pAmount);

        Entity attacker = pSource.getDirectEntity();
        if (this.level().isClientSide() && !pSource.isIndirect() && attacker != null)
            this.spawnItemParticles(EATEN_PARTICLES); // spawn chips if this was a direct physical attack
        if (attacker instanceof LivingEntity livingEntity && livingEntity.getMainHandItem().isEmpty())
            livingEntity.eat(livingEntity.level(), CONSUME_ITEM.copy());

        return super.hurt(pSource, pAmount);
    }

    // gingerbread men can't swim in water
    @Override
    public void jumpInFluid(FluidType type) {
        if (type != ForgeMod.WATER_TYPE.get())
            super.jumpInFluid(type);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.getEatenState().ordinal() < 2 ? CCSounds.GINGERBREAD_MAN_AMBIENT.get() : null;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 150;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return CCSounds.GINGERBREAD_MAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return CCSounds.GINGERBREAD_MAN_DEATH.get();
    }

    public boolean isFood(ItemStack pStack) {
        return pStack.is(FOOD_ITEMS);
    }

    public boolean canEat(ItemStack stack) {
        return this.isFood(stack) && this.getHealth() < this.getMaxHealth();
    }

    @Override
    public Vec3 getLeashOffset(float pPartialTick) {
        return new Vec3(0, 0.6 * this.getEyeHeight(), this.getBbWidth() * 0.2);
    }

    @Override
    public double getMyRidingOffset() {
        return this.getEyeHeight() * 0.5D;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) return;

        if (this.tickCount - this.nextPrankTime > 2000) this.resetPrankDuration();
    }

    static final double sitMult = 0.7;
    int jukeboxTicks = 0;
    @Override
    public void aiStep() {
        super.aiStep();
        // slow down the entity we're sitting on
        Entity vehicle = this.getVehicle();
        if (vehicle != null && this.shouldPrank(Prank.CLIMB_HEAD)) {
            vehicle.setDeltaMovement(vehicle.getDeltaMovement().multiply(sitMult, 1, sitMult));
        }

        if (!this.level().isClientSide() && jukeboxTicks++ == 20) {
            jukeboxTicks = 0;
            this.setRecordPlayingNearby();
        }
    }

    // the one in LivingEntity is purely clientside. No good.
    // checks if there's a jukebox playing music nearby, and sync the results with clients
    // FIXME registers as dancing even when there is no music coming out of the box
    public void setRecordPlayingNearby() {
        Optional<BlockPos> closestJukebox = BlockPos.findClosestMatch(this.blockPosition(), 3, 3, pos -> this.level().getBlockState(pos).is(Blocks.JUKEBOX));
        if (closestJukebox.isPresent()) {
            BlockEntity blockEntity = this.level().getBlockEntity(closestJukebox.get());
            if (blockEntity instanceof JukeboxBlockEntity jukeboxBlockEntity && jukeboxBlockEntity.isRecordPlaying()) {
                this.record = jukeboxBlockEntity.getFirstItem();
            } else {
                this.record = ItemStack.EMPTY;
            }
            this.jukebox = closestJukebox.get();
        }
        else {
            this.jukebox = null;
            this.record = ItemStack.EMPTY;
        }
        ClientboundGingerbreadManData.broadcastGingerbreadManData(this); // TODO perhaps only send this packet when stats change
    }

    public boolean partying() {
        return this.jukebox != null && this.goodBeats();
    }

    public BlockPos getJukebox() {
        return this.jukebox;
    }

    public void setJukebox(BlockPos pos) {
        this.jukebox = pos;
    }

    public ItemStack getRecord() {
        return this.record;
    }

    public void setRecord(ItemStack stack) {
        this.record = stack;
    }

    public boolean goodBeats() {
        return !this.record.isEmpty() && !this.record.is(BAD_DISCS);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5D));
//        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.2D, 10F, 2F, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1D));

        // pranks
        this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.25D, 20, 16F));
        this.goalSelector.addGoal(3, new EatCakeGoal(this));
        this.goalSelector.addGoal(3, new ClimbOnHeadGoal(this));
        this.goalSelector.addGoal(3, new SwitchJukeboxGoal(this, CCItems.MUSIC_DISC_THE_BRIGHT_SIDE.get().getDefaultInstance()));

        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(FOOD_ITEMS), false));

        // register players as pranking targets
        // target players and monsters at range to throw eggs at
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, mob -> this.shouldPrank(mob, Prank.THROW_EGG)));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Mob.class, 10, true, false, mob -> mob instanceof Enemy));
        // target players to climb on
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, 20, true, true, mob -> this.shouldPrank(mob, Prank.CLIMB_HEAD)));
    }

    @Override
    public boolean shouldPrank() {
        return this.tickCount > this.nextPrankTime;
    }

    @Override
    public boolean shouldPrank(LivingEntity entity) {
        return this.shouldPrank() && entity instanceof Player; // TODO: prank "trusted" players less often
    }

    @Override
    public void resetPrankDuration() {
        this.nextPrankTime = this.tickCount + MIN_PRANK_DELAY + this.random.nextInt(MIN_PRANK_DELAY);
        this.resetPrankType();
    }

    @Override
    public void resetPrankType() {
        this.nextPrankType = Prank.get(this.random.nextInt(Prank.count));
    }

    @Override
    public Prank getPrankType() {
        return this.nextPrankType;
    }

    public Decay getEatenState() {
        return Decay.getDecay(this.getHealth() / this.getMaxHealth());
    }

    public enum Decay {
        FULL, HEAD_CHIPPED, HEAD_MISSING, BODY_CHIPPED;

        private static final Decay[] values = Decay.values();

        public static Decay getDecay(float percent) {
            int index = values.length - (Mth.lerpInt(percent, 1, values.length));
            return values[index];
        }

        public String toString() {
            return this.name().toLowerCase();
        }
    }

    public static AttributeSupplier. Builder createAttributes() {
        return TamableAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.ARMOR, 0D)
                .add(Attributes.ATTACK_DAMAGE, 3D)
                .add(Attributes.FOLLOW_RANGE, 16D);
    }
}
