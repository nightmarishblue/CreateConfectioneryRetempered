package blue.nightmarish.create_confectionery.data;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.registry.CCBlocks;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public class CCRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static Consumer<FinishedRecipe> WRITER;

    public CCRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        WRITER = pWriter;
        // packing recipes for blocks
//        twoByTwoPacker(pWriter, RecipeCategory.BUILDING_BLOCKS, CCBlocks.GINGERBREAD_BLOCK.get(), );
        // generate stairs and slabs
        stoneStuff(CCBlocks.GINGERBREAD_BLOCK, CCBlocks.GINGERBREAD_SLAB, CCBlocks.GINGERBREAD_STAIRS);
        stoneStuff(CCBlocks.GINGERBREAD_BRICKS, CCBlocks.GINGERBREAD_BRICK_SLAB, CCBlocks.GINGERBREAD_BRICK_STAIRS);

        stoneStuff(CCBlocks.CHOCOLATE_BRICKS, CCBlocks.CHOCOLATE_BRICK_SLAB, CCBlocks.CHOCOLATE_BRICK_STAIRS);
        stoneStuff(CCBlocks.DARK_CHOCOLATE_BRICKS, CCBlocks.DARK_CHOCOLATE_BRICK_SLAB, CCBlocks.DARK_CHOCOLATE_BRICK_STAIRS);
        stoneStuff(CCBlocks.WHITE_CHOCOLATE_BRICKS, CCBlocks.WHITE_CHOCOLATE_BRICK_SLAB, CCBlocks.WHITE_CHOCOLATE_BRICK_STAIRS);
        stoneStuff(CCBlocks.RUBY_CHOCOLATE_BRICKS, CCBlocks.RUBY_CHOCOLATE_BRICK_SLAB, CCBlocks.RUBY_CHOCOLATE_BRICK_STAIRS);
    }

    // make the stairs and block for a set of stonelike things
    private static void stoneStuff(RegistryObject<Block> blockSup, RegistryObject<SlabBlock> slabSup, RegistryObject<StairBlock> stairSup) {
        Item block = blockSup.get().asItem();
        Item slab = slabSup.get().asItem();
        Item stair = stairSup.get().asItem();

        Ingredient ingredient = Ingredient.of(block);
        String criterion = getHasName(block);

        // craft a slab in a table
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, slab, ingredient)
                .unlockedBy(criterion, has(block))
                .save(WRITER);

        // stone cut a slab
        stonecutterRecipe(slab, block, 2);

        // stair in table
        stairBuilder(stair, ingredient)
                .unlockedBy(criterion, has(block))
                .save(WRITER);

        // stair in cutter
        stonecutterRecipe(stair, block, 1);
    }

    private static void stonecutterRecipe(ItemLike pResult, ItemLike pMaterial, int pResultCount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(pMaterial), RecipeCategory.BUILDING_BLOCKS, pResult, pResultCount)
                .unlockedBy(getHasName(pMaterial), has(pMaterial))
                .save(WRITER, new ResourceLocation(CreateConfectionery.MOD_ID, getConversionRecipeName(pResult, pMaterial) + "_stonecutting"));
    }
}
