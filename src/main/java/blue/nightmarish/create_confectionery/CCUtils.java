package blue.nightmarish.create_confectionery;

import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.tags.IReverseTag;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CCUtils {
    // combine two sets of effects together
    public static List<MobEffectInstance> combineEffects(List<MobEffectInstance> firstEffects, List<MobEffectInstance> secondEffects) {
        int length = firstEffects.size() + secondEffects.size();
        List<MobEffectInstance> output = new ArrayList<>(length);
        for (MobEffectInstance effect : firstEffects)
            output.add(new MobEffectInstance(effect.getEffect(), effect.getDuration() / length, effect.getAmplifier()));
        for (MobEffectInstance effect : secondEffects)
            output.add(new MobEffectInstance(effect.getEffect(), effect.getDuration() / length, effect.getAmplifier()));
        return output;
    }

    // quick way to get a default item
    public static Item genericItem() {
        return new Item(new Item.Properties());
    }

    //
    public static Vec3 offsetVec3(double bound, RandomSource source) {
        Vec3 output = Vec3.ZERO
                .add(source.nextDouble() - 0.5D, source.nextDouble() - 0.5D, source.nextDouble() - 0.5D);
        return output.multiply(bound, bound, bound);
    }

    public static <T> TagKey<T> createTagKey(IForgeRegistry<T> registry, String tagName) {
        ResourceLocation loc = new ResourceLocation("forge", tagName);
        return registry.tags().createOptionalTagKey(loc, Collections.emptySet());
    }

    public static final TagKey<FluidType> choc = createTagKey(ForgeRegistries.FLUID_TYPES.get(), "chocolate");

    public static boolean queryFluidType(FluidType fluidType, Set<TagKey<FluidType>> targetTags) {
        IReverseTag<FluidType> tagsOfFluid = getTags(fluidType);
        if (tagsOfFluid == null) return false;
        if (tagsOfFluid.containsTag(choc)) return true;
        return false;
    }

    @Nullable
    public static IReverseTag<FluidType> getTags(FluidType fluidType) {
        IForgeRegistry<FluidType> fluidTypeReg = ForgeRegistries.FLUID_TYPES.get();
        return fluidTypeReg.tags().getReverseTag(fluidType).orElse(null);
    }

    public static final float baseWaterFogDistance = 96F;
    public static float getChocolateFogMult() {
        if (AllConfigs.client().specification == null) return 1F;
        return AllConfigs.client().chocolateTransparencyMultiplier.getF();
    }
}
