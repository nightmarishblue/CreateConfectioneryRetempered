package blue.nightmarish.create_confectionery.data.processing;

import blue.nightmarish.create_confectionery.CCUtils;
import blue.nightmarish.create_confectionery.registry.CCItems;
import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

public class CCMillingRecipes extends CCProcessingRecipeGen {
    GeneratedRecipe
        CRUSHED_COCOA = create(CCItems.CRUSHED_COCOA::get, b -> b.output(CCItems.COCOA_POWDER.get()).output(0.75f, CCItems.COCOA_BUTTER.get()))
    ;

    public CCMillingRecipes(PackOutput output) {
        super(output);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.MILLING;
    }
}
