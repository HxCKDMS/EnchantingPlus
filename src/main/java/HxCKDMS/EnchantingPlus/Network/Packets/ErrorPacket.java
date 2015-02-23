package HxCKDMS.EnchantingPlus.Network.Packets;

import HxCKDMS.EnchantingPlus.Client.Gui.GuiModTable;
import HxCKDMS.EnchantingPlus.Network.AbstractPacket;
import cpw.mods.fml.client.FMLClientHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ErrorPacket extends AbstractPacket{
    protected String error;


    public ErrorPacket() {}

    public ErrorPacket(String localizedMessage) {
        this.error = localizedMessage;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf byteBuf) {
        byteBuf.writeInt(error.length());
        byteBuf.writeBytes(error.getBytes());
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf byteBuf) {
        int length = byteBuf.readInt();
        error = byteBuf.readBytes(length).toString();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        Minecraft client = FMLClientHandler.instance().getClient();

        if (client.currentScreen instanceof GuiModTable) {
            ((GuiModTable) client.currentScreen).error = error;
        }
    }

    @Override
    public void handleServerSide(EntityPlayer player) {

    }
}
