package blue.nightmarish.create_confectionery.network.clientbound;

import blue.nightmarish.create_confectionery.entity.custom.GingerbreadManEntity;
import blue.nightmarish.create_confectionery.network.CCNetManager;
import blue.nightmarish.create_confectionery.network.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
public class ClientboundGingerbreadManData {
    public final int gingerbreadManId;
    public final ItemStack record;
    public final BlockPos jukebox;

    public ClientboundGingerbreadManData(int gingerbreadManId, ItemStack stack, BlockPos jukebox) {
        this.gingerbreadManId = gingerbreadManId;
        this.record = stack;
        this.jukebox = jukebox; // just ignored later
    }

    public ClientboundGingerbreadManData(FriendlyByteBuf buf) {
        this.gingerbreadManId = buf.readInt();
        this.record = buf.readItem();
        this.jukebox = (this.record.isEmpty()) ? null : buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.gingerbreadManId);
        buf.writeItem(this.record);
        if (!this.record.isEmpty())
            buf.writeBlockPos(this.jukebox);
    }

    public static boolean handle(ClientboundGingerbreadManData message, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientPacketHandler.updateGingerbreadManData(message, context);
        });
        return true;
    }

    public static void broadcastGingerbreadManData(GingerbreadManEntity gingerbreadMan) {
        CCNetManager.broadcastToPlayersTrackingEntity(new ClientboundGingerbreadManData(gingerbreadMan.getId(), gingerbreadMan.getRecord(), gingerbreadMan.getJukebox()), gingerbreadMan);
    }
}
