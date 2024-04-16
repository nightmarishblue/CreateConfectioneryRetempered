package blue.nightmarish.create_confectionery.data;

import blue.nightmarish.create_confectionery.data.loot.CCBlockLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class CCLootTableProvider extends LootTableProvider {
    public CCLootTableProvider(PackOutput pOutput, Set<ResourceLocation> pRequiredTables, List<SubProviderEntry> pSubProviders) {
        super(pOutput, pRequiredTables, pSubProviders);
    }

    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(CCBlockLootTables::new, LootContextParamSets.BLOCK)
        ));
    }
}
