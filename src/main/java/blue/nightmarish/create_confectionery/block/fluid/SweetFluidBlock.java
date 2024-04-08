package blue.nightmarish.create_confectionery.block.fluid;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SweetFluidBlock extends LiquidBlock {
    public SweetFluidBlock(Supplier<? extends FlowingFluid> pFluid, Properties pProperties) {
        super(pFluid, pProperties);
    }

    public SweetFluidBlock(Supplier<? extends FlowingFluid> fluid) {
        this(fluid, getProps());
    }

    public static BlockBehaviour.Properties getProps() {
        return Properties.of()
                .mapColor(MapColor.WATER)
                .strength(100F)
                .noCollission()
                .noLootTable()
                .liquid()
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.EMPTY)
                .replaceable();
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return true;
    }
}
