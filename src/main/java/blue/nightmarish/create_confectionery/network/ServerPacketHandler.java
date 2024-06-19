package blue.nightmarish.create_confectionery.network;

import blue.nightmarish.create_confectionery.entity.custom.GingerbreadManEntity;
import blue.nightmarish.create_confectionery.network.clientbound.ClientboundJukeboxRecordItem;
import blue.nightmarish.create_confectionery.network.serverbound.ServerboundJukeboxRecordRequest;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraftforge.network.NetworkEvent;

public class ServerPacketHandler {
    public static void getJukeboxItem(ServerboundJukeboxRecordRequest packet, NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            // get player and level
            ServerPlayer player = context.getSender();
            assert player != null;
            Level level = player.level();
            if (!level.hasChunkAt(packet.pos)) return; // stop if this block isn't loaded
            // get entities
            Entity entity = level.getEntity(packet.gingerbreadManId);
            if (entity instanceof GingerbreadManEntity gingerbreadMan) {
                BlockEntity blockEntity = level.getBlockEntity(packet.pos);
                ItemStack stack = ItemStack.EMPTY;
                if (blockEntity instanceof JukeboxBlockEntity jukeboxEntity)
                    stack = jukeboxEntity.getFirstItem();
                gingerbreadMan.setListeningTo(stack);
                ClientboundJukeboxRecordItem.broadcastJukeboxRecord(gingerbreadMan, stack);
            }
        });
    }
}
