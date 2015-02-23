package HxCKDMS.EnchantingPlus.Client.Gui;

import HxCKDMS.EnchantingPlus.EnchantingPlus;
import HxCKDMS.EnchantingPlus.Lib.ConfigurationSettings;
import HxCKDMS.EnchantingPlus.Lib.References;
import HxCKDMS.EnchantingPlus.Lib.Strings;
import HxCKDMS.EnchantingPlus.Network.Packets.GuiPacket;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

public class GuiVanillaTable extends GuiEnchantment {
    
    private final EntityPlayer player;
    private final int xPos;
    private final int yPos;
    private final int zPos;
    
    public GuiVanillaTable(InventoryPlayer inventoryPlayer, World world, int x, int y, int z, String name) {
        super(inventoryPlayer, world, x, y, z, name);
        player = inventoryPlayer.player;
        xPos = x;
        yPos = y;
        zPos = z;
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        super.actionPerformed(guiButton);

        switch (guiButton.id) {
            case 0:
                EnchantingPlus.packetPipeline.sendToServer(new GuiPacket(player.getDisplayName(), References.MOD_ENCHANT_GUI_ID, xPos, yPos, zPos));
        }
    }

    @Override
    public void drawScreen(int xSize, int ySize, float opacity) {
        super.drawScreen(xSize, ySize, opacity);
        final String displayText = String.format("%s: %s", Strings.playerLevel, player.experienceLevel);
        drawCreativeTabHoveringText(displayText, guiLeft - 20 - fontRendererObj.getStringWidth(displayText), guiTop + fontRendererObj.FONT_HEIGHT + 8);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        super.initGui();
        final String s = "Mod";
        if (ConfigurationSettings.useMod)
        {
            buttonList.add(new GuiButton(0, guiLeft + xSize + 10, guiTop + 5, fontRendererObj.getStringWidth(s) + 10, 20, s));
        }
    }
}
