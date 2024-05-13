package blue.nightmarish.create_confectionery.data.processing;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.registry.CCFluids;
import blue.nightmarish.create_confectionery.registry.CCItems;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.function.UnaryOperator;

public class CCSequencedAssemblyRecipes extends CreateRecipeProvider {
    GeneratedRecipe HOT_CHOCOLATE = create("hot_chocolate", b -> b.require(Items.GLASS_BOTTLE)
        .transitionTo(CCItems.PLAIN_HOT_CHOCOLATE.get())
        .addOutput(CCItems.HOT_CHOCOLATE.get(), 1)
        .loops(1)
        .addStep(FillingRecipe::new, rb -> rb.require(CCFluids.HOT_CHOCOLATE.get(), CCMixingRecipes.BOTTLE))
        .addStep(DeployerApplicationRecipe::new, rb -> rb.require(CCItems.MARSHMALLOW.get())));

	public CCSequencedAssemblyRecipes(PackOutput output) {
		super(output);
	}

	protected GeneratedRecipe create(String name, UnaryOperator<SequencedAssemblyRecipeBuilder> transform) {
		GeneratedRecipe generatedRecipe =
			c -> transform.apply(new SequencedAssemblyRecipeBuilder(new ResourceLocation(CreateConfectionery.MOD_ID, name)))
				.build(c);
		all.add(generatedRecipe);
		return generatedRecipe;
	}

	@Override
	public String getName() {
		return "Create Confectionery's Sequenced Assembly Recipes";
	}
}
