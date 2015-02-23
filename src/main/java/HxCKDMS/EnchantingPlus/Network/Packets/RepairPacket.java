package HxCKDMS.EnchantingPlus.Network.Packets;

import HxCKDMS.EnchantingPlus.EnchantingPlus;
import HxCKDMS.EnchantingPlus.Inventory.ContainerAdvancedEnchantmentTable;
import HxCKDMS.EnchantingPlus.Lib.References;
import HxCKDMS.EnchantingPlus.Network.AbstractPacket;
import HxCKDMS.HxCCore.Utils.LogHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class RepairPacket extends AbstractPacket {
    protected int totalCost;
    protected int repairAmount;

    public RepairPacket() {}

    public RepairPacket(int totalCost, int repairAmount) {
        this.totalCost = totalCost;
        this.repairAmount = repairAmount;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf byteBuf) {
        byteBuf.writeInt(totalCost);
        byteBuf.writeInt(repairAmount);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf byteBuf) {
        totalCost = byteBuf.readInt();
        repairAmount = byteBuf.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {

    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        {
            if (player.openContainer instanceof ContainerAdvancedEnchantmentTable)
            {
                try
                {
                    ((ContainerAdvancedEnchantmentTable) player.openContainer).repair(player, totalCost, repairAmount);
                }
                catch (Exception e)
                {
                    LogHelper.info("Repair failed because: " + e.getLocalizedMessage(), References.MOD_NAME);
                    EnchantingPlus.packetPipeline.sendTo(new ErrorPacket(e.getLocalizedMessage()), (EntityPlayerMP) player);
                }
                player.openContainer.detectAndSendChanges();
            }
        }
    }
}
