package blue.nightmarish.create_confectionery.item.hot_chocolate;

import blue.nightmarish.create_confectionery.item.SweetDrinkItem;
import com.simibubi.create.foundation.utility.Color;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class PlainHotChocolateItem extends SweetDrinkItem {
    public PlainHotChocolateItem() {
        super(List.of(), defaultItemProps(), defaultFoodProps());
    }

    public float getProgress(ItemStack stack) {
        if (!stack.hasTag())
            return 0;
        CompoundTag tag = stack.getTag();
        if (!tag.contains("SequencedAssembly"))
            return 0;
        return tag.getCompound("SequencedAssembly")
                .getFloat("Progress");
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(getProgress(stack) * 13);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return Color.mixColors(0xFF_FFC074, 0xFF_46FFE0, getProgress(stack));
    }

    public static Properties defaultItemProps() {
        return new Properties().stacksTo(1);
    }

    public static FoodProperties.Builder defaultFoodProps() {
        return SweetDrinkItem.defaultFoodProps().nutrition(3).saturationMod(.2F);
    }
}
