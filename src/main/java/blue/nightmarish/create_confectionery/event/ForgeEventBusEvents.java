package blue.nightmarish.create_confectionery.event;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.registry.CCItems;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateConfectionery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventBusEvents {
    @SubscribeEvent
    public static void registerWanderingTrades(WandererTradesEvent event) {
        event.getGenericTrades().add(new BasicItemListing(3, CCItems.GINGERBREAD_MAN.get().getDefaultInstance(), 10, 5));
        event.getGenericTrades().add(new BasicItemListing(5, CCItems.CANDY_CANE.get().getDefaultInstance(), 5, 10));
    }
}
