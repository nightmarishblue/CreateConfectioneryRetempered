package blue.nightmarish.create_confectionery.registry;

import blue.nightmarish.create_confectionery.CCConstants;
import blue.nightmarish.create_confectionery.CCUtils;
import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.fluid.type.CaramelFluidType;
import blue.nightmarish.create_confectionery.fluid.type.GenericChocolateFluidType;
import com.simibubi.create.foundation.utility.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class CCFluidTypes {
    public static DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister
            .create(ForgeRegistries.Keys.FLUID_TYPES, CreateConfectionery.MOD_ID);

    public static RegistryObject<FluidType> BLACK_CHOCOLATE_TYPE = registerChocolate("black_chocolate", CCConstants.BLACK_CHOC_COLOR);
    public static RegistryObject<FluidType> RUBY_CHOCOLATE_TYPE = registerChocolate("ruby_chocolate", CCConstants.RUBY_CHOC_COLOR);//C35D9D);
    public static RegistryObject<FluidType> WHITE_CHOCOLATE_TYPE = registerChocolate("white_chocolate", CCConstants.WHITE_CHOC_COLOR);//D0945E);

    public static RegistryObject<FluidType> CARAMEL_TYPE = registerFluidType("caramel", CCConstants.CARAMEL_COLOR, CaramelFluidType::new);

    // this one is never placed anywhere so its colour does not apply
    public static RegistryObject<FluidType> HOT_CHOCOLATE_TYPE = registerChocolate("hot_chocolate", CCConstants.WHITE_CHOC_COLOR);

    public static RegistryObject<FluidType> registerChocolate(String name, int fogColor) {
        return registerFluidType(name, fogColor, GenericChocolateFluidType::new);
    }

    public static RegistryObject<FluidType> registerFluidType(String name, int fogColor, FluidTypeConstructor constructor) {
        Vector3f color = new Color(fogColor, false).asVectorF();
        ResourceLocation stillTexture = new ResourceLocation(CreateConfectionery.MOD_ID, "fluid/" + name + "_still");
        ResourceLocation flowTexture = new ResourceLocation(CreateConfectionery.MOD_ID, "fluid/" + name + "_flow");
        return FLUID_TYPES.register(name, () -> constructor.apply(stillTexture, flowTexture, color));
    }

    @FunctionalInterface
    public interface FluidTypeConstructor {
        FluidType apply(ResourceLocation still, ResourceLocation flowing, Vector3f color);
    }
}
