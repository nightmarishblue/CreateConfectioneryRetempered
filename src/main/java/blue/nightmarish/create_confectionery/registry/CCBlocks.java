package blue.nightmarish.create_confectionery.registry;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.block.CaramelBlock;
import blue.nightmarish.create_confectionery.block.CaramelSlabBlock;
import blue.nightmarish.create_confectionery.block.fluid.SweetFluidBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
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
    public static final RegistryObject<LiquidBlock> DARK_CHOCOLATE = BLOCKS
            .register("dark_chocolate", () -> new SweetFluidBlock(CCFluids.DARK_CHOCOLATE));
    public static final RegistryObject<LiquidBlock> RUBY_CHOCOLATE = BLOCKS
            .register("ruby_chocolate", () -> new SweetFluidBlock(CCFluids.RUBY_CHOCOLATE));
    public static final RegistryObject<LiquidBlock> WHITE_CHOCOLATE = BLOCKS
            .register("white_chocolate", () -> new SweetFluidBlock(CCFluids.WHITE_CHOCOLATE));

    public static final RegistryObject<LiquidBlock> CARAMEL = BLOCKS
            .register("caramel", () -> new SweetFluidBlock(CCFluids.CARAMEL));

    // this block has no bucket and is not intended to be placed
//    public static final RegistryObject<LiquidBlock> HOT_CHOCOLATE = BLOCKS
//            .register("hot_chocolate", () -> new SweetFluidBlock(CCFluids.HOT_CHOCOLATE));

    public static final RegistryObject<Block> GINGERBREAD_BLOCK =
            registerBlockAndItem(
                    "gingerbread_block",
                    () -> new Block(
                            BlockBehaviour.Properties.of()
                                .strength(1F)
                                .instrument(NoteBlockInstrument.BASEDRUM)
                                .sound(SoundType.TUFF)
                                .requiresCorrectToolForDrops()
                    ),
                    new Item.Properties().rarity(Rarity.UNCOMMON)
            );

    // gingerbread
    public static final RegistryObject<StairBlock> GINGERBREAD_STAIRS =
            registerBlockAndItem("gingerbread_stairs",
                    () -> new StairBlock(
                            () -> GINGERBREAD_BLOCK.get().defaultBlockState(),
                            BlockBehaviour.Properties.copy(GINGERBREAD_BLOCK.get())
                    ),
                    new Item.Properties().rarity(Rarity.UNCOMMON)
            );

    public static final RegistryObject<SlabBlock> GINGERBREAD_SLAB =
            registerBlockAndItem("gingerbread_slab",
                    () -> new SlabBlock(BlockBehaviour.Properties.copy(GINGERBREAD_BLOCK.get())),
                    new Item.Properties().rarity(Rarity.UNCOMMON)
            );

    public static final RegistryObject<Block> GINGERBREAD_BRICKS =
            registerBlockAndItem(
                    "gingerbread_bricks",
                    () -> new Block(BlockBehaviour.Properties.copy(GINGERBREAD_BLOCK.get())),
                    new Item.Properties().rarity(Rarity.UNCOMMON)
            );

    public static final RegistryObject<StairBlock> GINGERBREAD_BRICK_STAIRS =
            registerBlockAndItem(
                    "gingerbread_brick_stairs",
                    () -> new StairBlock(
                            () -> GINGERBREAD_BRICKS.get().defaultBlockState(),
                            BlockBehaviour.Properties.copy(GINGERBREAD_BRICKS.get())
                    ),
                    new Item.Properties().rarity(Rarity.UNCOMMON)
            );

    public static final RegistryObject<SlabBlock> GINGERBREAD_BRICK_SLAB =
            registerBlockAndItem(
                    "gingerbread_brick_slab",
                    () -> new SlabBlock(BlockBehaviour.Properties.copy(GINGERBREAD_BRICKS.get())),
                    new Item.Properties().rarity(Rarity.UNCOMMON)
            );

    // chocolate
    public static final RegistryObject<Block> CHOCOLATE_BRICKS =
            registerBlockAndItem("chocolate_bricks", () -> new Block(BlockBehaviour.Properties.of()
                    .strength(2F)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .sound(SoundType.MUD)
                    .requiresCorrectToolForDrops()
            ));
    public static final RegistryObject<SlabBlock> CHOCOLATE_BRICK_SLAB =
            registerBlockAndItem("chocolate_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CHOCOLATE_BRICKS.get())));
    public static final RegistryObject<StairBlock> CHOCOLATE_BRICK_STAIRS =
            registerBlockAndItem("chocolate_brick_stairs", () -> new StairBlock(() -> CHOCOLATE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(CHOCOLATE_BRICKS.get())));

    public static final RegistryObject<Block> DARK_CHOCOLATE_BRICKS =
            registerBlockAndItem("dark_chocolate_bricks", () -> new Block(BlockBehaviour.Properties.copy(CHOCOLATE_BRICKS.get())));
    public static final RegistryObject<SlabBlock> DARK_CHOCOLATE_BRICK_SLAB =
            registerBlockAndItem("dark_chocolate_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(DARK_CHOCOLATE_BRICKS.get())));
    public static final RegistryObject<StairBlock> DARK_CHOCOLATE_BRICK_STAIRS =
            registerBlockAndItem("dark_chocolate_brick_stairs", () -> new StairBlock(() -> DARK_CHOCOLATE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(DARK_CHOCOLATE_BRICKS.get())));

    public static final RegistryObject<Block> WHITE_CHOCOLATE_BRICKS =
            registerBlockAndItem("white_chocolate_bricks", () -> new Block(BlockBehaviour.Properties.copy(CHOCOLATE_BRICKS.get())));
    public static final RegistryObject<SlabBlock> WHITE_CHOCOLATE_BRICK_SLAB =
            registerBlockAndItem("white_chocolate_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(WHITE_CHOCOLATE_BRICKS.get())));
    public static final RegistryObject<StairBlock> WHITE_CHOCOLATE_BRICK_STAIRS =
            registerBlockAndItem("white_chocolate_brick_stairs", () -> new StairBlock(() -> WHITE_CHOCOLATE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(WHITE_CHOCOLATE_BRICKS.get())));

    public static final RegistryObject<Block> RUBY_CHOCOLATE_BRICKS =
            registerBlockAndItem("ruby_chocolate_bricks", () -> new Block(BlockBehaviour.Properties.copy(CHOCOLATE_BRICKS.get())));
    public static final RegistryObject<SlabBlock> RUBY_CHOCOLATE_BRICK_SLAB =
            registerBlockAndItem("ruby_chocolate_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(RUBY_CHOCOLATE_BRICKS.get())));
    public static final RegistryObject<StairBlock> RUBY_CHOCOLATE_BRICK_STAIRS =
            registerBlockAndItem("ruby_chocolate_brick_stairs", () -> new StairBlock(() -> RUBY_CHOCOLATE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(RUBY_CHOCOLATE_BRICKS.get())));

    public static final RegistryObject<Block> CARAMEL_BLOCK =
            registerBlockAndItem("caramel_block", () -> new CaramelBlock(BlockBehaviour.Properties.of()
//                    .speedFactor(0.4f)
//                    .jumpFactor(0.5f)
                    .noOcclusion()
                    .sound(SoundType.DEEPSLATE_TILES)
            ));

    public static final RegistryObject<SlabBlock> CARAMEL_SLAB =
            registerBlockAndItem("caramel_slab", () -> new CaramelSlabBlock(BlockBehaviour.Properties.copy(CARAMEL_BLOCK.get())));

    public static final RegistryObject<RotatedPillarBlock> CANDY_CANE_BLOCK =
            registerBlockAndItem("candy_cane_block", () -> new RotatedPillarBlock(
                    BlockBehaviour.Properties.of()
                            .instrument(NoteBlockInstrument.BASEDRUM)
                            .sound(SoundType.CALCITE)
                            .strength(1, 2)
                            .requiresCorrectToolForDrops()
            ), new Item.Properties().rarity(Rarity.UNCOMMON));

    private static <T extends Block> RegistryObject<T> registerBlockAndItem(String name, Supplier<T> block) {
       return registerBlockAndItem(name, block, new Item.Properties());
    }

    private static <T extends Block> RegistryObject<T> registerBlockAndItem(String name, Supplier<T> block, Item.Properties itemProps) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, itemProps);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, Item.Properties itemProps) {
        return CCItems.ITEMS.register(name, () -> new BlockItem(block.get(), itemProps));
    }
}
