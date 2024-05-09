package blue.nightmarish.create_confectionery.data.processing;

import blue.nightmarish.create_confectionery.registry.CCItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

public class CCPressingRecipes extends CCProcessingRecipeGen {
    GeneratedRecipe
            GINGERBREAD_MAN = create(CCItems.GINGERBREAD::get, b -> b.output(CCItems.GINGERBREAD_MAN.get())),
            CRUSHED_COCOA = create(() -> Items.COCOA_BEANS, b -> b.output(CCItems.CRUSHED_COCOA.get()));

    public CCPressingRecipes(PackOutput output) {
        super(output);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.PRESSING;
    }
}
