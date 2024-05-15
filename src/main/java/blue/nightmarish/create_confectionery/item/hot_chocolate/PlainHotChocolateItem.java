package blue.nightmarish.create_confectionery.item.hot_chocolate;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.item.SweetDrinkItem;
import com.simibubi.create.foundation.utility.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class PlainHotChocolateItem extends SweetDrinkItem {
    public PlainHotChocolateItem() {
        super(List.of(), defaultItemProps(), defaultFoodProps());
    }

//    public static Properties defaultItemProps() {
//        return new Properties().stacksTo(1);
//    }

    public static FoodProperties.Builder defaultFoodProps() {
        return SweetDrinkItem.defaultFoodProps().nutrition(3).saturationMod(.2F);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        if (!(pStack.hasTag() && pStack.getTag().contains("SequencedAssembly")))
            return;
        pTooltip.add(Component.translatable("item.create_confectionery.plain_hot_chocolate.tooltip.hint").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        pTooltip.add(Component.literal("Create Confectionery").withStyle(ChatFormatting.BLUE)); // some tomfuckery goes on with Create and removing this tooltip, and I have as yet not found it
    }
}
