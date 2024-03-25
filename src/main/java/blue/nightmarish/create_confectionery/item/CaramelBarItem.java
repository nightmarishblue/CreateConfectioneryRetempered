package blue.nightmarish.create_confectionery.item;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

import java.util.List;

@MethodsReturnNonnullByDefault
public class CaramelBarItem extends ConfectionItem {
    public CaramelBarItem(Properties pProperties) {
        super(pProperties);
    }

    public CaramelBarItem(List<MobEffectInstance> effects) {
        super(effects, ConfectionItem.defaultItemProps(), defaultFoodProps());
    }

    public static FoodProperties.Builder defaultFoodProps() {
        return new FoodProperties.Builder()
                .nutrition(6).saturationMod(0.3F);
    }
}
