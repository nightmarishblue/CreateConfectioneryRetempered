package blue.nightmarish.create_confectionery.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class Gingerbread {
    public static BlockBehaviour.Properties props() {
        return BlockBehaviour.Properties.of()
                .strength(1F)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .sound(SoundType.TUFF)
                .requiresCorrectToolForDrops();
    }
}
