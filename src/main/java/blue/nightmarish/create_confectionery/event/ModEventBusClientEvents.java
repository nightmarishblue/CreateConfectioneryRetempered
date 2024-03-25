package blue.nightmarish.create_confectionery.event;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.entity.model.GingerbreadManModel;
import blue.nightmarish.create_confectionery.entity.client.GingerbreadManRenderer;
import blue.nightmarish.create_confectionery.registry.CCEntities;
import blue.nightmarish.create_confectionery.registry.CCFluids;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = CreateConfectionery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GingerbreadManModel.GINGERBREAD_MAN_LAYER, GingerbreadManModel::createBodyLayers);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(CCEntities.LITTLE_GINGERBREAD_MAN.get(), GingerbreadManRenderer::new);

        CCFluids.FLUIDS.getEntries().forEach(fluid -> {
            ItemBlockRenderTypes.setRenderLayer(fluid.get(), RenderType.translucent());
        });
    }
}
