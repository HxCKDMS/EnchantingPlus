package HxCKDMS.EnchantingPlus.Inventory;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotEnchant extends Slot {
    public ContainerAdvancedEnchantmentTable container;
    public SlotEnchant(ContainerAdvancedEnchantmentTable container, IInventory inventory, int slot, int posX, int posY) {
        super(inventory, slot, posX, posY);
        this.container = container;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return itemStack.isItemEnchantable() || itemStack.isItemEnchanted() || itemStack.getItem() == Items.book || itemStack.getItem() == Items.enchanted_book;
    }
}
