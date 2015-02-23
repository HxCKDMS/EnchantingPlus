package HxCKDMS.EnchantingPlus.Network.Packets;

import HxCKDMS.EnchantingPlus.Inventory.ContainerAdvancedEnchantmentTable;
import HxCKDMS.EnchantingPlus.Lib.References;
import HxCKDMS.EnchantingPlus.Network.AbstractPacket;
import HxCKDMS.HxCCore.Utils.LogHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;

public class EnchantPacket extends AbstractPacket {
    protected int totalCost;
    protected HashMap<Integer, Integer> levels = new HashMap<Integer, Integer>();
    protected HashMap<Integer, Integer> enchants = new HashMap<Integer, Integer>();

    public EnchantPacket() {}

    public EnchantPacket(HashMap<Integer, Integer> enchants, HashMap<Integer, Integer> levels, int totalCost) {
        this.enchants = enchants;
        this.levels = levels;
        this.totalCost = totalCost;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf byteBuf) {
        byteBuf.writeInt(totalCost);
        byteBuf.writeInt(enchants.size());

        for (Integer enchantmentId : enchants.keySet()) {
            byteBuf.writeInt(enchantmentId);
            byteBuf.writeInt(enchants.get(enchantmentId));
        }

        byteBuf.writeInt(levels.size());

        for (Integer enchantmentId : levels.keySet()) {
            byteBuf.writeInt(enchantmentId);
            byteBuf.writeInt(levels.get(enchantmentId));
        }
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf byteBuf) {
        final HashMap<Integer, Integer> enchants = new HashMap<Integer, Integer>();
        final HashMap<Integer, Integer> levels = new HashMap<Integer, Integer>();

        this.totalCost = byteBuf.readInt();

        int size = byteBuf.readInt();

        for (int i = 0; i < size; i++)
        {
            enchants.put(byteBuf.readInt(), byteBuf.readInt());
        }

        size = byteBuf.readInt();

        for (int i = 0; i < size; i++)
        {
            levels.put(byteBuf.readInt(), byteBuf.readInt());
        }

        this.enchants = enchants;
        this.levels = levels;
    }

    @Override
    public void handleClientSide(EntityPlayer player) {

    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        if (player.openContainer instanceof ContainerAdvancedEnchantmentTable)
        {
            try
            {
                ((ContainerAdvancedEnchantmentTable) player.openContainer).enchant(player, enchants, levels, totalCost);
            }
            catch (final Exception e)
            {
                LogHelper.info("Enchanting failed because: " + e.getLocalizedMessage(), References.MOD_NAME);
            }
            player.openContainer.detectAndSendChanges();
        }
    }
}
