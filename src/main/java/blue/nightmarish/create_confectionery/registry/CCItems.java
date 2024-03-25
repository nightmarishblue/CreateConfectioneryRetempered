package blue.nightmarish.create_confectionery.registry;

import blue.nightmarish.create_confectionery.CCConstants;
import blue.nightmarish.create_confectionery.CCUtils;
import blue.nightmarish.create_confectionery.CreateConfectionery;
import blue.nightmarish.create_confectionery.item.*;
import blue.nightmarish.create_confectionery.item.chocolate.*;
import blue.nightmarish.create_confectionery.item.gingerbread.GingerbreadItem;
import blue.nightmarish.create_confectionery.item.gingerbread.GingerbreadManItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class CCItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CreateConfectionery.MOD_ID);

    // gingerbread
    public static final RegistryObject<Item> GINGERDOUGH = ITEMS
            .register("gingerdough", CCUtils::genericItem);
    public static final RegistryObject<Item> GINGERBREAD = ITEMS
            .register("gingerbread", () -> new GingerbreadItem(CCConstants.GINGERBREAD_EFFECTS));
    public static final RegistryObject<Item> GINGERBREAD_MAN = ITEMS
            .register("gingerbread_man", () -> new GingerbreadManItem(CCConstants.GINGERBREAD_EFFECTS));
    public static final RegistryObject<Item> GINGERBREAD_MAN_SPAWN_EGG = ITEMS
            .register("little_gingerbread_man_spawn_egg", () -> new ForgeSpawnEggItem(
                    CCEntities.LITTLE_GINGERBREAD_MAN, -5611197, -2435116, new Item.Properties()));

    // basic ingredients
    public static final RegistryObject<Item> CRUSHED_COCOA = ITEMS
            .register("crushed_cocoa", CCUtils::genericItem);
    public static final RegistryObject<Item> COCOA_POWDER = ITEMS
            .register("cocoa_powder", CCUtils::genericItem);
    public static final RegistryObject<Item> COCOA_BUTTER = ITEMS
            .register("cocoa_butter", CCUtils::genericItem);

    // other chocolate bars
    public static final RegistryObject<Item> BAR_OF_BLACK_CHOCOLATE = ITEMS
            .register("bar_of_black_chocolate", () -> new ChocolateBarItem(CCConstants.BLACK_CHOC_EFFECTS));
    public static final RegistryObject<Item> BAR_OF_RUBY_CHOCOLATE = ITEMS
            .register("bar_of_ruby_chocolate", () -> new ChocolateBarItem(CCConstants.RUBY_CHOC_EFFECTS));
    public static final RegistryObject<Item> BAR_OF_WHITE_CHOCOLATE = ITEMS
            .register("bar_of_white_chocolate", () -> new ChocolateBarItem(CCConstants.WHITE_CHOC_EFFECTS));

    public static final RegistryObject<Item> BAR_OF_CARAMEL = ITEMS
            .register("bar_of_caramel", () -> new CaramelBarItem(CCConstants.CARAMEL_EFFECTS));

    // full chocolate bars
    public static final RegistryObject<Item> FULL_CHOCOLATE_BAR = ITEMS
            .register("full_chocolate_bar", () -> new FullChocolateBarItem(List.of()));
    public static final RegistryObject<Item> FULL_BLACK_CHOCOLATE_BAR = ITEMS
            .register("full_black_chocolate_bar", () -> new FullChocolateBarItem(CCConstants.BLACK_CHOC_EFFECTS));
    public static final RegistryObject<Item> FULL_RUBY_CHOCOLATE_BAR = ITEMS
            .register("full_ruby_chocolate_bar", () -> new FullChocolateBarItem(CCConstants.RUBY_CHOC_EFFECTS));
    public static final RegistryObject<Item> FULL_WHITE_CHOCOLATE_BAR = ITEMS
            .register("full_white_chocolate_bar", () -> new FullChocolateBarItem(CCConstants.WHITE_CHOC_EFFECTS));

    // glazed berries
    public static final RegistryObject<Item> BLACK_CHOCOLATE_GLAZED_BERRIES = ITEMS
            .register("black_chocolate_glazed_berries", () -> new ChocolateGlazedBerriesItem(CCConstants.BLACK_CHOC_EFFECTS));
    public static final RegistryObject<Item> CARAMEL_GLAZED_BERRIES = ITEMS
            .register("caramel_glazed_berries", () -> new ChocolateGlazedBerriesItem(CCConstants.CARAMEL_EFFECTS));
    public static final RegistryObject<Item> RUBY_CHOCOLATE_GLAZED_BERRIES = ITEMS
            .register("ruby_chocolate_glazed_berries", () -> new ChocolateGlazedBerriesItem(CCConstants.RUBY_CHOC_EFFECTS));
    public static final RegistryObject<Item> WHITE_CHOCOLATE_GLAZED_BERRIES = ITEMS
            .register("white_chocolate_glazed_berries", () -> new ChocolateGlazedBerriesItem(CCConstants.WHITE_CHOC_EFFECTS));

    // marshmallows
    public static final RegistryObject<Item> MARSHMALLOW = ITEMS.register("marshmallow",
                    () -> new ChocolateGlazedMarshmallowItem(List.of(), ConfectionItem.defaultItemProps(),
                            ChocolateGlazedMarshmallowItem.defaultFoodProps().nutrition(2).saturationMod(0.2F)));
    // the sticks too
    public static final RegistryObject<Item> MARSHMALLOW_ON_STICK = ITEMS
            .register("marshmallow_on_a_stick", () -> new MarshmallowOnStickItem(3, 0.4F));
    public static final RegistryObject<Item> CARAMELIZED_MARSHMALLOW_ON_STICK = ITEMS
            .register("caramelized_marshmallow_on_a_stick", () -> new MarshmallowOnStickItem(5, 0.5F));
    // the glazed ones
    public static final RegistryObject<Item> CHOCOLATE_GLAZED_MARSHMALLOW = ITEMS
            .register("chocolate_glazed_marshmallow", () -> new ChocolateGlazedMarshmallowItem(List.of()));
    public static final RegistryObject<Item> BLACK_CHOCOLATE_GLAZED_MARSHMALLOW = ITEMS
            .register("black_chocolate_glazed_marshmallow", () -> new ChocolateGlazedMarshmallowItem(CCConstants.BLACK_CHOC_EFFECTS));
    public static final RegistryObject<Item> RUBY_CHOCOLATE_GLAZED_MARSHMALLOW = ITEMS
            .register("ruby_chocolate_glazed_marshmallow", () -> new ChocolateGlazedMarshmallowItem(CCConstants.RUBY_CHOC_EFFECTS));
    public static final RegistryObject<Item> WHITE_CHOCOLATE_GLAZED_MARSHMALLOW = ITEMS
            .register("white_chocolate_glazed_marshmallow", () -> new ChocolateGlazedMarshmallowItem(CCConstants.WHITE_CHOC_EFFECTS));

    // candy cane!!
    public static final RegistryObject<Item> CANDY_CANE = ITEMS
            .register("candy_cane", () -> new CandyCaneItem(CCConstants.CANDY_CANE_EFFECTS));
    public static final RegistryObject<RecordItem> MUSIC_DISC_THE_BRIGHT_SIDE = ITEMS
            .register("music_disc_the_bright_side",
                    () -> new RecordItem(2, CCSounds.THE_BRIGHT_SIDE, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 3220));
    public static final RegistryObject<Item> HONEY_CANDY = ITEMS
            .register("honey_candy", HoneyCandyItem::new);

    // the super complex chocolate coated candy stuff
    // stuff coated in chocolate
    public static final RegistryObject<Item> CHOCOLATE_COATED_CARAMEL_CANDY = ITEMS.
            register("chocolate_coated_caramel_candy", () -> new ChocolateCandyItem(CCConstants.CARAMEL_EFFECTS));
    public static final RegistryObject<Item> CHOCOLATE_COATED_WHITE_CANDY = ITEMS.
            register("chocolate_coated_white_candy", () -> new ChocolateCandyItem(CCConstants.WHITE_CHOC_EFFECTS));
    public static final RegistryObject<Item> CHOCOLATE_COATED_BLACK_CANDY = ITEMS.
            register("chocolate_coated_black_candy", () -> new ChocolateCandyItem(CCConstants.BLACK_CHOC_EFFECTS));
    public static final RegistryObject<Item> CHOCOLATE_COATED_RUBY_CANDY = ITEMS.
            register("chocolate_coated_ruby_candy", () -> new ChocolateCandyItem(CCConstants.RUBY_CHOC_EFFECTS));
    // stuff coated in dark chocolate
    public static final RegistryObject<Item> BLACK_COATED_CARAMEL_CANDY = ITEMS.
            register("black_coated_caramel_candy", () -> new ChocolateCandyItem(
                    CCUtils.combineEffects(CCConstants.BLACK_CHOC_EFFECTS, CCConstants.CARAMEL_EFFECTS)));
    public static final RegistryObject<Item> BLACK_COATED_WHITE_CANDY = ITEMS.
            register("black_coated_white_candy", () -> new ChocolateCandyItem(
                    CCUtils.combineEffects(CCConstants.BLACK_CHOC_EFFECTS, CCConstants.WHITE_CHOC_EFFECTS)));
    public static final RegistryObject<Item> BLACK_COATED_CHOCOLATE_CANDY = ITEMS.
            register("black_coated_chocolate_candy", () -> new ChocolateCandyItem(CCConstants.BLACK_CHOC_EFFECTS));
    public static final RegistryObject<Item> BLACK_COATED_RUBY_CANDY = ITEMS.
            register("black_coated_ruby_candy", () -> new ChocolateCandyItem(
                    CCUtils.combineEffects(CCConstants.BLACK_CHOC_EFFECTS, CCConstants.RUBY_CHOC_EFFECTS)));
    // stuff coated in white chocolate
    public static final RegistryObject<Item> WHITE_COATED_CARAMEL_CANDY = ITEMS.
            register("white_coated_caramel_candy", () -> new ChocolateCandyItem(
                    CCUtils.combineEffects(CCConstants.WHITE_CHOC_EFFECTS, CCConstants.CARAMEL_EFFECTS)));
    public static final RegistryObject<Item> WHITE_COATED_BLACK_CANDY = ITEMS.
            register("white_coated_black_candy", () -> new ChocolateCandyItem(
                    CCUtils.combineEffects(CCConstants.WHITE_CHOC_EFFECTS, CCConstants.BLACK_CHOC_EFFECTS)));
    public static final RegistryObject<Item> WHITE_COATED_CHOCOLATE_CANDY = ITEMS.
            register("white_coated_chocolate_candy", () -> new ChocolateCandyItem(CCConstants.WHITE_CHOC_EFFECTS));
    public static final RegistryObject<Item> WHITE_COATED_RUBY_CANDY = ITEMS.
            register("white_coated_ruby_candy", () -> new ChocolateCandyItem(
                    CCUtils.combineEffects(CCConstants.WHITE_CHOC_EFFECTS, CCConstants.RUBY_CHOC_EFFECTS)));
    // stuff coated in ruby chocolate
    public static final RegistryObject<Item> RUBY_COATED_CARAMEL_CANDY = ITEMS.
            register("ruby_coated_caramel_candy", () -> new ChocolateCandyItem(
                    CCUtils.combineEffects(CCConstants.RUBY_CHOC_EFFECTS, CCConstants.CARAMEL_EFFECTS)));
    public static final RegistryObject<Item> RUBY_COATED_BLACK_CANDY = ITEMS.
            register("ruby_coated_black_candy", () -> new ChocolateCandyItem(
                    CCUtils.combineEffects(CCConstants.RUBY_CHOC_EFFECTS, CCConstants.BLACK_CHOC_EFFECTS)));
    public static final RegistryObject<Item> RUBY_COATED_CHOCOLATE_CANDY = ITEMS.
            register("ruby_coated_chocolate_candy", () -> new ChocolateCandyItem(CCConstants.RUBY_CHOC_EFFECTS));
    public static final RegistryObject<Item> RUBY_COATED_WHITE_CANDY = ITEMS.
            register("ruby_coated_white_candy", () -> new ChocolateCandyItem(
                    CCUtils.combineEffects(CCConstants.RUBY_CHOC_EFFECTS, CCConstants.WHITE_CHOC_EFFECTS)));

    // chocolate and caramel buckets
    public static final RegistryObject<BucketItem> BLACK_CHOCOLATE_BUCKET = ITEMS
            .register("black_chocolate_bucket", () -> new BucketItem(CCFluids.BLACK_CHOCOLATE, bucketProps()));
    public static final RegistryObject<BucketItem> RUBY_CHOCOLATE_BUCKET = ITEMS
            .register("ruby_chocolate_bucket", () -> new BucketItem(CCFluids.RUBY_CHOCOLATE, bucketProps()));
    public static final RegistryObject<BucketItem> WHITE_CHOCOLATE_BUCKET = ITEMS
            .register("white_chocolate_bucket", () -> new BucketItem(CCFluids.WHITE_CHOCOLATE, bucketProps()));

    public static final RegistryObject<BucketItem> CARAMEL_BUCKET = ITEMS
            .register("caramel_bucket", () -> new BucketItem(CCFluids.CARAMEL, bucketProps()));

    public static Item.Properties bucketProps() {
        return new Item.Properties()
                .stacksTo(1)
                .craftRemainder(Items.BUCKET);
    }
}
