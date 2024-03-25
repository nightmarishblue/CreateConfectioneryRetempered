package blue.nightmarish.create_confectionery.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import java.util.List;

public class HoneyCandyItem extends Item {
    public HoneyCandyItem(Properties pProperties) {
        super(pProperties);
    }

    public HoneyCandyItem() {
        super(new Properties().food(defaultFoodProps().build()));
    }

    public static FoodProperties.Builder defaultFoodProps() {
        return ConfectionItem.defaultFoodProps().nutrition(3).saturationMod(0.8F).fast();
    }
}
