package blue.nightmarish.create_confectionery;

import blue.nightmarish.create_confectionery.network.CCNetManager;
import blue.nightmarish.create_confectionery.registry.*;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CreateConfectionery.MOD_ID)
public class CreateConfectionery
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "create_confectionery";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateConfectionery()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // register the game objects alphabetically
        CCBlocks.BLOCKS.register(modEventBus);
        CCEffects.EFFECTS.register(modEventBus);
        CCEntities.ENTITY_TYPES.register(modEventBus);
        CCFluids.FLUIDS.register(modEventBus);
        CCFluidTypes.FLUID_TYPES.register(modEventBus);
        CCItems.ITEMS.register(modEventBus);
        CCSounds.SOUND_EVENTS.register(modEventBus);
        CCTabs.TABS.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            CCNetManager.register();
        });
    }
}
