package HxCKDMS.EnchantingPlus.Network.Packets;

import HxCKDMS.EnchantingPlus.EnchantingPlus;
import HxCKDMS.EnchantingPlus.Lib.ConfigurationSettings;
import HxCKDMS.EnchantingPlus.Lib.References;
import HxCKDMS.EnchantingPlus.Network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public class GuiPacket extends AbstractPacket {
    private int guiId;
    private int xPos;
    private int yPos;
    private int zPos;
    private String username;
    
    public GuiPacket(String displayName, int guiId, int xPos, int yPos, int zPos) {
        this.username = displayName;
        this.guiId = guiId;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
    }

    public GuiPacket() {}

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf byteBuf) {
        byteBuf.writeInt(username.length());
        byteBuf.writeBytes(username.getBytes());
        byteBuf.writeInt(guiId);
        byteBuf.writeInt(xPos);
        byteBuf.writeInt(yPos);
        byteBuf.writeInt(zPos);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf byteBuf) {
        int length = byteBuf.readInt();
        username = byteBuf.readBytes(length).toString();
        guiId = byteBuf.readInt();
        xPos = byteBuf.readInt();
        yPos = byteBuf.readInt();
        zPos = byteBuf.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {

    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        switch (guiId)
        {
            case References.MOD_ENCHANT_GUI_ID:
                if (ConfigurationSettings.useMod)
                {
                    player.openGui(EnchantingPlus.instance, guiId, player.worldObj, xPos, yPos, zPos);
                }
                break;

            case References.VANILLA_ENCHANT_GUI_ID:
                player.openGui(EnchantingPlus.instance, guiId, player.worldObj, xPos, yPos, zPos);
                break;
        }
    }
}
