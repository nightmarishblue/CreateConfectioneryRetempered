package blue.nightmarish.create_confectionery.network.client;

import blue.nightmarish.create_confectionery.block.CaramelBlock;
import blue.nightmarish.create_confectionery.block.CaramelBlockBehavior;
import blue.nightmarish.create_confectionery.network.packet.ClientboundCaramelParticleEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

public class CaramelParticleHandler {
    public static void handleCaramelPacket(ClientboundCaramelParticleEvent event, NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            Entity entity = level.getEntity(event.entityId);
            CaramelBlockBehavior.showParticles(entity, event.numParticles);
        });
    }
}
