package echoenchanting.mixin;

import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public class AnvilMenuMixin {
    @Shadow
    @Final
    private DataSlot cost;

    @Inject(method = "createResult", at = @At(value = "TAIL"))
    private void preventEnchantedBooks(CallbackInfo info) {
        ItemCombinerMenuAccessor accessor = (ItemCombinerMenuAccessor) this;

        ItemStack addition = accessor.getInputSlots().getItem(1);

        if (addition.is(Items.ENCHANTED_BOOK)) {
            accessor.getResultSlots().setItem(0, ItemStack.EMPTY);
            this.cost.set(0);
        }
    }
}
