package blue.nightmarish.create_confectionery.data.processing;

import blue.nightmarish.create_confectionery.CCUtils;
import blue.nightmarish.create_confectionery.registry.CCBlocks;
import blue.nightmarish.create_confectionery.registry.CCFluids;
import blue.nightmarish.create_confectionery.registry.CCItems;
import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.fluids.potion.PotionFluid;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
public class CCMixingRecipes extends CCProcessingRecipeGen {
    public static final int BOTTLE = 250;
    public static final int BUCKET = FluidType.BUCKET_VOLUME;

    GeneratedRecipe
    // recipes for the new candies
    GINGERDOUGH = create("gingerdough", b -> b
            .require(AllFluids.HONEY.get(), BOTTLE)
            .require(Items.SUGAR)
            .require(AllItems.CINDER_FLOUR)
            .require(CCUtils.forgeItemTag("flour/wheat"))
            .output(CCItems.GINGERDOUGH.get())
    ),
    MARSHMALLOW = create("marshmallow", b -> b
            .requiresHeat(HeatCondition.HEATED)
            .require(Fluids.WATER, BOTTLE)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(Items.SLIME_BALL)
            .output(CCItems.MARSHMALLOW.get(), 6)
    ),
    CANDY_CANE = create("candy_cane", b -> b
            .requiresHeat(HeatCondition.HEATED)
            .require(Fluids.WATER, BOTTLE)
            .require(FluidIngredient.fromFluidStack(PotionFluid.of(BOTTLE, Potion.byName("mundane"))))
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .output(CCItems.CANDY_CANE.get(), 2)
    ),
    HONEY_CANDY = create("honey_candy", b -> b
            .requiresHeat(HeatCondition.HEATED)
            .require(AllFluids.HONEY.get(), BOTTLE)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .require(CCUtils.forgeItemTag("flour/wheat"))
            .output(CCItems.HONEY_CANDY.get(), 4)
    ),
    CARAMEL = create("caramel", b -> b
            .requiresHeat(HeatCondition.HEATED)
            .require(ForgeMod.MILK.get(), BOTTLE)
            .require(Items.SUGAR)
            .require(Items.SUGAR)
            .output(CCFluids.CARAMEL.get(), BOTTLE)
    ),

    // recipes for chocolate
    DARK_CHOCOLATE = create("dark_chocolate", b -> b
                .requiresHeat(HeatCondition.HEATED)
                .require(ForgeMod.MILK.get(), BOTTLE)
                .require(Items.SUGAR)
                .require(CCItems.COCOA_BUTTER.get())
                .require(Items.COCOA_BEANS)
                .require(Items.COCOA_BEANS)
                .output(CCFluids.DARK_CHOCOLATE.get(), BOTTLE)
    ),
    WHITE_CHOCOLATE = create("white_chocolate", b -> b
            .requiresHeat(HeatCondition.HEATED)
            .require(ForgeMod.MILK.get(), BOTTLE)
            .require(Items.SUGAR)
            .require(CCItems.COCOA_BUTTER.get())
            .output(CCFluids.WHITE_CHOCOLATE.get(), BOTTLE)
    ),
    RUBY_CHOCOLATE = create("ruby_chocolate", b -> b
            .requiresHeat(HeatCondition.HEATED)
            .require(ForgeMod.MILK.get(), BOTTLE)
            .require(Items.SUGAR)
            .require(Items.DRAGON_BREATH)
            .require(Items.COCOA_BEANS)
            .output(CCFluids.RUBY_CHOCOLATE.get(), BOTTLE)
    ),
    HOT_CHOCOLATE = create("hot_chocolate", b -> b
            .requiresHeat(HeatCondition.HEATED)
            .require(ForgeMod.MILK.get(), BOTTLE)
            .require(Items.SUGAR)
            .require(CCItems.COCOA_POWDER.get())
            .require(CCItems.COCOA_POWDER.get())
            .output(CCFluids.HOT_CHOCOLATE.get(), BOTTLE)
    ),

    // recipes to melt bars back to their fluid
    DARK_CHOCOLATE_FROM_BAR = meltBar(CCItems.BAR_OF_DARK_CHOCOLATE, CCFluids.DARK_CHOCOLATE),
    WHITE_CHOCOLATE_FROM_BAR = meltBar(CCItems.BAR_OF_WHITE_CHOCOLATE, CCFluids.WHITE_CHOCOLATE),
    RUBY_CHOCOLATE_FROM_BAR = meltBar(CCItems.BAR_OF_RUBY_CHOCOLATE, CCFluids.RUBY_CHOCOLATE),
    CARAMEL_FROM_BAR = meltBar(CCItems.BAR_OF_CARAMEL, CCFluids.CARAMEL),

    // recipes to melt the bricks back into fluids
    CHOCOLATE_MELT_BLOCK = meltBlocks(AllFluids.CHOCOLATE, BUCKET, CCBlocks.CHOCOLATE_BRICKS.get(), CCBlocks.CHOCOLATE_BRICK_STAIRS.get()),
    CHOCOLATE_MELT_SLAB = meltBlocks(AllFluids.CHOCOLATE, BUCKET / 2, CCBlocks.CHOCOLATE_BRICK_SLAB.get()),
    DARK_CHOCOLATE_MELT_BLOCK = meltBlocks(CCFluids.DARK_CHOCOLATE, BUCKET, CCBlocks.DARK_CHOCOLATE_BRICKS.get(), CCBlocks.DARK_CHOCOLATE_BRICK_STAIRS.get()),
    DARK_CHOCOLATE_MELT_SLAB = meltBlocks(CCFluids.DARK_CHOCOLATE, BUCKET / 2, CCBlocks.DARK_CHOCOLATE_BRICK_SLAB.get()),
    WHITE_CHOCOLATE_MELT_BLOCK = meltBlocks(CCFluids.WHITE_CHOCOLATE, BUCKET, CCBlocks.WHITE_CHOCOLATE_BRICKS.get(), CCBlocks.WHITE_CHOCOLATE_BRICK_STAIRS.get()),
    WHITE_CHOCOLATE_MELT_SLAB = meltBlocks(CCFluids.WHITE_CHOCOLATE, BUCKET / 2, CCBlocks.WHITE_CHOCOLATE_BRICK_SLAB.get()),
    RUBY_CHOCOLATE_MELT_BLOCK = meltBlocks(CCFluids.RUBY_CHOCOLATE, BUCKET, CCBlocks.RUBY_CHOCOLATE_BRICKS.get(), CCBlocks.RUBY_CHOCOLATE_BRICK_STAIRS.get()),
    RUBY_CHOCOLATE_MELT_SLAB = meltBlocks(CCFluids.RUBY_CHOCOLATE, BUCKET / 2, CCBlocks.RUBY_CHOCOLATE_BRICK_SLAB.get()),
    CARAMEL_MELT_BLOCK = meltBlocks(CCFluids.CARAMEL, BUCKET, CCBlocks.CARAMEL_BLOCK.get()),
    CARAMEL_MELT_SLAB = meltBlocks(CCFluids.CARAMEL, BUCKET / 2, CCBlocks.CARAMEL_SLAB.get())
    ;


    GeneratedRecipe meltBlocks(Supplier<? extends Fluid> fluid, int amount, Block... blocks) {
        return create(amount + "_" + Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(fluid.get())).getPath() + "_from_melting", b -> b
                .requiresHeat(HeatCondition.SUPERHEATED)
                .require(Ingredient.of(blocks))
                .output(fluid.get(), amount)
        );
    }

    GeneratedRecipe meltBar(RegistryObject<? extends Item> bar, RegistryObject<? extends Fluid> fluid) {
        // <FLUID>_melting is the format used in Create
        return create(fluid.getId().getPath() + "_melting", b -> b
                .requiresHeat(HeatCondition.HEATED)
                .require(bar.get())
                .output(fluid.get(), BOTTLE)
        );
    }

    public CCMixingRecipes(PackOutput output) {
        super(output);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.MIXING;
    }
}
