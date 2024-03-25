package blue.nightmarish.create_confectionery.registry;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.entity.custom.GingerbreadManEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CCEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CreateConfectionery.MOD_ID);

    public static final RegistryObject<EntityType<GingerbreadManEntity>> LITTLE_GINGERBREAD_MAN = ENTITY_TYPES
            .register("little_gingerbread_man", () -> EntityType.Builder.of(GingerbreadManEntity::new, MobCategory.MISC)
                    .sized(0.5F, 1F)
                    .build("little_gingerbread_man"));
}
