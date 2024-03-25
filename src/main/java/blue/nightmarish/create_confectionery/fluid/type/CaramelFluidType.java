package blue.nightmarish.create_confectionery.fluid.type;

import blue.nightmarish.create_confectionery.CCUtils;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CaramelFluidType extends SweetFluidType {
    public CaramelFluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, Vector3f fogColor) {
        super(stillTexture, flowingTexture, fogColor);
    }

    @Override
    public void modifyFog() {
        float modifier = 1F / 36F * CCUtils.getChocolateFogMult();
        if (modifier == 1F) return;
        RenderSystem.setShaderFogShape(FogShape.CYLINDER);
        RenderSystem.setShaderFogStart(-8F);
        RenderSystem.setShaderFogEnd(CCUtils.baseWaterFogDistance * modifier);
    }

    // this is copying Create's values for honey (Create doesn't have a class to extend)
    public static Properties getTypeProps() {
        return SweetFluidType.getTypeProps()
                .viscosity(2000)
                .density(1400)
                .motionScale(0.0035D);
    }
}
