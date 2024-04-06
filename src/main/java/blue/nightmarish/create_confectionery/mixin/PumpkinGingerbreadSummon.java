package blue.nightmarish.create_confectionery.mixin;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.entity.custom.GingerbreadManEntity;
import blue.nightmarish.create_confectionery.registry.CCBlocks;
import blue.nightmarish.create_confectionery.registry.CCEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(CarvedPumpkinBlock.class)
public abstract class PumpkinGingerbreadSummon extends HorizontalDirectionalBlock {
    @Shadow
    private static void spawnGolemInWorld(Level pLevel, BlockPattern.BlockPatternMatch pPatternMatch, Entity pGolem, BlockPos pPos) {
    }

    @Shadow @Final private static Predicate<BlockState> PUMPKINS_PREDICATE;

    protected PumpkinGingerbreadSummon(Properties pProperties) {
        super(pProperties);
    }

    @Unique
    private static final Predicate<BlockState> cc$GINGERBREAD_PRED = (block) -> block != null && (block.is(CCBlocks.GINGERBREAD_BLOCK.get()));

    @Unique
    private static BlockPattern cc$GINGERBREAD_MAN_BASE = null;
    @Unique
    private static BlockPattern cc$GINGERBREAD_MAN_FULL = null;

    @Inject(method = "canSpawnGolem", at = @At("HEAD"), cancellable = true)
    protected void modifyCanSpawnGolem(LevelReader pLevel, BlockPos pPos, CallbackInfoReturnable<Boolean> cir) {
        if (cc$getOrCreateGingerbreadManBase().find(pLevel, pPos) == null)
            return;
        cir.setReturnValue(true);
        cir.cancel();
    }

    @Inject(method = "trySpawnGolem", at = @At("HEAD"), cancellable = true)
    protected void modifyTrySpawnGolem(Level pLevel, BlockPos pPos, CallbackInfo ci) {
        BlockPattern.BlockPatternMatch match = cc$getOrCreateGingerbreadManFull().find(pLevel, pPos);
        if (match == null)
            return;
        ci.cancel();
        GingerbreadManEntity gingerbreadMan = CCEntities.LITTLE_GINGERBREAD_MAN.get().create(pLevel);
        if (gingerbreadMan == null)
            return;
        spawnGolemInWorld(pLevel, match, gingerbreadMan, pPos.below());
    }

    @Unique
    private static BlockPattern cc$getOrCreateGingerbreadManBase() {
        if (cc$GINGERBREAD_MAN_BASE == null)
            cc$GINGERBREAD_MAN_BASE = BlockPatternBuilder
                    .start().aisle(" ", "#")
                    .where('#', BlockInWorld.hasState(cc$GINGERBREAD_PRED))
                    .build();
        return cc$GINGERBREAD_MAN_BASE;
    }

    @Unique
    private static BlockPattern cc$getOrCreateGingerbreadManFull() {
        if (cc$GINGERBREAD_MAN_FULL == null)
            cc$GINGERBREAD_MAN_FULL = BlockPatternBuilder
                    .start().aisle("^", "#")
                    .where('^', BlockInWorld.hasState(PUMPKINS_PREDICATE))
                    .where('#', BlockInWorld.hasState(cc$GINGERBREAD_PRED))
                    .build();
        return cc$GINGERBREAD_MAN_FULL;
    }
}
