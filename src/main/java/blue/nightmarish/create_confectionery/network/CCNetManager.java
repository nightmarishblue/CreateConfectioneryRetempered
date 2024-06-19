package blue.nightmarish.create_confectionery.network;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.network.clientbound.ClientboundCaramelParticleEvent;
import blue.nightmarish.create_confectionery.network.clientbound.ClientboundGingerbreadManData;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class CCNetManager {
    private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(CreateConfectionery.MOD_ID, "network"))
            .networkProtocolVersion(() -> "1.0")
            .clientAcceptedVersions(str -> true)
            .serverAcceptedVersions(str -> true)
            .simpleChannel();

    private static int packetId = 0;

    static int id() {
        return packetId++;
    }

    public static void register() {
        INSTANCE.messageBuilder(ClientboundCaramelParticleEvent.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundCaramelParticleEvent::new)
                .encoder(ClientboundCaramelParticleEvent::toBytes)
                .consumerMainThread(ClientboundCaramelParticleEvent::handle)
                .add();

        INSTANCE.messageBuilder(ClientboundGingerbreadManData.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundGingerbreadManData::new)
                .encoder(ClientboundGingerbreadManData::toBytes)
                .consumerMainThread(ClientboundGingerbreadManData::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void broadcastToPlayersTrackingEntity(MSG message, Entity entity) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), message);
    }

    public static <MSG> void broadcastToPlayersTrackingChunk(MSG message, Level level, BlockPos pos) {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), message);
    }
}
