package blue.nightmarish.create_confectionery.registry;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class CCFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister
            .create(ForgeRegistries.FLUIDS, CreateConfectionery.MOD_ID);

    // the special chocolates
    public static final RegistryObject<FlowingFluid> DARK_CHOCOLATE = FLUIDS
            .register("dark_chocolate", () -> new ForgeFlowingFluid.Source(CCFluids.DARK_CHOCOLATE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_DARK_CHOCOLATE = FLUIDS
            .register("flowing_dark_chocolate", () -> new ForgeFlowingFluid.Flowing(CCFluids.DARK_CHOCOLATE_PROPERTIES));

    public static final ForgeFlowingFluid.Properties DARK_CHOCOLATE_PROPERTIES =
            flowingFluidProps(CCFluidTypes.DARK_CHOCOLATE_TYPE, CCFluids.DARK_CHOCOLATE,
                    CCFluids.FLOWING_DARK_CHOCOLATE, CCBlocks.DARK_CHOCOLATE, CCItems.DARK_CHOCOLATE_BUCKET);


    public static final RegistryObject<FlowingFluid> RUBY_CHOCOLATE = FLUIDS
            .register("ruby_chocolate", () -> new ForgeFlowingFluid.Source(CCFluids.RUBY_CHOCOLATE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_RUBY_CHOCOLATE = FLUIDS
            .register("flowing_ruby_chocolate", () -> new ForgeFlowingFluid.Flowing(CCFluids.RUBY_CHOCOLATE_PROPERTIES));

    public static final ForgeFlowingFluid.Properties RUBY_CHOCOLATE_PROPERTIES =
            flowingFluidProps(CCFluidTypes.RUBY_CHOCOLATE_TYPE, CCFluids.RUBY_CHOCOLATE,
                    CCFluids.FLOWING_RUBY_CHOCOLATE, CCBlocks.RUBY_CHOCOLATE, CCItems.RUBY_CHOCOLATE_BUCKET);


    public static final RegistryObject<FlowingFluid> WHITE_CHOCOLATE = FLUIDS
            .register("white_chocolate", () -> new ForgeFlowingFluid.Source(CCFluids.WHITE_CHOCOLATE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_WHITE_CHOCOLATE = FLUIDS
            .register("flowing_white_chocolate", () -> new ForgeFlowingFluid.Flowing(CCFluids.WHITE_CHOCOLATE_PROPERTIES));

    public static final ForgeFlowingFluid.Properties WHITE_CHOCOLATE_PROPERTIES =
            flowingFluidProps(CCFluidTypes.WHITE_CHOCOLATE_TYPE, CCFluids.WHITE_CHOCOLATE,
                    CCFluids.FLOWING_WHITE_CHOCOLATE, CCBlocks.WHITE_CHOCOLATE, CCItems.WHITE_CHOCOLATE_BUCKET);


    public static final RegistryObject<FlowingFluid> CARAMEL = FLUIDS
            .register("caramel", () -> new ForgeFlowingFluid.Source(CCFluids.CARAMEL_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_CARAMEL = FLUIDS
            .register("flowing_caramel", () -> new ForgeFlowingFluid.Flowing(CCFluids.CARAMEL_PROPERTIES));

    public static final ForgeFlowingFluid.Properties CARAMEL_PROPERTIES =
            flowingFluidProps(CCFluidTypes.CARAMEL_TYPE, CCFluids.CARAMEL,
                    CCFluids.FLOWING_CARAMEL, CCBlocks.CARAMEL, CCItems.CARAMEL_BUCKET);

    // the "virtual" hot chocolate
    public static final RegistryObject<FlowingFluid> HOT_CHOCOLATE = FLUIDS
            .register("hot_chocolate", () -> new ForgeFlowingFluid.Source(CCFluids.HOT_CHOCOLATE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_HOT_CHOCOLATE = FLUIDS
            .register("flowing_hot_chocolate", () -> new ForgeFlowingFluid.Flowing(CCFluids.HOT_CHOCOLATE_PROPERTIES));

    public static final ForgeFlowingFluid.Properties HOT_CHOCOLATE_PROPERTIES =
            applySweetFluidProps(new ForgeFlowingFluid.Properties(CCFluidTypes.HOT_CHOCOLATE_TYPE,
                    CCFluids.HOT_CHOCOLATE, CCFluids.FLOWING_HOT_CHOCOLATE)).block(CCBlocks.HOT_CHOCOLATE);

    public static ForgeFlowingFluid.Properties applySweetFluidProps(@NotNull ForgeFlowingFluid.Properties properties) {
        return properties.tickRate(25).slopeFindDistance(3).levelDecreasePerBlock(2).explosionResistance(100F);
    }

    public static ForgeFlowingFluid.Properties flowingFluidProps(Supplier<FluidType> type, Supplier<FlowingFluid> still,
                                                                 Supplier<FlowingFluid> flowing, Supplier<LiquidBlock> block,
                                                                 Supplier<BucketItem> bucket) {
        return applySweetFluidProps(new ForgeFlowingFluid.Properties(type, still, flowing))
                .block(block)
                .bucket(bucket);
    }


}
