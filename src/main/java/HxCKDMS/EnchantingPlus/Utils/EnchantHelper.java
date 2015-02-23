package HxCKDMS.EnchantingPlus.Utils;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.HashMap;
import java.util.Map;

/**
 * @user odininon
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

/**
 * Helper class with Enchanting functions
 */
public class EnchantHelper
{

    /**
     * Checks to see if an enchantment can enchant an item
     *
     * @param itemStack the item to check
     * @param obj       the enchantment to add
     * @return true is item can accept the enchantment
     */
    public static boolean canEnchantItem(ItemStack itemStack, Enchantment obj)
    {
        // Item.enchantedBook.get(new EnchantmentData(obj, 1));

        return itemStack != null && itemStack.getItem() == Items.book && obj.isAllowedOnBooks() || obj != null && obj.canApplyAtEnchantingTable(itemStack) ;
    }

    /**
     * checks to see if item is enchantable
     *
     * @param itemStack the item to check
     * @return true if item can accept more enchantments
     */
    public static boolean isItemEnchantable(ItemStack itemStack)
    {
        boolean flag = true;
        if (itemStack.hasTagCompound())
        {
            flag = !itemStack.getTagCompound().hasKey("charge");
        }

        return itemStack.getItem().getItemEnchantability() > 0 && (itemStack.getItem() == Items.book || itemStack.isItemEnchantable() && flag);
    }

    /**
     * Checks to see if item is enchanted
     *
     * @param itemStack the item to check
     * @return true if item is enchanted
     */
    public static boolean isItemEnchanted(ItemStack itemStack)
    {
        return itemStack.hasTagCompound()
                && (itemStack.getItem() != Items.enchanted_book ? itemStack.stackTagCompound.hasKey("ench") : itemStack.stackTagCompound.hasKey("StoredEnchantments"));
    }

    /**
     * adds enchantments to an item
     *  @param map       map of enchantments to add
     * @param itemStack the item to add enchantments to
     * @param levels
     * @param player
     */
    public static ItemStack setEnchantments(Map<?, ?> map, ItemStack itemStack, HashMap<Integer, Integer> levels, EntityPlayer player)
    {
        final NBTTagList nbttaglist = new NBTTagList();

        NBTTagList restrictions;

        if (itemStack.hasTagCompound())
        {
            restrictions = itemStack.getTagCompound().getTagList("restrictions", 10);
        }
        else
        {
            restrictions = new NBTTagList();
        }

        for (final Object o : map.keySet())
        {
            final int i = (Integer) o;
            final NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setShort("id", (short) i);
            nbttagcompound.setShort("lvl", (short) ((Integer) map.get(i)).intValue());
            nbttaglist.appendTag(nbttagcompound);

            int startLevel = (Integer) map.get(i);
            try
            {
                startLevel = levels.get(i);
            }
            catch (NullPointerException e)
            {

            }

            for (int y = startLevel; y <= (Integer) map.get(i); y++)
            {
                if (containsKey(restrictions, i, y))
                {
                    continue;
                }

                NBTTagCompound compound = new NBTTagCompound();
                compound.setShort("id", (short) i);
                compound.setShort("lvl", (short) y);
                compound.setString("player", player.getDisplayName());
                restrictions.appendTag(compound);

            }
        }

        if (itemStack.getItem() == Items.book)
        {
            itemStack = new ItemStack(Items.enchanted_book);
        }

        if (nbttaglist.tagCount() > 0)
        {
            if (itemStack.getItem() != Items.enchanted_book)
            {
                itemStack.setTagInfo("ench", nbttaglist);
            }
            else
            {
                itemStack.setTagInfo("StoredEnchantments", nbttaglist);
            }
            itemStack.setTagInfo("restrictions", restrictions);
        }
        else if (itemStack.hasTagCompound())
        {
            if (itemStack.getItem() != Items.enchanted_book)
            {
                itemStack.getTagCompound().removeTag("ench");
            }
            else
            {
                itemStack.getTagCompound().removeTag("StoredEnchantments");
                itemStack.stackTagCompound = null;
                itemStack = new ItemStack(Items.book);
            }
        }
        return itemStack;
    }

    public static boolean containsKey(NBTTagList restrictions, int id, int y)
    {
        for (int k = 0; k < restrictions.tagCount(); k++)
        {
            NBTTagCompound tag = restrictions.getCompoundTagAt(k);
            if (tag.getShort("lvl") == y && tag.getShort("id") == id)
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isNewItemEnchantable(Item item)
    {
        if (item.equals(Items.enchanted_book))
        {
            return isItemEnchantable(new ItemStack(Items.book));
        }
        return isItemEnchantable(new ItemStack(item));
    }

    public static ItemStack removeEnchant(ItemStack itemStack, Enchantment enchantment)
    {
        Map enchantments = EnchantmentHelper.getEnchantments(itemStack);
        if(enchantments.containsKey(enchantment.effectId))
        {
            enchantments.remove(enchantment.effectId);
        }

        return setEnchantments(enchantments, itemStack, null, null);
    }
}
