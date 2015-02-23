package HxCKDMS.EnchantingPlus.Utils;

import HxCKDMS.EnchantingPlus.Handler.ConfigurationHandler;
import HxCKDMS.EnchantingPlus.Lib.ConfigurationSettings;
import HxCKDMS.EnchantingPlus.Lib.Strings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantmentHelp {

    private static Map<String, String> toolTips = new HashMap<String, String>();
    private static List<String> enchantmentBlackList = new ArrayList<String>();
    private static List<Integer> itemBlackList = new ArrayList<Integer>();

    public static String getToolTip(Enchantment enchant)
    {
        return toolTips.containsKey(enchant.getName()) ? toolTips.get(enchant.getName()) : "";
    }

    public static void init()
    {
        putToolTips(Enchantment.unbreaking, Strings.enchantmentUnbreaking);

        // Armour
        putToolTips(Enchantment.thorns, Strings.enchantmentThorns);
        putToolTips(Enchantment.aquaAffinity, Strings.enchantmentAquaAffinity);
        putToolTips(Enchantment.respiration, Strings.enchantmentRespiration);
        putToolTips(Enchantment.projectileProtection, Strings.enchantmentProtectionProjectile);
        putToolTips(Enchantment.blastProtection, Strings.enchantmentProtectionBlast);
        putToolTips(Enchantment.featherFalling, Strings.enchantmentFeatherFalling);
        putToolTips(Enchantment.fireProtection, Strings.enchantmentProtectionFire);
        putToolTips(Enchantment.protection, Strings.enchantmentProtection);

        // Weapons
        putToolTips(Enchantment.sharpness, Strings.enchantmentSharpness);
        putToolTips(Enchantment.smite, Strings.enchantmentSmite);
        putToolTips(Enchantment.baneOfArthropods, Strings.enchantmentBaneOfAthropods);
        putToolTips(Enchantment.knockback, Strings.enchantmentKnockBack);
        putToolTips(Enchantment.fireAspect, Strings.enchantmentFireAspect);
        putToolTips(Enchantment.looting, Strings.enchantmentLooting);

        // Tools
        putToolTips(Enchantment.efficiency, Strings.enchantmentEfficiency);
        putToolTips(Enchantment.silkTouch, Strings.enchantmentSilkTouch);
        putToolTips(Enchantment.fortune, Strings.enchantmentFortune);

        // Bows
        putToolTips(Enchantment.power, Strings.enchantmentPower);
        putToolTips(Enchantment.punch, Strings.enchantmentPunch);
        putToolTips(Enchantment.flame, Strings.enchantmentFlame);
        putToolTips(Enchantment.infinity, Strings.enchantmentInfinity);

        saveToolTips();
    }

    public static void saveToolTips()
    {
        for (String enchantment : toolTips.keySet())
        {
            ConfigurationHandler.set(enchantment + "-ToolTip", toolTips.get(enchantment));
        }
    }

    public static boolean isBlackListed(Enchantment enchantment)
    {
        if(enchantment == null)
        {
            return true;
        }

        boolean flag = enchantmentBlackList.contains(enchantment.getName());

        if (ConfigurationSettings.enchantments.containsKey(enchantment.getName()))
        {
            flag |= !ConfigurationSettings.enchantments.get(enchantment.getName());
        }

        return flag;
    }

    public static boolean isBlackListed(Item item)
    {
        return itemBlackList.contains(Item.getIdFromItem(item));
    }


    public static boolean putBlackListItem(Integer itemId)
    {
        if (!itemBlackList.contains(itemId))
        {
            itemBlackList.add(itemId);
            return true;
        }
        return false;
    }

    public static boolean putToolTips(Enchantment enchantment, String info)
    {
        return putToolTips(enchantment.getName(), info);
    }

    public static boolean putToolTips(String enchantment, String info)
    {
        if (!toolTips.containsKey(enchantment))
        {
            toolTips.put(enchantment, info);
            return true;
        }
        else
        {
            if (toolTips.get(enchantment).isEmpty())
            {
                toolTips.put(enchantment, info);
                return true;
            }
        }

        return false;
    }

    public static Enchantment getEnchantmentById(Integer enchantmentID)
    {
        return Enchantment.enchantmentsList[enchantmentID];
    }
}
