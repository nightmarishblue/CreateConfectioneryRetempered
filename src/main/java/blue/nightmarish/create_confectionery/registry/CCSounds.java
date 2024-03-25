package blue.nightmarish.create_confectionery.registry;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CCSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister
            .create(ForgeRegistries.SOUND_EVENTS, CreateConfectionery.MOD_ID);

    public static final RegistryObject<SoundEvent> THE_BRIGHT_SIDE = registerNew("music_disc.the_bright_side");

    public static final RegistryObject<SoundEvent> GINGERBREAD_MAN_REPAIR = registerNew("entity.gingerbread_man.repair");
    public static final RegistryObject<SoundEvent> GINGERBREAD_MAN_AMBIENT = registerNew("entity.gingerbread_man.ambient");
    public static final RegistryObject<SoundEvent> GINGERBREAD_MAN_HURT = registerNew("entity.gingerbread_man.hurt");
    public static final RegistryObject<SoundEvent> GINGERBREAD_MAN_DEATH = registerNew("entity.gingerbread_man.death");

    public static RegistryObject<SoundEvent> registerNew(String name) {
        ResourceLocation id = new ResourceLocation(CreateConfectionery.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }
}
