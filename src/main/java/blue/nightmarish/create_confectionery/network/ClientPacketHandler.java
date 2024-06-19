package blue.nightmarish.create_confectionery.network;

import blue.nightmarish.create_confectionery.block.CaramelBlockBehavior;
import blue.nightmarish.create_confectionery.entity.custom.GingerbreadManEntity;
import blue.nightmarish.create_confectionery.network.clientbound.ClientboundCaramelParticleEvent;
import blue.nightmarish.create_confectionery.network.clientbound.ClientboundJukeboxRecordItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

public class ClientPacketHandler {
    public static void renderCaramelParticles(ClientboundCaramelParticleEvent event, NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            Entity entity = level.getEntity(event.entityId);
            CaramelBlockBehavior.showParticles(entity, event.numParticles);
        });
    }

    public static void updateJukeboxContents(ClientboundJukeboxRecordItem packet, NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            Entity entity = level.getEntity(packet.gingerbreadManId);
            if (entity instanceof GingerbreadManEntity gingerbreadMan) {
                gingerbreadMan.setListeningTo(packet.stack);
            }
        });
    }
}
