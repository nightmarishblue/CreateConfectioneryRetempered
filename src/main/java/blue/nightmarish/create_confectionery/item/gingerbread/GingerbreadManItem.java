package blue.nightmarish.create_confectionery.item.gingerbread;

import blue.nightmarish.create_confectionery.item.ConfectionItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import java.util.List;

@MethodsReturnNonnullByDefault
public class GingerbreadManItem extends ConfectionItem {
    public GingerbreadManItem(Properties pProperties) {
        super(pProperties);
    }

    public GingerbreadManItem(List<MobEffectInstance> effects) {
        super(effects, defaultItemProps(), defaultFoodProps());
    }

    public static Item.Properties defaultItemProps() {
        return ConfectionItem.defaultItemProps().stacksTo(16);
    }

    public static FoodProperties.Builder defaultFoodProps() {
        return ConfectionItem.defaultFoodProps()
                .nutrition(2).saturationMod(0.3F).fast();
    }
}
