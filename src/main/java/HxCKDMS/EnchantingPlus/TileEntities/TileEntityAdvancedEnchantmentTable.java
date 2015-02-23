package HxCKDMS.EnchantingPlus.TileEntities;

import HxCKDMS.EnchantingPlus.Api.HxCRegistry;
import HxCKDMS.EnchantingPlus.Api.EnumHxCRegistryType;
import HxCKDMS.EnchantingPlus.Client.Render.TileEntity.TileEntityAdvancedEnchantmentTableRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityEnchantmentTable;

@HxCRegistry(unlocalizedName = "TileEntityAdvancedEnchantmentTable", registryType = EnumHxCRegistryType.TILEENTITY, tileEntitySpecialRenderer = TileEntityAdvancedEnchantmentTableRenderer.class)
public class TileEntityAdvancedEnchantmentTable extends TileEntityEnchantmentTable {
    public ItemStack itemInTable;
    
    public void writeSyncedNBT(NBTTagCompound tagCompound){
        NBTTagList tagList = new NBTTagList();
        if(itemInTable != null){
            NBTTagCompound tagCompound1 = new NBTTagCompound();
            itemInTable.writeToNBT(tagCompound1);
            tagList.appendTag(tagCompound1);
        }
        
        tagCompound.setTag("Item", tagList);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        writeSyncedNBT(tagCompound);
    }

    public void readSyncedNBT(NBTTagCompound tagCompound){
        NBTTagList tagList = tagCompound.getTagList("Item", 10);
        
        itemInTable = null;
        for(int i = 0; i < tagList.tagCount(); i++){
            NBTTagCompound tagCompound1 = tagList.getCompoundTagAt(i);
            itemInTable = ItemStack.loadItemStackFromNBT(tagCompound1);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        readSyncedNBT(tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet) {
       readSyncedNBT(packet.func_148857_g());
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeSyncedNBT(tagCompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tagCompound);
    }
}
