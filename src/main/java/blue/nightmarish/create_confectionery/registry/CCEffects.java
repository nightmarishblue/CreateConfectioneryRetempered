package blue.nightmarish.create_confectionery.registry;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.effect.RestMobEffect;
import blue.nightmarish.create_confectionery.effect.StimulationMobEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CCEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, CreateConfectionery.MOD_ID);
    public static final RegistryObject<MobEffect> REST = EFFECTS.register("rest", RestMobEffect::new);
    public static final RegistryObject<MobEffect> STIMULATION = EFFECTS.register("stimulation", StimulationMobEffect::new);
}
