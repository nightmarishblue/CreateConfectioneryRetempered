package blue.nightmarish.create_confectionery.data;

import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.registry.CCItems;
import com.simibubi.create.AllTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeItemTagsProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CCItemTagGenerator extends ItemTagsProvider {
    public static TagKey<Item> GINGERBREAD_MAN_FOODS = itemTag("gingerbread_man_foods");
    public static TagKey<Item> GINGERBREAD_MAN_TAME_ITEMS = itemTag("gingerbread_man_tame_items");

    public CCItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, CreateConfectionery.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ItemTags.MUSIC_DISCS).add(CCItems.MUSIC_DISC_THE_BRIGHT_SIDE.get());
        this.tag(GINGERBREAD_MAN_FOODS).add(
                CCItems.GINGERDOUGH.get(),
                Items.SUGAR
        );
        this.tag(GINGERBREAD_MAN_TAME_ITEMS).add(
                CCItems.CANDY_CANE.get(),
                Items.HONEY_BOTTLE,
                Items.COOKIE
        );
        this.tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag).add(
                CCItems.PLAIN_HOT_CHOCOLATE.get(),
                CCItems.HOT_CHOCOLATE.get()
        );
    }

    public static TagKey<Item> itemTag(String name) {
        return ItemTags.create(new ResourceLocation(CreateConfectionery.MOD_ID, name));
    }
}
