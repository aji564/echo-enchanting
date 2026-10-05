package echoenchanting.menu;

import echoenchanting.registry.ModMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.Objects;

public class EchoEnchantingMenu extends AbstractContainerMenu {
    private final Container enchantSlots;
    private final ContainerLevelAccess access;

    private static final int EQUIPMENT_SLOT = 0;
    private static final int BOOK_SLOT = 1;
    private static final int ECHO_SHARD_SLOT = 2;


    public EchoEnchantingMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public EchoEnchantingMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        super(ModMenuTypes.ECHO_ENCHANTING, containerId);

        this.enchantSlots = new SimpleContainer(3) {
            @Override
            public void setChanged() {
                super.setChanged();
                EchoEnchantingMenu.this.slotsChanged(this);
            }
        };

        this.access = access;

        this.addSlot(new Slot(this.enchantSlots, EQUIPMENT_SLOT, 10, 19) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.isEnchantable();
            }
        });
        this.addSlot(new Slot(this.enchantSlots, BOOK_SLOT, 38, 19) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Items.ENCHANTED_BOOK);
            }
        });
        this.addSlot(new Slot(this.enchantSlots, ECHO_SHARD_SLOT, 24, 39) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Items.ECHO_SHARD);
            }
        });

        this.addStandardInventorySlots(inventory, 8, 84);
    }

    public ItemStack quickMoveStack(final Player player, final int slotIndex) {
        ItemStack clicked = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(slotIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            clicked = stack.copy();
            // Moving from menu to inventory, slots 0-2 are menu and 3-39 are player inventory
            if (slotIndex < 3) {
                if (!this.moveItemStackTo(stack, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
            }
            // Moving from inventory to menu
            else {
                if (stack.is(Items.ENCHANTED_BOOK)) {
                    if (!this.moveItemStackTo(stack, BOOK_SLOT, 2, true)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (stack.is(Items.ECHO_SHARD)) {
                    if (!this.moveItemStackTo(stack, ECHO_SHARD_SLOT, 3, true)) {
                        return ItemStack.EMPTY;
                    }
                }
                else {
                    if (!this.moveItemStackTo(stack, EQUIPMENT_SLOT, 1, true)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == clicked.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
        }

        return clicked;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);

        if (!player.level().isClientSide()) {
            this.clearContainer(player, this.enchantSlots);
        }
    }

    public boolean stillValid(final Player player) {
        return stillValid(this.access, player, Blocks.ENCHANTING_TABLE);
    }
}
