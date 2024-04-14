package blue.nightmarish.create_confectionery.data;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.item.MarshmallowOnStickItem;
import blue.nightmarish.create_confectionery.registry.CCItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class CCItemModelProvider extends ItemModelProvider {
    public CCItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CreateConfectionery.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        CCItems.ITEMS.getEntries().forEach(item -> {
//            if (!validItem(item.get().getClass()))
            Item obj = item.get();
            if (obj instanceof MarshmallowOnStickItem || obj instanceof BlockItem)
                return;
            if (obj instanceof SpawnEggItem)
                spawnEggItem(item);
            else
                simpleItemWithTexture(item);
        });
    }

    private ItemModelBuilder simpleItemWithTexture(RegistryObject<Item> item) {
        return withExistingParent(
                item.getId().getPath(),
                mcLoc("item/generated")
        ).texture(
                "layer0",
                new ResourceLocation(CreateConfectionery.MOD_ID, "item/" + item.getId().getPath())
        );
    }

    private ItemModelBuilder spawnEggItem(RegistryObject<Item> spawnEgg) {
        return withExistingParent(
                spawnEgg.getId().getPath(),
                mcLoc("item/template_spawn_egg")
        );
    }
}
