package HxCKDMS.EnchantingPlus.Client.Render.TileEntity;

import HxCKDMS.EnchantingPlus.TileEntities.TileEntityAdvancedEnchantmentTable;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityAdvancedEnchantmentTableRenderer extends TileEntitySpecialRenderer {
    private ModelBook enchantmentBook = new ModelBook();
    private ResourceLocation enchantmentBookTexture = new ResourceLocation("enchantingplus:textures/gui/enchantingplus_book.png");
    
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTick) {
        if(tileEntity instanceof TileEntityAdvancedEnchantmentTable){
            TileEntityAdvancedEnchantmentTable tileEntityAdvancedEnchantmentTable = (TileEntityAdvancedEnchantmentTable) tileEntity;

            GL11.glPushMatrix();
            {
                GL11.glTranslatef((float)x + 0.5F, (float)y + 0.75F, (float)z + 0.5F);
                float f1 = (float)tileEntityAdvancedEnchantmentTable.field_145926_a + partialTick;
                GL11.glTranslatef(0.0F, 0.1F + MathHelper.sin(f1 * 0.1F) * 0.01F, 0.0F);
                float f2;

                for (f2 = tileEntityAdvancedEnchantmentTable.field_145928_o - tileEntityAdvancedEnchantmentTable.field_145925_p; f2 >= (float)Math.PI; f2 -= ((float)Math.PI * 2F)) {}

                while (f2 < -(float)Math.PI)
                {
                    f2 += ((float)Math.PI * 2F);
                }

                float f3 = tileEntityAdvancedEnchantmentTable.field_145925_p + f2 * partialTick;
                GL11.glRotatef(-f3 * 180.0F / (float)Math.PI, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(80.0F, 0.0F, 0.0F, 1.0F);
                this.bindTexture(enchantmentBookTexture);
                float f4 = tileEntityAdvancedEnchantmentTable.field_145931_j + (tileEntityAdvancedEnchantmentTable.field_145933_i - tileEntityAdvancedEnchantmentTable.field_145931_j) * partialTick + 0.25F;
                float f5 = tileEntityAdvancedEnchantmentTable.field_145931_j + (tileEntityAdvancedEnchantmentTable.field_145933_i - tileEntityAdvancedEnchantmentTable.field_145931_j) * partialTick + 0.75F;
                f4 = (f4 - (float)MathHelper.truncateDoubleToInt((double)f4)) * 1.6F - 0.3F;
                f5 = (f5 - (float)MathHelper.truncateDoubleToInt((double)f5)) * 1.6F - 0.3F;

                if (f4 < 0.0F) {
                    f4 = 0.0F;
                }

                if (f5 < 0.0F) {
                    f5 = 0.0F;
                }

                if (f4 > 1.0F) {
                    f4 = 1.0F;
                }

                if (f5 > 1.0F) {
                    f5 = 1.0F;
                }

                float f6 = tileEntityAdvancedEnchantmentTable.field_145927_n + (tileEntityAdvancedEnchantmentTable.field_145930_m - tileEntityAdvancedEnchantmentTable.field_145927_n) * partialTick;
                GL11.glEnable(GL11.GL_CULL_FACE);
                this.enchantmentBook.render((Entity) null, f1, f4, f5, f6, 0.0F, 0.0625F);
            }
            GL11.glPopMatrix();
        }
    }
}
