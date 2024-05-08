package blue.nightmarish.create_confectionery.data;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.registry.CCBlocks;
import blue.nightmarish.create_confectionery.registry.CCItems;
import com.simibubi.create.AllItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
public class CCRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static Consumer<FinishedRecipe> WRITER;

    public CCRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        WRITER = pWriter;
        // recipes for the foodstuffs
        ovenRecipe(CCItems.GINGERDOUGH, CCItems.GINGERBREAD);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, CCItems.MARSHMALLOW_ON_STICK.get())
                .define('#', CCItems.MARSHMALLOW.get()).define('|', Items.STICK)
                .pattern("##").pattern("| ").unlockedBy(getHasName(CCItems.MARSHMALLOW.get()), has(CCItems.MARSHMALLOW.get())).save(WRITER);
        campfireRecipe(CCItems.MARSHMALLOW_ON_STICK, CCItems.CARAMELIZED_MARSHMALLOW_ON_STICK);
        // chocolate bars
        chocolateBar(AllItems.BAR_OF_CHOCOLATE, CCItems.FULL_CHOCOLATE_BAR);
        chocolateBar(CCItems.BAR_OF_DARK_CHOCOLATE, CCItems.FULL_DARK_CHOCOLATE_BAR);
        chocolateBar(CCItems.BAR_OF_WHITE_CHOCOLATE, CCItems.FULL_WHITE_CHOCOLATE_BAR);
        chocolateBar(CCItems.BAR_OF_RUBY_CHOCOLATE, CCItems.FULL_RUBY_CHOCOLATE_BAR);

        // packing recipes for blocks
        twoXPacker(CCBlocks.GINGERBREAD_BLOCK, CCItems.GINGERDOUGH);
        twoXPacker(CCBlocks.GINGERBREAD_BRICKS, CCItems.GINGERBREAD);

        twoXPacker(CCBlocks.CHOCOLATE_BRICKS, AllItems.BAR_OF_CHOCOLATE);
        twoXPacker(CCBlocks.DARK_CHOCOLATE_BRICKS, CCItems.BAR_OF_DARK_CHOCOLATE);
        twoXPacker(CCBlocks.WHITE_CHOCOLATE_BRICKS, CCItems.BAR_OF_WHITE_CHOCOLATE);
        twoXPacker(CCBlocks.RUBY_CHOCOLATE_BRICKS, CCItems.BAR_OF_RUBY_CHOCOLATE);

        twoXPacker(CCBlocks.CARAMEL_BLOCK, CCItems.BAR_OF_CARAMEL); // considering switching this to a fluid pressing recipe

        twoXPacker(CCBlocks.CANDY_CANE_BLOCK, CCItems.CANDY_CANE);

        // generate stairs and slabs
        stoneStuff(CCBlocks.GINGERBREAD_BLOCK, CCBlocks.GINGERBREAD_SLAB, CCBlocks.GINGERBREAD_STAIRS);
        stoneStuff(CCBlocks.GINGERBREAD_BRICKS, CCBlocks.GINGERBREAD_BRICK_SLAB, CCBlocks.GINGERBREAD_BRICK_STAIRS);

        stoneStuff(CCBlocks.CHOCOLATE_BRICKS, CCBlocks.CHOCOLATE_BRICK_SLAB, CCBlocks.CHOCOLATE_BRICK_STAIRS);
        stoneStuff(CCBlocks.DARK_CHOCOLATE_BRICKS, CCBlocks.DARK_CHOCOLATE_BRICK_SLAB, CCBlocks.DARK_CHOCOLATE_BRICK_STAIRS);
        stoneStuff(CCBlocks.WHITE_CHOCOLATE_BRICKS, CCBlocks.WHITE_CHOCOLATE_BRICK_SLAB, CCBlocks.WHITE_CHOCOLATE_BRICK_STAIRS);
        stoneStuff(CCBlocks.RUBY_CHOCOLATE_BRICKS, CCBlocks.RUBY_CHOCOLATE_BRICK_SLAB, CCBlocks.RUBY_CHOCOLATE_BRICK_STAIRS);

        slabStuff(CCBlocks.CARAMEL_BLOCK, CCBlocks.CARAMEL_SLAB);
    }

    // make the stairs and block for a set of stonelike things
    private static void stoneStuff(Supplier<? extends Block> block, Supplier<? extends SlabBlock> slab, Supplier<? extends StairBlock> stairs) {
        slabStuff(block, slab);
        stairStuff(block, stairs);
    }

    private static void slabStuff(Supplier<? extends Block> block, Supplier<? extends SlabBlock> slab) {
        // craft a slab in a table
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, slab.get(), Ingredient.of(block.get()))
                .unlockedBy(getHasName(block.get()), has(block.get()))
                .save(WRITER);

        // stone cut a slab
        stonecutterRecipe(slab.get(), block.get(), 2);
    }

    private static void stairStuff(Supplier<? extends Block> block, Supplier<? extends StairBlock> stairs) {
        // craft a slab in a table
        stairBuilder(stairs.get(), Ingredient.of(block.get()))
                .unlockedBy(getHasName(block.get()), has(block.get()))
                .save(WRITER);

        // stone cut a slab
        stonecutterRecipe(stairs.get(), block.get(), 1);
    }

    private static void stonecutterRecipe(ItemLike pResult, ItemLike pMaterial, int pResultCount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(pMaterial), RecipeCategory.BUILDING_BLOCKS, pResult, pResultCount)
                .unlockedBy(getHasName(pMaterial), has(pMaterial))
                .save(WRITER, new ResourceLocation(CreateConfectionery.MOD_ID, getConversionRecipeName(pResult, pMaterial) + "_stonecutting"));
    }

    private static void twoXPacker(Supplier<? extends Block> block, Supplier<? extends Item> item) {
        twoByTwoPacker(WRITER, RecipeCategory.BUILDING_BLOCKS, block.get(), item.get());
    }

    private static void ovenRecipe(Supplier<? extends Item> input, Supplier<? extends Item> output) {
        simpleCookingRecipe(WRITER, "smoking", RecipeSerializer.SMOKING_RECIPE, 100, input.get(), output.get(), 0.35F);
        simpleCookingRecipe(WRITER, "smelting", RecipeSerializer.SMELTING_RECIPE, 200, input.get(), output.get(), 0.35F);
    }

    private static void campfireRecipe(Supplier<? extends Item> input, Supplier<? extends Item> output) {
        simpleCookingRecipe(WRITER, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, 600, input.get(), output.get(), 0F);
    }

    private static void chocolateBar(Supplier<? extends Item> chocolate, Supplier<? extends Item> fullBar) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, fullBar.get())
                .define('^', Items.PAPER).define('#', chocolate.get())
                .pattern("##")
                .pattern("##")
                .pattern("^^")
                .unlockedBy(getHasName(chocolate.get()), has(chocolate.get()))
                .save(WRITER);
    }
}
