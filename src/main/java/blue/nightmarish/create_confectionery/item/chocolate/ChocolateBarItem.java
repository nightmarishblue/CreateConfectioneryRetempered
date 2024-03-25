package blue.nightmarish.create_confectionery.item.chocolate;

import blue.nightmarish.create_confectionery.item.ConfectionItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import java.util.List;

@MethodsReturnNonnullByDefault
public class ChocolateBarItem extends ConfectionItem {
    public ChocolateBarItem(Item.Properties itemProps) {
        super(itemProps);
    }

    public ChocolateBarItem(List<MobEffectInstance> effects) {
        super(effects, ConfectionItem.defaultItemProps(), defaultFoodProps());
    }

    public static FoodProperties.Builder defaultFoodProps() {
        return new FoodProperties.Builder()
                .nutrition(6).saturationMod(0.3F);
    }
}
