package blue.nightmarish.create_confectionery;

import blue.nightmarish.create_confectionery.registry.CCEffects;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CCConstants {
    public static final int DARK_CHOC_COLOR = 0x3F1916;
    public static final int RUBY_CHOC_COLOR = 0xA14D82;
    public static final int WHITE_CHOC_COLOR = 0xB07C4F;
    public static final int CARAMEL_COLOR = 0xad5626;

    public static final int STIMULATION_DURATION = 20 * 120;
    public static final int HEALTH_DURATION = 20 * 90;
    public static final int REST_DURATION = 20 * 60 * 3;
    public static final int SPEED_DURATION = 20 * 60;
    public static final int RESISTANCE_DURATION = 20 * 15;
    public static final int HASTE_DURATION = 20 * 60;

    public static final List<MobEffectInstance> DARK_CHOC_EFFECTS = List.of(new MobEffectInstance(CCEffects.STIMULATION.get(), STIMULATION_DURATION));
    public static final List<MobEffectInstance> RUBY_CHOC_EFFECTS = List.of(new MobEffectInstance(MobEffects.HEALTH_BOOST, HEALTH_DURATION));
    public static final List<MobEffectInstance> WHITE_CHOC_EFFECTS = List.of(new MobEffectInstance(CCEffects.REST.get(), REST_DURATION));
    public static final List<MobEffectInstance> HOT_CHOC_EFFECTS = List.of(new MobEffectInstance((CCEffects.REST.get()), REST_DURATION));
    public static final List<MobEffectInstance> CARAMEL_EFFECTS = List.of(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, SPEED_DURATION));
    public static final List<MobEffectInstance> GINGERBREAD_EFFECTS = List.of(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, RESISTANCE_DURATION));
    public static final List<MobEffectInstance> CANDY_CANE_EFFECTS = List.of(new MobEffectInstance(MobEffects.DIG_SPEED, HASTE_DURATION));
}
