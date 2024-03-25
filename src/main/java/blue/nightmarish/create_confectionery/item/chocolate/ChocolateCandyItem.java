package blue.nightmarish.create_confectionery.item.chocolate;

import blue.nightmarish.create_confectionery.item.ConfectionItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import java.util.List;

@MethodsReturnNonnullByDefault
public class ChocolateCandyItem extends ConfectionItem {
    public ChocolateCandyItem(Properties pProperties) {
        super(pProperties);
    }

    public ChocolateCandyItem(List<MobEffectInstance> effects) {
        super(effects, defaultItemProps(), defaultFoodProps());
    }

    public static Item.Properties defaultItemProps() {
        return ConfectionItem.defaultItemProps().stacksTo(16);
    }

    public static FoodProperties.Builder defaultFoodProps() {
        return new FoodProperties.Builder()
                .nutrition(7).saturationMod(0.6F);
    }
}
