package blue.nightmarish.create_confectionery.network.clientbound;

import blue.nightmarish.create_confectionery.network.CCNetManager;
import blue.nightmarish.create_confectionery.network.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
public class ClientboundCaramelParticleEvent {
    public final int entityId; // the entity causing the particles
    public final int numParticles; // the number of particles to spawn

    public ClientboundCaramelParticleEvent(int entityId, int numParticles) {
        this.entityId = entityId;
        this.numParticles = numParticles;
    }

    public ClientboundCaramelParticleEvent(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.numParticles = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeInt(this.numParticles);
    }

    public static boolean handle(ClientboundCaramelParticleEvent message, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientPacketHandler.renderCaramelParticles(message, context);
        });
        return true;
    }

    public static void broadcastCaramelParticles(Entity entity, int amount) {
        CCNetManager.broadcastToPlayersTrackingEntity(new ClientboundCaramelParticleEvent(entity.getId(), amount), entity);
    }
}
