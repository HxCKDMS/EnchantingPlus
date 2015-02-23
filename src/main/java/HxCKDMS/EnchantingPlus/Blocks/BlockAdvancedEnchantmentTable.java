package HxCKDMS.EnchantingPlus.Blocks;

import HxCKDMS.EnchantingPlus.Api.HxCRegistry;
import HxCKDMS.EnchantingPlus.Api.EnumHxCRegistryType;
import HxCKDMS.EnchantingPlus.EnchantingPlus;
import HxCKDMS.EnchantingPlus.TileEntities.TileEntityAdvancedEnchantmentTable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

@HxCRegistry(unlocalizedName = "BlockAdvancedEnchantmentTable", registryType = EnumHxCRegistryType.BLOCK)
@SuppressWarnings("unused")
public class BlockAdvancedEnchantmentTable extends BlockEnchantmentTable {
    
    @HxCRegistry.Instance
    public static BlockAdvancedEnchantmentTable instance;
    
    @SideOnly(Side.CLIENT)
    public IIcon[] icons;
    
    public BlockAdvancedEnchantmentTable() {
        setCreativeTab(CreativeTabs.tabDecorations);
        setHardness(5F);
        setResistance(2000F);
        setLightLevel(1F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int metadata){
        return new TileEntityAdvancedEnchantmentTable();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float px, float py, float pz) {
        if(world.isRemote) return true;
        
        player.openGui(EnchantingPlus.instance, 0, world, x, y, z);
        return true;
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        super.randomDisplayTick(world, x, y, z, random);
        for (int i = 0; i < 4; i++){
            double xPos = x + random.nextFloat();
            double yPos = y + random.nextFloat();
            double zPos = z + random.nextFloat();
            int modifier = random.nextInt(2) * 2 - 1;
            double velX = (random.nextFloat() - 0.5d) * 0.5D;
            double velY = (random.nextFloat() - 0.5d) * 0.5D;
            double velZ = (random.nextFloat() - 0.5d) * 0.5D;
            
            if(!(world.getBlock(x - 1, y, z) instanceof BlockAdvancedEnchantmentTable) && !(world.getBlock(x + 1, y, z)instanceof BlockAdvancedEnchantmentTable)){
                xPos = x + 0.0D + 0.25D * modifier;
                velX = random.nextFloat() * 2.0F * modifier;
            }else{
                zPos = z + 0.0D + 0.25D * modifier;
                velZ = random.nextFloat() * 2.0F * modifier;
            }
            world.spawnParticle("portal", xPos, yPos, zPos, velX, velY, velZ);
        }
    }

    @Override
    public IIcon getIcon(int side, int metadata){
        return icons[side > 1 ? 2 : side];
    }
    
    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        icons = new IIcon[3];

        icons[0] = iconRegister.registerIcon("enchantingplus:enchanting_table_bottom");
        icons[1] = iconRegister.registerIcon("enchantingplus:enchanting_table_top");
        icons[2] = iconRegister.registerIcon("enchantingplus:enchanting_table_side");
        
    }
}
