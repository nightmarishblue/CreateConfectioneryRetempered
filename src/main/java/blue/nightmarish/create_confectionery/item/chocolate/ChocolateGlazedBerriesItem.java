package blue.nightmarish.create_confectionery.item.chocolate;

import blue.nightmarish.create_confectionery.item.ConfectionItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChocolateGlazedBerriesItem extends ConfectionItem {
    public ChocolateGlazedBerriesItem(List<MobEffectInstance> effects) {
        super(effects, defaultItemProps(), defaultFoodProps());
    }

    public static FoodProperties.Builder defaultFoodProps() {
        return new FoodProperties.Builder()
                .nutrition(7).saturationMod(0.8F);
    }
}
