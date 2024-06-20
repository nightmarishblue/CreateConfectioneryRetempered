package blue.nightmarish.create_confectionery.entity.client;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.entity.custom.GingerbreadManEntity;
import blue.nightmarish.create_confectionery.entity.custom.GingerbreadManEntity.GingerbreadManDecay;
import blue.nightmarish.create_confectionery.entity.model.GingerbreadManModel;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GingerbreadManRenderer extends MobRenderer<GingerbreadManEntity, GingerbreadManModel> {
    public static final List<ResourceLocation> TEXTURE_TRACK = new ArrayList<>(GingerbreadManDecay.values().length);
    public static final String TEXTURE_LOC = "textures/entities/gingerbread_man_%s.png";
    public GingerbreadManRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GingerbreadManModel(pContext.bakeLayer(GingerbreadManModel.GINGERBREAD_MAN_LAYER)), 0.2F);
        GingerbreadManDecay[] decayStates = GingerbreadManDecay.values();
        for (int i = 0; i < decayStates.length; i++) {
            TEXTURE_TRACK.add(i, new ResourceLocation(CreateConfectionery.MOD_ID, String.format(TEXTURE_LOC, decayStates[i])));
        }
    }

    @Override
    public ResourceLocation getTextureLocation(GingerbreadManEntity pEntity) {
        return TEXTURE_TRACK.get(pEntity.getEatenState().ordinal());
    }
}
