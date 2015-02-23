package HxCKDMS.EnchantingPlus.Inventory;

import net.minecraft.inventory.InventoryBasic;

public class SlotAdvancedEnchantmentTable extends InventoryBasic {
    ContainerAdvancedEnchantmentTable container;
    
    public SlotAdvancedEnchantmentTable(ContainerAdvancedEnchantmentTable container, String inventoryName, boolean hasCustomName, int slotCount) {
        super(inventoryName, hasCustomName, slotCount);
        this.container = container;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        container.onCraftMatrixChanged(this);
    }
}
