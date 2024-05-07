package blue.nightmarish.create_confectionery.event;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.data.*;
import blue.nightmarish.create_confectionery.entity.custom.GingerbreadManEntity;
import blue.nightmarish.create_confectionery.registry.CCEntities;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

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
    }
}
