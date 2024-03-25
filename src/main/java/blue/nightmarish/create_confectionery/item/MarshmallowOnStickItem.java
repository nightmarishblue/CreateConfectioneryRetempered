package blue.nightmarish.create_confectionery.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class MarshmallowOnStickItem extends Item {
    public MarshmallowOnStickItem(Item.Properties pProperties) {
        super(pProperties);
    }

    public MarshmallowOnStickItem(int nutrition, float saturationMod) {
        this(constructProps(nutrition, saturationMod));
    }

    public static Item.Properties constructProps(int nutrition, float saturationMod) {
        Item.Properties props = new Item.Properties().stacksTo(16);
        return props.food(new FoodProperties.Builder()
                .nutrition(nutrition).saturationMod(saturationMod).build());
    }
}
