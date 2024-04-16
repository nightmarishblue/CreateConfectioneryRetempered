package blue.nightmarish.create_confectionery.data.loot;

import blue.nightmarish.create_confectionery.registry.CCBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

@MethodsReturnNonnullByDefault
public class CCBlockLootTables extends BlockLootSubProvider {
    protected CCBlockLootTables(Set<Item> pExplosionResistant, FeatureFlagSet pEnabledFeatures) {
        super(pExplosionResistant, pEnabledFeatures);
    }

    public CCBlockLootTables() {
        this(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        CCBlocks.BLOCKS.getEntries().forEach(block -> {
            if (block.get() instanceof LiquidBlock)
                return;
            this.dropSelf(block.get());
        });
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return CCBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
