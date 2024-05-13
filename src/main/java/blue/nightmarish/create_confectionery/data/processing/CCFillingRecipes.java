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
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CCFillingRecipes extends CCProcessingRecipeGen {
    public static int BOTTLE = CCMixingRecipes.BOTTLE;

    GeneratedRecipe
            // glazed berries
        DARK_CHOCOLATE_GLAZED_BERRIES = create(CCFluids.DARK_CHOCOLATE.get(), BOTTLE, () -> Items.SWEET_BERRIES, CCItems.DARK_CHOCOLATE_GLAZED_BERRIES),
        WHITE_CHOCOLATE_GLAZED_BERRIES = create(CCFluids.WHITE_CHOCOLATE.get(), BOTTLE, () -> Items.SWEET_BERRIES, CCItems.WHITE_CHOCOLATE_GLAZED_BERRIES),
        RUBY_CHOCOLATE_GLAZED_BERRIES = create(CCFluids.RUBY_CHOCOLATE.get(), BOTTLE, () -> Items.SWEET_BERRIES, CCItems.RUBY_CHOCOLATE_GLAZED_BERRIES),
        CARAMEL_GLAZED_BERRIES = create(CCFluids.CARAMEL.get(), BOTTLE, () -> Items.SWEET_BERRIES, CCItems.CARAMEL_GLAZED_BERRIES),
            // glazed marshmallow
        CHOCOLATE_GLAZED_MARSHMALLOW = create(AllFluids.CHOCOLATE.get(), BOTTLE, CCItems.MARSHMALLOW, CCItems.CHOCOLATE_GLAZED_MARSHMALLOW),
        DARK_CHOCOLATE_GLAZED_MARSHMALLOW = create(CCFluids.DARK_CHOCOLATE.get(), BOTTLE, CCItems.MARSHMALLOW, CCItems.DARK_CHOCOLATE_GLAZED_MARSHMALLOW),
        WHITE_CHOCOLATE_GLAZED_MARSHMALLOW = create(CCFluids.WHITE_CHOCOLATE.get(), BOTTLE, CCItems.MARSHMALLOW, CCItems.WHITE_CHOCOLATE_GLAZED_MARSHMALLOW),
        RUBY_CHOCOLATE_GLAZED_MARSHMALLOW = create(CCFluids.RUBY_CHOCOLATE.get(), BOTTLE, CCItems.MARSHMALLOW, CCItems.RUBY_CHOCOLATE_GLAZED_MARSHMALLOW)


    ;


    GeneratedRecipe create(Fluid fluid, int amount, Supplier<? extends Item> fillee, Supplier<? extends Item> output) {
        return create(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(output.get())).getPath(), b -> b.require(fluid, amount).require(fillee.get()).output(output.get()));
    }

    List<Item> bars = List.of(AllItems.BAR_OF_CHOCOLATE.get(), CCItems.BAR_OF_DARK_CHOCOLATE.get(), CCItems.BAR_OF_WHITE_CHOCOLATE.get(),
            CCItems.BAR_OF_RUBY_CHOCOLATE.get(), CCItems.BAR_OF_CARAMEL.get());
    List<Fluid> fluids = List.of(AllFluids.CHOCOLATE.get(), CCFluids.DARK_CHOCOLATE.get(), CCFluids.WHITE_CHOCOLATE.get(),
            CCFluids.RUBY_CHOCOLATE.get());


    public CCFillingRecipes(PackOutput output) {
        super(output);
        for (int i = 0; i < bars.size(); i++) {
            for (int j = 0; j < fluids.size(); j++) {
                if (i == j) continue;
                Item bar = bars.get(i);
                Fluid fluid = fluids.get(j);
                // (bar of chocolate) chocolate coated (ruby_chocolate) ruby candy
                String barName = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(bar)).getPath();
                String fluidName = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(fluid)).getPath();
                // for the bar, we remove bar_of from the start, and then take the first word
                String inner = barName.replaceFirst("bar_of_", "").split("_", 2)[0];
                String coating = fluidName.replaceFirst("flowing_", "").split("_", 2)[0];
                // for the fluid, we take the first word

                String candyName = String.format("%s_coated_%s_candy", coating, inner);
                CreateConfectionery.LOGGER.info(candyName);
                // now we combine
                Item candy = ForgeRegistries.ITEMS.getValue(new ResourceLocation(CreateConfectionery.MOD_ID, candyName));
                assert candy != null;
                CreateConfectionery.LOGGER.info(candy.getDescriptionId());
                create(fluid, BOTTLE, () -> bar, () -> candy);
            }
        }
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.FILLING;
    }
}
