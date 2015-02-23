package HxCKDMS.EnchantingPlus.Network;

import HxCKDMS.EnchantingPlus.Client.Gui.GuiModTable;
import HxCKDMS.EnchantingPlus.Client.Gui.GuiVanillaTable;
import HxCKDMS.EnchantingPlus.Inventory.ContainerAdvancedEnchantmentTable;
import HxCKDMS.EnchantingPlus.Lib.References;
import HxCKDMS.EnchantingPlus.TileEntities.TileEntityAdvancedEnchantmentTable;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity;
        
        switch(ID){
            case References.MOD_ENCHANT_GUI_ID:
                tileEntity = world.getTileEntity(x, y, z);
                if (tileEntity == null || !(tileEntity instanceof TileEntityAdvancedEnchantmentTable)) return null;
                return new GuiModTable(player.inventory, world, x, y, z, (TileEntityAdvancedEnchantmentTable) tileEntity);
            case References.VANILLA_ENCHANT_GUI_ID:
                tileEntity = world.getTileEntity(x, y, z);
                if (tileEntity == null || !(tileEntity instanceof TileEntityAdvancedEnchantmentTable)) return null;
                return new GuiVanillaTable(player.inventory, world, x, y, z, "");
            default:
                return null;
        }
    }

    @Override
    public Object getServerGuiElement(int ID, final EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity;
        switch (ID) {
            case References.MOD_ENCHANT_GUI_ID:
                tileEntity = world.getTileEntity(x, y, z);
                if (tileEntity == null || !(tileEntity instanceof TileEntityAdvancedEnchantmentTable)) {
                    return null;
                }
                return new ContainerAdvancedEnchantmentTable(player.inventory, world, x, y, z, (TileEntityAdvancedEnchantmentTable) tileEntity);
            case References.VANILLA_ENCHANT_GUI_ID:
                tileEntity = world.getTileEntity(x, y, z);
                if (tileEntity == null || !(tileEntity instanceof TileEntityAdvancedEnchantmentTable)) {
                    return null;
                }
                return new ContainerEnchantment(player.inventory, world, x, y, z) {
                    @Override
                    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
                    {
                        return !player.isDead;
                    }
                };
            default:
                return null;
        }
    }
}
