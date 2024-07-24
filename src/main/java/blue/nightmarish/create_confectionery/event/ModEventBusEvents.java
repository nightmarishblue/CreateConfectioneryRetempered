package blue.nightmarish.create_confectionery.event;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.data.*;
import blue.nightmarish.create_confectionery.data.processing.CCProcessingRecipeGen;
import blue.nightmarish.create_confectionery.data.processing.CCSequencedAssemblyRecipes;
import blue.nightmarish.create_confectionery.entity.custom.GingerbreadManEntity;
import blue.nightmarish.create_confectionery.registry.CCEntities;
import blue.nightmarish.create_confectionery.registry.CCFluidTypes;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = CreateConfectionery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(CCEntities.LITTLE_GINGERBREAD_MAN.get(), GingerbreadManEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // recipes goes heres
        generator.addProvider(event.includeServer(), new CCRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), CCLootTableProvider.create(packOutput));

        generator.addProvider(event.includeClient(), new CCBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new CCItemModelProvider(packOutput, existingFileHelper));

        CCBlockTagProvider blockTagProvider = generator.addProvider(event.includeServer(),
                new CCBlockTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(),
                new CCItemTagGenerator(packOutput, lookupProvider, blockTagProvider.contentsGetter(), existingFileHelper));
        // register the processing recipes
        generator.addProvider(true, new CCSequencedAssemblyRecipes(packOutput));
        CCProcessingRecipeGen.registerAll(generator, packOutput);
    }

    @SubscribeEvent
    public static void registerFluidInteractions(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            fluidInteraction(ForgeMod.LAVA_TYPE, CCFluidTypes.DARK_CHOCOLATE_TYPE, AllPaletteStoneTypes.SCORCHIA.getBaseBlock().get());
            fluidInteraction(ForgeMod.LAVA_TYPE, CCFluidTypes.WHITE_CHOCOLATE_TYPE, Blocks.CALCITE);
            fluidInteraction(ForgeMod.LAVA_TYPE, CCFluidTypes.RUBY_CHOCOLATE_TYPE, AllPaletteStoneTypes.GRANITE.getBaseBlock().get());
            fluidInteraction(ForgeMod.LAVA_TYPE, CCFluidTypes.CARAMEL_TYPE, Blocks.SANDSTONE); // not so sure about this one
        });
    }

    private static void fluidInteraction(Supplier<FluidType> input, Supplier<FluidType> add, Block result) {
        fluidInteraction(input.get(), add.get(), result.defaultBlockState());
    }

    private static void fluidInteraction(FluidType input, FluidType add, BlockState result) {
        FluidInteractionRegistry.addInteraction(input, new FluidInteractionRegistry.InteractionInformation(add, result));
    }
}
