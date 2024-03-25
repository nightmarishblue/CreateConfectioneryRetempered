package blue.nightmarish.create_confectionery.registry;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.block.Gingerbread;
import blue.nightmarish.create_confectionery.block.SweetFluidBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class CCBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister
            .create(ForgeRegistries.BLOCKS, CreateConfectionery.MOD_ID);

    // liquid blocks for chocolate
    public static final RegistryObject<LiquidBlock> BLACK_CHOCOLATE = BLOCKS
            .register("black_chocolate", () -> new SweetFluidBlock(CCFluids.BLACK_CHOCOLATE));
    public static final RegistryObject<LiquidBlock> RUBY_CHOCOLATE = BLOCKS
            .register("ruby_chocolate", () -> new SweetFluidBlock(CCFluids.RUBY_CHOCOLATE));
    public static final RegistryObject<LiquidBlock> WHITE_CHOCOLATE = BLOCKS
            .register("white_chocolate", () -> new SweetFluidBlock(CCFluids.WHITE_CHOCOLATE));

    public static final RegistryObject<LiquidBlock> CARAMEL = BLOCKS
            .register("caramel", () -> new SweetFluidBlock(CCFluids.CARAMEL));

    // this block has no bucket and is not intended to be placed
    public static final RegistryObject<LiquidBlock> HOT_CHOCOLATE = BLOCKS
            .register("hot_chocolate", () -> new SweetFluidBlock(CCFluids.HOT_CHOCOLATE));

    public static final RegistryObject<Block> GINGERBREAD_BLOCK =
            registerBlockAndItem("gingerbread_block", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1F)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.TUFF)
                    .requiresCorrectToolForDrops()
                    ));
    public static final RegistryObject<SlabBlock> GINGERBREAD_SLAB =
            registerBlockAndItem("gingerbread_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(GINGERBREAD_BLOCK.get())));
    public static final RegistryObject<StairBlock> GINGERBREAD_STAIR =
            registerBlockAndItem("gingerbread_stairs", () -> new StairBlock(() -> GINGERBREAD_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(GINGERBREAD_BLOCK.get())));

//    public static final RegistryObject<Block> GINGERBREAD_BRICKS =
//            registerBlockAndItem("gingerbread_bricks", () -> new Block(BlockBehaviour.Properties.copy(GINGERBREAD_BLOCK.get())));
//    public static final RegistryObject<SlabBlock> GINGERBREAD_BRICK_SLAB =
//            registerBlockAndItem("gingerbread_brick_stairs", () -> new SlabBlock(BlockBehaviour.Properties.copy(GINGERBREAD_BRICKS.get())));
//    public static final RegistryObject<StairBlock> GINGERBREAD_BRICK_STAIRS =
//            registerBlockAndItem("gingerbread_brick_stairs", () -> new StairBlock(() -> GINGERBREAD_BRICKS.get().defaultBlockState(),
//                    BlockBehaviour.Properties.copy(GINGERBREAD_BRICKS.get())));
//
//    public static final RegistryObject<Block> CHOCOLATE_BRICKS =
//            registerBlockAndItem("chocolate_bricks", () -> new Block(BlockBehaviour.Properties.of()
//                    .strength(2F)
//                    .instrument(NoteBlockInstrument.BASEDRUM)
//                    .friction(0.8F)
//                    .requiresCorrectToolForDrops()
//            ));
//    public static final RegistryObject<SlabBlock> CHOCOLATE_BRICK_SLAB =
//            registerBlockAndItem("chocolate_brick_stairs", () -> new SlabBlock(BlockBehaviour.Properties.copy(CHOCOLATE_BRICKS.get())));
//    public static final RegistryObject<StairBlock> CHOCOLATE_BRICK_STAIRS =
//            registerBlockAndItem("chocolate_brick_stairs", () -> new StairBlock(() -> CHOCOLATE_BRICKS.get().defaultBlockState(),
//                    BlockBehaviour.Properties.copy(CHOCOLATE_BRICKS.get())));


    private static <T extends Block> RegistryObject<T> registerBlockAndItem(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return CCItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
