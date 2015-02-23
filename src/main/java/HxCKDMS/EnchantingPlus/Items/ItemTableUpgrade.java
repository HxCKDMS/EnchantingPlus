package HxCKDMS.EnchantingPlus.Items;

import HxCKDMS.EnchantingPlus.Api.EnumHxCRegistryType;
import HxCKDMS.EnchantingPlus.Api.HxCRegistry;
import HxCKDMS.EnchantingPlus.Blocks.BlockAdvancedEnchantmentTable;
import HxCKDMS.EnchantingPlus.Registry.ModRegistry;
import HxCKDMS.EnchantingPlus.TileEntities.TileEntityAdvancedEnchantmentTable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@HxCRegistry(unlocalizedName = "ItemTableUpgrade", registryType = EnumHxCRegistryType.ITEM)
@SuppressWarnings("unused")
public class ItemTableUpgrade extends Item {
    public ItemTableUpgrade() {
        setMaxStackSize(16);
        setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
        this.itemIcon = iconRegister.registerIcon("enchantingplus:enchanting_table_upgrade");
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float px, float py, float pz)
    {
        if (!world.isRemote)
        {
            Block block = world.getBlock(x, y, z);

            if (block instanceof BlockEnchantmentTable)
            {
                world.setBlock(x, y, z, ModRegistry.blockRegistry.get(BlockAdvancedEnchantmentTable.class));
                world.setTileEntity(x, y, z, new TileEntityAdvancedEnchantmentTable());
                itemStack.stackSize--;
                return true;
            }
        }

        return false;
    }
}
