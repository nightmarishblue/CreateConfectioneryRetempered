package blue.nightmarish.create_confectionery.item;

import blue.nightmarish.create_confectionery.CCConstants;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class HotChocolateItem extends Item {
    public List<MobEffectInstance> effects = new ArrayList<>();

    public HotChocolateItem() {
        this(CCConstants.HOT_CHOC_EFFECTS);
    }
    // you probably shouldn't call this without factoring in the effects above
    public HotChocolateItem(Properties pProperties) {
        super(pProperties);
    }

    public HotChocolateItem(List<MobEffectInstance> effects, Properties itemProps, FoodProperties.Builder foodBuilder) {
        this(constructProps(effects, itemProps, foodBuilder));
        this.effects = effects;
    }

    public HotChocolateItem(List<MobEffectInstance> effects) {
        this(effects, defaultItemProps(), defaultFoodProps());
    }

    public static Properties constructProps(List<MobEffectInstance> effects, Properties itemProps, FoodProperties.Builder foodBuilder) {
        effects.forEach(effect -> foodBuilder.effect(() -> effect, 1));
        return itemProps.food(foodBuilder.build());
    }

    public static Properties defaultItemProps() {
        return new Properties().stacksTo(16);
    }

    public static FoodProperties.Builder defaultFoodProps() {
        return new FoodProperties.Builder().nutrition(6).saturationMod(.4F).alwaysEat();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        super.finishUsingItem(stack, level, entity);
        Player player = (entity instanceof Player) ? (Player) entity : null;


        if (player instanceof ServerPlayer)
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);

        ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);

        if (stack.isEmpty()) {
            return bottle;
        }

        if (player != null && !player.getAbilities().instabuild) {
            if (!player.getInventory().add(bottle)) {
                player.drop(bottle, false);
            }
        }

        return stack;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    public List<MobEffectInstance> getEffects() {
        return this.effects;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        if (!this.getEffects().isEmpty())
            PotionUtils.addPotionTooltip(this.getEffects(), pTooltip, 1f);
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}
