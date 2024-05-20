package blue.nightmarish.create_confectionery.data.processing;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.registry.CCFluids;
import blue.nightmarish.create_confectionery.registry.CCItems;
import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class CCEmptyingRecipes extends CCProcessingRecipeGen {
    public static int BOTTLE = CCMixingRecipes.BOTTLE;

    public CCEmptyingRecipes(PackOutput output) {
        super(output);
    }

    GeneratedRecipe
        HOT_CHOCOLATE = create("hot_chocolate", b -> b
            .require(Ingredient.of(CCItems.PLAIN_HOT_CHOCOLATE.get(), CCItems.HOT_CHOCOLATE.get()))
            .output(CCFluids.HOT_CHOCOLATE.get(), BOTTLE)
            .output(Items.GLASS_BOTTLE));

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.EMPTYING;
    }
}
