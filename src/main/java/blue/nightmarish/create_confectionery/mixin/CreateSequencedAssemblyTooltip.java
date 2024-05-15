package blue.nightmarish.create_confectionery.mixin;

import blue.nightmarish.create_confectionery.registry.CCItems;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SequencedAssemblyRecipe.class)
public abstract class CreateSequencedAssemblyTooltip implements Recipe<RecipeWrapper> {
    @Inject(method = "addToTooltip", at = @At("HEAD"), cancellable = true, remap = false)
    private static void cancelAddToTooltip(ItemTooltipEvent event, CallbackInfo ci) {
        if (event.getItemStack().getItem().equals(CCItems.PLAIN_HOT_CHOCOLATE.get()))
            ci.cancel();
    }
}
