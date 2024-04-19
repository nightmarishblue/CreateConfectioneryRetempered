package blue.nightmarish.create_confectionery.data;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.registry.CCBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class CCBlockStateProvider extends BlockStateProvider {
    public CCBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CreateConfectionery.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        liquidBlock(CCBlocks.DARK_CHOCOLATE);
        liquidBlock(CCBlocks.WHITE_CHOCOLATE);
        liquidBlock(CCBlocks.RUBY_CHOCOLATE);
        liquidBlock(CCBlocks.CARAMEL);

        blockWithItem(CCBlocks.GINGERBREAD_BLOCK);
        stairWithItemFromBlock(CCBlocks.GINGERBREAD_STAIRS, CCBlocks.GINGERBREAD_BLOCK);
        slabWithItemFromBlock(CCBlocks.GINGERBREAD_SLAB, CCBlocks.GINGERBREAD_BLOCK);

        blockWithItem(CCBlocks.GINGERBREAD_BRICKS);
        stairWithItemFromBlock(CCBlocks.GINGERBREAD_BRICK_STAIRS, CCBlocks.GINGERBREAD_BRICKS);
        slabWithItemFromBlock(CCBlocks.GINGERBREAD_BRICK_SLAB, CCBlocks.GINGERBREAD_BRICKS);

        blockWithItem(CCBlocks.CHOCOLATE_BRICKS);
        stairWithItemFromBlock(CCBlocks.CHOCOLATE_BRICK_STAIRS, CCBlocks.CHOCOLATE_BRICKS);
        slabWithItemFromBlock(CCBlocks.CHOCOLATE_BRICK_SLAB, CCBlocks.CHOCOLATE_BRICKS);

        blockWithItem(CCBlocks.DARK_CHOCOLATE_BRICKS);
        stairWithItemFromBlock(CCBlocks.DARK_CHOCOLATE_BRICK_STAIRS, CCBlocks.DARK_CHOCOLATE_BRICKS);
        slabWithItemFromBlock(CCBlocks.DARK_CHOCOLATE_BRICK_SLAB, CCBlocks.DARK_CHOCOLATE_BRICKS);

        blockWithItem(CCBlocks.WHITE_CHOCOLATE_BRICKS);
        stairWithItemFromBlock(CCBlocks.WHITE_CHOCOLATE_BRICK_STAIRS, CCBlocks.WHITE_CHOCOLATE_BRICKS);
        slabWithItemFromBlock(CCBlocks.WHITE_CHOCOLATE_BRICK_SLAB, CCBlocks.WHITE_CHOCOLATE_BRICKS);

        blockWithItem(CCBlocks.RUBY_CHOCOLATE_BRICKS);
        stairWithItemFromBlock(CCBlocks.RUBY_CHOCOLATE_BRICK_STAIRS, CCBlocks.RUBY_CHOCOLATE_BRICKS);
        slabWithItemFromBlock(CCBlocks.RUBY_CHOCOLATE_BRICK_SLAB, CCBlocks.RUBY_CHOCOLATE_BRICKS);

        blockWithItem(CCBlocks.CARAMEL_BLOCK, "translucent");
        slabWithItemFromBlock(CCBlocks.CARAMEL_SLAB, CCBlocks.CARAMEL_BLOCK, "translucent");

        logWithItem(CCBlocks.CANDY_CANE_BLOCK);
    }

    private void blockWithItem(RegistryObject<Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    private void blockWithItem(RegistryObject<Block> block, String renderType) {
        String loc = "block/" + block.getId().getPath();
        ModelFile model = models().withExistingParent(loc, "block/cube_all")
                .texture("all", loc)
                .texture("particle", loc)
                .renderType(renderType);
        simpleBlock(block.get(), model);
        simpleBlockItem(block.get(), model);
    }

    private void liquidBlock(RegistryObject<LiquidBlock> block) {
        String id = block.getId().getPath();
        simpleBlock(block.get(), models()
                .getBuilder("fluid/" + id)
                .texture("particle", "fluid/" + id + "_still")
        );
    }

    private void logWithItem(RegistryObject<RotatedPillarBlock> block) {
        logBlock(block.get());
        simpleBlockItem(block.get(), models().withExistingParent(
                "block/" + block.getId().getPath(),
                "block/cube_column"));
    }

    private void stairWithItemFromBlock(RegistryObject<StairBlock> stairs, RegistryObject<Block> block) {
        String id = block.getId().getPath();
        stairsBlock(stairs.get(), new ResourceLocation(CreateConfectionery.MOD_ID, "block/" + id));
        simpleBlockItem(stairs.get(), models().withExistingParent(
                "block/" + stairs.getId().getPath(),
                "block/stairs"));
    }

    private void slabWithItemFromBlock(RegistryObject<SlabBlock> slab, RegistryObject<Block> block) {
        ResourceLocation loc = new ResourceLocation(CreateConfectionery.MOD_ID, "block/" + block.getId().getPath());
        slabBlock(slab.get(), loc, loc);
        simpleBlockItem(slab.get(), models().withExistingParent(
                "block/" + slab.getId().getPath(),
                "block/slab"
        ));
    }

    private void slabWithItemFromBlock(RegistryObject<SlabBlock> slab, RegistryObject<Block> block, String renderType) {
        ResourceLocation loc = new ResourceLocation(CreateConfectionery.MOD_ID, "block/" + block.getId().getPath());
        slabBlockWithRenderType(slab, loc, loc, renderType);
        simpleBlockItem(slab.get(), models().withExistingParent(
                "block/" + slab.getId().getPath(),
                "block/slab"
        ).renderType(renderType));
    }

    public void slabBlockWithRenderType(RegistryObject<SlabBlock> block, ResourceLocation doubleslab, ResourceLocation texture, String renderType) {
        slabBlock(block, doubleslab, texture, texture, texture, renderType);
    }

    public void slabBlock(RegistryObject<SlabBlock> block, ResourceLocation doubleslab, ResourceLocation side, ResourceLocation bottom, ResourceLocation top, String renderType) {
        slabBlock(block.get(),
                models().slab(block.getId().getPath(), side, bottom, top).renderType(renderType),
                models().slabTop(block.getId().getPath() + "_top", side, bottom, top).renderType(renderType),
                models().getExistingFile(doubleslab)
        );
    }
}
