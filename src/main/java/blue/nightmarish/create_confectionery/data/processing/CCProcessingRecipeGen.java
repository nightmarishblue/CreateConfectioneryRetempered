package blue.nightmarish.create_confectionery.data.processing;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public abstract class CCProcessingRecipeGen extends ProcessingRecipeGen {
    protected static final List<CCProcessingRecipeGen> GENERATORS = new ArrayList<>();

    public static void registerAll(DataGenerator generator, PackOutput output) {
//        GENERATORS.add(new CCPressingRecipes(output));
//        GENERATORS.add(new CCMixingRecipes(output));
//        GENERATORS.add(new CCMillingRecipes(output));

        generator.addProvider(true, new DataProvider() {
            @Override
            public CompletableFuture<?> run(CachedOutput pOutput) {
                return CompletableFuture.allOf(GENERATORS.stream()
                        .map(gen -> gen.run(pOutput))
                        .toArray(CompletableFuture[]::new));
            }

            @Override
            public String getName() {
                return "Create Confectionery's: Processing Recipes";
            }
        });
    }

    // package private so we copy and paste it
    <T extends ProcessingRecipe<?>> GeneratedRecipe create(String name,
                                                           UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
        return create(new ResourceLocation(CreateConfectionery.MOD_ID, name), transform);
    }
    <T extends ProcessingRecipe<?>> GeneratedRecipe create(Supplier<ItemLike> singleIngredient,
                                                           UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
        return create(CreateConfectionery.MOD_ID, singleIngredient, transform);
    }

    public CCProcessingRecipeGen(PackOutput output) {
        super(output);
    }
}
