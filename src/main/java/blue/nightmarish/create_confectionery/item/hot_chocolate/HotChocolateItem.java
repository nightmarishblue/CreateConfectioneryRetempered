package blue.nightmarish.create_confectionery.item.hot_chocolate;

import blue.nightmarish.create_confectionery.CCConstants;
import blue.nightmarish.create_confectionery.item.SweetDrinkItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class HotChocolateItem extends SweetDrinkItem {
    public HotChocolateItem() {
        super(CCConstants.HOT_CHOC_EFFECTS, defaultItemProps(), defaultFoodProps());
    }

    public static Properties defaultItemProps() {
        return SweetDrinkItem.defaultItemProps();
    }

    public static FoodProperties.Builder defaultFoodProps() {
        return SweetDrinkItem.defaultFoodProps().nutrition(6).saturationMod(.4F);
    }
}
