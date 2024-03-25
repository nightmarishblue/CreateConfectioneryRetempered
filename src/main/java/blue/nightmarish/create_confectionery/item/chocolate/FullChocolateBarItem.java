package blue.nightmarish.create_confectionery.item.chocolate;

import blue.nightmarish.create_confectionery.item.ConfectionItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FullChocolateBarItem extends ConfectionItem {
    public FullChocolateBarItem(List<MobEffectInstance> effects) {
        super(effects, defaultItemProps(), defaultFoodProps());
    }

    public static Item.Properties defaultItemProps() {
        return ConfectionItem.defaultItemProps().durability(12);
    }

    public static FoodProperties.Builder defaultFoodProps() {
        return ChocolateBarItem.defaultFoodProps().fast();
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        super.finishUsingItem(pStack.copy(), pLevel, pLivingEntity);
        pStack.hurtAndBreak(1, pLivingEntity, entity -> {});
        return pStack;
    }
}
