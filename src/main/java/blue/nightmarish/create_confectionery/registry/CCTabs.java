package blue.nightmarish.create_confectionery.registry;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CCTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateConfectionery.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CC_TAB = TABS.register("create_confectionery_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("item_group.create_confectionery.create_confectionery_tab"))
                    .icon(() -> new ItemStack(CCItems.GINGERBREAD_MAN.get()))
                    .displayItems(((params, tab) -> CCItems.ITEMS.getEntries().forEach(itemReg -> {
                        if (itemReg.get().equals(CCItems.PLAIN_HOT_CHOCOLATE.get())) return; // hide plain hot chocolate
                        tab.accept(itemReg.get());
                    })))
                    .build());
}
