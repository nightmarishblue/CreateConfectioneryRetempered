package blue.nightmarish.create_confectionery.fluid.type;

import blue.nightmarish.create_confectionery.CCUtils;
import blue.nightmarish.create_confectionery.CreateConfectionery;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GenericChocolateFluidType extends SweetFluidType {
    public GenericChocolateFluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, Vector3f fogColor) {
        super(stillTexture, flowingTexture, fogColor);
    }

    @Override
    public void modifyFog() {
        float modifier = 1F / 32F * CCUtils.getChocolateFogMult();
        if (modifier == 1F) return;
        RenderSystem.setShaderFogShape(FogShape.CYLINDER);
        RenderSystem.setShaderFogStart(-8F);
        RenderSystem.setShaderFogEnd(CCUtils.baseWaterFogDistance * modifier);
    }

    // this is copying Create's values for chocolate (Create doesn't have a class to extend)
    public static FluidType.Properties getTypeProps() {
        return SweetFluidType.getTypeProps()
                .viscosity(1500)
                .density(1400)
                .motionScale(0.007D);
    }
}
