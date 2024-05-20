package blue.nightmarish.create_confectionery.item.hot_chocolate;

import blue.nightmarish.create_confectionery.item.SweetDrinkItem;
import blue.nightmarish.create_confectionery.registry.CCFluids;
import com.simibubi.create.content.fluids.OpenEndedPipe;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
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

    static {
        OpenEndedPipe.registerEffectHandler(new HotChocolateHandler());
    }

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

    public static class HotChocolateHandler implements OpenEndedPipe.IEffectHandler {
        @Override
        public boolean canApplyEffects(OpenEndedPipe pipe, FluidStack fluid) {
            return fluid.getFluid().isSame(CCFluids.HOT_CHOCOLATE.get());
        }

        @Override
        public void applyEffects(OpenEndedPipe pipe, FluidStack fluid) {
            Level world = pipe.getWorld();
            if (world.getGameTime() % 20 != 0)
                return;
            List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, pipe.getAOE(), entity -> !entity.fireImmune());
            for (LivingEntity entity : entities)
                entity.hurt(world.damageSources().onFire(), 1f);
        }
    }
}
