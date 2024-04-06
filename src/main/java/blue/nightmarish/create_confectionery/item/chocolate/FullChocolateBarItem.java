package blue.nightmarish.create_confectionery.item.chocolate;

import blue.nightmarish.create_confectionery.item.ConfectionItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FullChocolateBarItem extends ConfectionItem {
    public static int MAX_EATEN_AMOUNT = 12; // the number of times this item can be gnawed on until it is consumed

    public FullChocolateBarItem(List<MobEffectInstance> effects) {
        super(effects, defaultItemProps(), defaultFoodProps());
    }

    public static Item.Properties defaultItemProps() {
        return ConfectionItem.defaultItemProps().stacksTo(1);
    }

    public static FoodProperties.Builder defaultFoodProps() {
        return ChocolateBarItem.defaultFoodProps().fast();
    }

    // the chocolate bar's equivalent to what would be durability
    public int getEatenAmount(ItemStack stack) {
        return !stack.hasTag() ? 0 : stack.getTag().getInt("Eaten");
    }

    public void setEatenAmount(ItemStack stack, int amount) {
        stack.getOrCreateTag().putInt("Eaten", Math.max(0, amount));
    }

    // we must also override these
    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return this.getEatenAmount(pStack) > 0;
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        return Math.round(13.0F - (this.getEatenAmount(pStack) * 13.0F / MAX_EATEN_AMOUNT));
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        float f = Math.max(0.0F, (float) (MAX_EATEN_AMOUNT - this.getEatenAmount(pStack)) / MAX_EATEN_AMOUNT);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        super.finishUsingItem(pStack.copy(), pLevel, pLivingEntity);
        if (!pLevel.isClientSide() && !(pLivingEntity instanceof Player && ((Player) pLivingEntity).getAbilities().instabuild)) {
            int newEatenAmount = this.getEatenAmount(pStack) + 1;
            if (newEatenAmount >= 12) {
                pStack.shrink(1);
            } else {
                this.setEatenAmount(pStack, newEatenAmount);
            }
        }
        return pStack;
    }
}
