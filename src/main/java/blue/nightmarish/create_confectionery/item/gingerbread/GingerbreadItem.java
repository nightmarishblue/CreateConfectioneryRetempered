package blue.nightmarish.create_confectionery.item.gingerbread;

import blue.nightmarish.create_confectionery.item.ConfectionItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Rarity;

import java.util.List;

@MethodsReturnNonnullByDefault
public class GingerbreadItem extends ConfectionItem {
    public GingerbreadItem(Properties pProperties) {
        super(pProperties);
    }

    public GingerbreadItem(List<MobEffectInstance> effects) {
        super(effects, ConfectionItem.defaultItemProps().rarity(Rarity.UNCOMMON), defaultFoodProps());
    }

    public static FoodProperties.Builder defaultFoodProps() {
        return ConfectionItem.defaultFoodProps()
                .nutrition(4).saturationMod(0.3F);
    }
}
