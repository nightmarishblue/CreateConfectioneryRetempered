package blue.nightmarish.create_confectionery.entity.custom;

import blue.nightmarish.create_confectionery.CCUtils;
import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.registry.CCFluidTypes;
import blue.nightmarish.create_confectionery.registry.CCItems;
import blue.nightmarish.create_confectionery.registry.CCSounds;
import com.simibubi.create.AllFluids;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Set;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GingerbreadManEntity extends TamableAnimal {
    // what it counts as when you gnaw on a gingerbread man
    public static final ItemStack CONSUME_ITEM = new ItemStack(CCItems.GINGERBREAD.get());
    // the particles that come out when you take a bite of this mob
    public static final ItemStack EATEN_PARTICLES = new ItemStack(CCItems.GINGERBREAD_MAN.get());
    public static final TagKey<Item> FOOD_ITEMS = ItemTags.create(new ResourceLocation(CreateConfectionery.MOD_ID, "gingerbread_man_foods"));
    public static final TagKey<Item> TAME_ITEMS = ItemTags.create(new ResourceLocation(CreateConfectionery.MOD_ID, "gingerbread_man_tame_items"));
    public static final Set<FluidType> CAN_SWIM_IN = Set.of(AllFluids.CHOCOLATE.getType(), ForgeMod.MILK_TYPE.get(),
            CCFluidTypes.BLACK_CHOCOLATE_TYPE.get());

    public GingerbreadManEntity(EntityType<GingerbreadManEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setMaxUpStep(0.6F);
        this.xpReward = 0;
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        // 1. this is client side and we can't do anything but guess the outcome
        if (this.level().isClientSide) {
            boolean flag = this.isOwnedBy(pPlayer) || this.isTame() || this.isFood(itemstack) && !this.isTame();
            this.repairParticles(itemstack);
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        }

        // 2. that was food you just gave it; try to feed it
        if (this.canEat(itemstack)) {
            FoodProperties foodProps = itemstack.getFoodProperties(this);
            this.heal(foodProps != null ? foodProps.getNutrition() : 0.5F); // since this thing can eat sugar, we want a fallback
            this.level().playSound(null, this.blockPosition(), CCSounds.GINGERBREAD_MAN_EAT.get(), SoundSource.NEUTRAL, 0.5F, 1.5F);

            if (!pPlayer.getAbilities().instabuild) itemstack.shrink(1);

            this.gameEvent(GameEvent.EAT, this);
            return InteractionResult.SUCCESS;
        }

        // 2. this is tame and you can issue a command
        if (this.isOwnedBy(pPlayer)) {
            InteractionResult interactionresult = super.mobInteract(pPlayer, pHand);
            // if something else didn't happen, try to change its state
            if (!interactionresult.consumesAction()) {
                this.setOrderedToSit(!this.isOrderedToSit());
                CreateConfectionery.LOGGER.info("f");
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
                return InteractionResult.SUCCESS;
            }
            return interactionresult; // otherwise report failure
        }

        // 3. this isn't tame and you can try to tame it
        if (itemstack.is(TAME_ITEMS)) {
            if (!pPlayer.getAbilities().instabuild) itemstack.shrink(1);

            if (this.random.nextInt(8) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, pPlayer)) {
                this.tame(pPlayer);
                this.navigation.stop();
                this.setTarget(null);
                this.setOrderedToSit(false); // unlike wolves, the gingerbread man doesn't sit, so he follows instead
                this.level().broadcastEntityEvent(this, (byte)7);
            } else {
                this.level().broadcastEntityEvent(this, (byte)6);
            }

            return InteractionResult.SUCCESS;
        }
        // 4. ???
        return super.mobInteract(pPlayer, pHand);
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
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pAmount <= 0) return super.hurt(pSource, pAmount);

        Entity attacker = pSource.getDirectEntity();
        if (this.level().isClientSide() && !pSource.isIndirect() && attacker != null)
            this.spawnItemParticles(EATEN_PARTICLES); // spawn chips if this was a direct physical attack
        if (attacker instanceof Player player && player.getMainHandItem().isEmpty())
            player.eat(player.level(), CONSUME_ITEM.copy());

        return super.hurt(pSource, pAmount);
    }

    // gingerbread men can only swim in chocolate and milk
    @Override
    public void jumpInFluid(FluidType type) {
        if (CAN_SWIM_IN.contains(type)) {
            super.jumpInFluid(type);
        }
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.getEatenState() < 2 ? CCSounds.GINGERBREAD_MAN_AMBIENT.get() : null;
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

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(FOOD_ITEMS);
    }

    public boolean canEat(ItemStack stack) {
        return this.isFood(stack) && this.getHealth() < this.getMaxHealth();
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public boolean canFallInLove() {
        return false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.2D, 10F, 2F, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1D));
        this.goalSelector.addGoal(4, new OwnerHurtByTargetGoal(this));
        this.goalSelector.addGoal(5, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new FloatGoal(this));
        this.goalSelector.addGoal(8, new TemptGoal(this, 1.2D, Ingredient.of(FOOD_ITEMS), false));
    }

    public int getEatenState() {
        return 3 - Mth.lerpInt(this.getHealth() / this.getMaxHealth(), 0, 3);
    }

    public enum eatenAmount {
        FULL, HEAD_CHIPPED, HEAD_MISSING, CHEST_CHIPPED;
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
