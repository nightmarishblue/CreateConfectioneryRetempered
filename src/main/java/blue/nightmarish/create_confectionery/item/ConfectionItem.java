package blue.nightmarish.create_confectionery.item;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class ConfectionItem extends Item {
    public List<MobEffectInstance> effects = new ArrayList<>();

    // you probably shouldn't call this without factoring in the effects above
    public ConfectionItem(Properties pProperties) {
        super(pProperties);
    }

    public ConfectionItem(List<MobEffectInstance> effects, Item.Properties itemProps, FoodProperties.Builder foodBuilder) {
        this(constructProps(effects, itemProps, foodBuilder));
        this.effects = effects;
    }

    public ConfectionItem(List<MobEffectInstance> effects) {
        this(effects, defaultItemProps(), defaultFoodProps());
    }

    public static Item.Properties constructProps(List<MobEffectInstance> effects, Item.Properties itemProps, FoodProperties.Builder foodBuilder) {
        effects.forEach(effect -> foodBuilder.effect(() -> effect, 1));
        return itemProps.food(foodBuilder.build());
    }

    public static Item.Properties defaultItemProps() {
        return new Item.Properties();
    }

    public static FoodProperties.Builder defaultFoodProps() {
        return new FoodProperties.Builder();
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
