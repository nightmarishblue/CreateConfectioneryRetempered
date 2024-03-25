package blue.nightmarish.create_confectionery.item.chocolate;

import blue.nightmarish.create_confectionery.item.ConfectionItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import java.util.List;

@MethodsReturnNonnullByDefault
public class ChocolateGlazedMarshmallowItem extends ConfectionItem {
    public ChocolateGlazedMarshmallowItem(List<MobEffectInstance> effects) {
        super(effects, defaultItemProps(), defaultFoodProps());
    }

    public ChocolateGlazedMarshmallowItem(List<MobEffectInstance> effects, Item.Properties itemProps, FoodProperties.Builder foodBuilder) {
        super(effects, itemProps, foodBuilder);
    }

    public static FoodProperties.Builder defaultFoodProps() {
        return new FoodProperties.Builder()
                .nutrition(3).saturationMod(0.3F).alwaysEat().fast();
    }
}
