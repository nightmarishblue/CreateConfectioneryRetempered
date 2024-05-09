package blue.nightmarish.create_confectionery.data.processing;

import blue.nightmarish.create_confectionery.registry.CCFluids;
import blue.nightmarish.create_confectionery.registry.CCItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.RegistryObject;

public class CCCompactingRecipes extends CCProcessingRecipeGen {
    public static int BOTTLE = CCMixingRecipes.BOTTLE;

    GeneratedRecipe
        BAR_OF_DARK_CHOCOLATE = create(CCFluids.DARK_CHOCOLATE, BOTTLE, CCItems.BAR_OF_DARK_CHOCOLATE),
        BAR_OF_WHITE_CHOCOLATE = create(CCFluids.WHITE_CHOCOLATE, BOTTLE, CCItems.BAR_OF_WHITE_CHOCOLATE),
        BAR_OF_RUBY_CHOCOLATE = create(CCFluids.RUBY_CHOCOLATE, BOTTLE, CCItems.BAR_OF_RUBY_CHOCOLATE),
        BAR_OF_CARAMEL = create(CCFluids.CARAMEL, BOTTLE, CCItems.BAR_OF_CARAMEL);

    GeneratedRecipe create(RegistryObject<? extends Fluid> fluid, int amount, RegistryObject<? extends Item> item) {
        return create(fluid.getId().getPath(), b -> b.require(fluid.get(), amount).output(item.get()));
    }

    public CCCompactingRecipes(PackOutput output) {
        super(output);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.COMPACTING;
    }
}
