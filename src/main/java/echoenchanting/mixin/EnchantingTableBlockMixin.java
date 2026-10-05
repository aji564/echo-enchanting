package echoenchanting.mixin;

import echoenchanting.menu.EchoEnchantingMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantingTableBlock.class)
public class EnchantingTableBlockMixin {
    @Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
    private void openEchoEnchantingMenu(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> info) {
        if (!level.isClientSide()) {
            player.openMenu(new SimpleMenuProvider((containerId, inventory, playerEntity)
                    -> new EchoEnchantingMenu(containerId, inventory, ContainerLevelAccess.create(level, pos)), Component.literal("Enchant"))
            );
        }

        info.setReturnValue(InteractionResult.SUCCESS);
    }
}
