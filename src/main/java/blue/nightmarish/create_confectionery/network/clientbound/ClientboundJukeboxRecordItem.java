package blue.nightmarish.create_confectionery.network.clientbound;

import blue.nightmarish.create_confectionery.entity.custom.GingerbreadManEntity;
import blue.nightmarish.create_confectionery.network.CCNetManager;
import blue.nightmarish.create_confectionery.network.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
public class ClientboundJukeboxRecordItem {
    public final int gingerbreadManId;
    public final ItemStack stack;

    public ClientboundJukeboxRecordItem(int gingerbreadManId, ItemStack stack) {
        this.gingerbreadManId = gingerbreadManId;
        this.stack = stack;
    }

    public ClientboundJukeboxRecordItem(FriendlyByteBuf buf) {
        this.gingerbreadManId = buf.readInt();
        this.stack = buf.readItem();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(gingerbreadManId);
        buf.writeItem(stack);
    }

    public static boolean handle(ClientboundJukeboxRecordItem message, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientPacketHandler.updateJukeboxContents(message, context);
        });
        return true;
    }

    public static void broadcastJukeboxRecord(GingerbreadManEntity gingerbreadMan, ItemStack stack) {
        CCNetManager.broadcastToPlayersTrackingEntity(new ClientboundJukeboxRecordItem(gingerbreadMan.getId(), stack), gingerbreadMan);
    }
}
