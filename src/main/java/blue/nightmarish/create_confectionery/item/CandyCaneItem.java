package blue.nightmarish.create_confectionery.item;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Rarity;

import java.util.List;

@MethodsReturnNonnullByDefault
public class CandyCaneItem extends ConfectionItem {
    public CandyCaneItem(Properties pProperties) {
        super(pProperties);
    }

    public CandyCaneItem(List<MobEffectInstance> effects) {
        super(effects, ConfectionItem.defaultItemProps().rarity(Rarity.UNCOMMON), defaultFoodProps());
    }

    public static FoodProperties.Builder defaultFoodProps() {
        return ConfectionItem.defaultFoodProps().nutrition(2).saturationMod(0.2F).fast();
    }
}
