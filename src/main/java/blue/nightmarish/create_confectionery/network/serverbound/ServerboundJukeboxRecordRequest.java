package blue.nightmarish.create_confectionery.network.serverbound;

import blue.nightmarish.create_confectionery.network.CCNetManager;
import blue.nightmarish.create_confectionery.network.ServerPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
public class ServerboundJukeboxRecordRequest {
    public final BlockPos pos;
    public final int gingerbreadManId;

    public ServerboundJukeboxRecordRequest(BlockPos pos, int gingerbreadManId) {
        this.pos = pos;
        this.gingerbreadManId = gingerbreadManId;
    }

    public ServerboundJukeboxRecordRequest(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.gingerbreadManId = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
        buf.writeInt(gingerbreadManId);
    }

    public static boolean handle(ServerboundJukeboxRecordRequest message, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPacketHandler.getJukeboxItem(message, context);
        });
        return true;
    }

    public static void sendRecordRequest(BlockPos pos, int gingerbreadManId) {
        CCNetManager.sendToServer(new ServerboundJukeboxRecordRequest(pos, gingerbreadManId));
    }
}
