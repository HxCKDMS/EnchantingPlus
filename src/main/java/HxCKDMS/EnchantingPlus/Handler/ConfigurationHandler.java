package HxCKDMS.EnchantingPlus.Handler;

import HxCKDMS.EnchantingPlus.EnchantingPlus;
import HxCKDMS.EnchantingPlus.Lib.ConfigurationSettings;
import HxCKDMS.EnchantingPlus.Lib.References;
import HxCKDMS.EnchantingPlus.Utils.EnchantmentHelp;
import HxCKDMS.HxCCore.Utils.LogHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Set;


public class ConfigurationHandler
{
    public static final String CATEGORY_CLIENT = "client";
    public static final String CATEGORY_SERVER = "server";
    public static final String CATEGORY_BOTH = "both";
    public static final String CATEGORY_ENCHANT = "enchantments";
    public static final String CATEGORY_IDS = "ids";

    public static Configuration configuration;

    public static void init(File suggestedConfigurationFile)
    {
        configuration = new Configuration(suggestedConfigurationFile, false);

        LogHelper.info("Initializing Configurations.", References.MOD_NAME);

        try
        {
            configuration.load();

            configuration.addCustomCategoryComment(CATEGORY_CLIENT, "Settings controlled client.\nCan vary from server to server.");
            configuration.addCustomCategoryComment(CATEGORY_SERVER, "Settings controlled by server.");
            configuration.addCustomCategoryComment(CATEGORY_BOTH,
                    "Settings controlled by both.\nClient can be different from server.\nIf client differs from server, server can disable client's setting if false.");

            configuration.addCustomCategoryComment(CATEGORY_ENCHANT, "Enchantments can be disabled to be enchantable in the table.\nControlled on the server side");

            ConfigurationSettings.allowDisenUnowned = configuration.get(CATEGORY_SERVER, "Disenchant Unowned", false,
                    "Allow disenchanting of enchantment levels the player doesn't own.").getBoolean(false);

            ConfigurationSettings.useMod = configuration.get(CATEGORY_BOTH, "useMod", ConfigurationSettings.useModDefault,
                    "Set to true to use custom Enchantment Table in place of Vanilla").getBoolean(ConfigurationSettings.useModDefault);
            ConfigurationSettings.needsBookShelves = configuration.get(CATEGORY_SERVER, "needsBookShelves", ConfigurationSettings.bookShelvesDefault,
                    "Set to true to require book shelves to enchant.").getBoolean(ConfigurationSettings.bookShelvesDefault);
            ConfigurationSettings.hasLight = configuration.get(CATEGORY_SERVER, "hasLight", ConfigurationSettings.lightDefault,
                    "Set to true to have the enchanting table emmit light.").getBoolean(ConfigurationSettings.lightDefault);
            ConfigurationSettings.hasParticles = configuration.get(CATEGORY_CLIENT, "hasParticles", ConfigurationSettings.particlesDefault,
                    "Set to true to have the enchanting table emmit particles.").getBoolean(ConfigurationSettings.particlesDefault);
            ConfigurationSettings.AllowDisenchanting = configuration.get(CATEGORY_SERVER, "AllowDisenchanting", ConfigurationSettings.disenchantingDefault,
                    "Set to true to allow disenchanting.").getBoolean(ConfigurationSettings.disenchantingDefault);
            ConfigurationSettings.AllowRepair = configuration.get(CATEGORY_SERVER, "AllowRepair", ConfigurationSettings.repairDefault,
                    "Set to true to allow repairing of items via enchantment table.").getBoolean(ConfigurationSettings.repairDefault);

            ConfigurationSettings.AllowEnchantDamaged = configuration.get(CATEGORY_SERVER, "AllowEnchantDamaged", ConfigurationSettings.AllowEnchantDamagedDefault,
                    "Determinds if a player can enchant a damaged item").getBoolean(ConfigurationSettings.AllowEnchantDamagedDefault);

            ConfigurationSettings.CostFactor = configuration.get(CATEGORY_SERVER, "CostFactor", 5, "Determinds the cost factor of enchanting / disenchanting / repair").getInt();

            clampSetting(CATEGORY_SERVER, "CostFactor", 1);

            ConfigurationSettings.RepairFactor = configuration.get(CATEGORY_SERVER, "RepairFactor", 5, "factor which repair cost is divided by (Default = 8). Higher is Cheaper")
                    .getInt();

            clampSetting(CATEGORY_SERVER, "RepairFactor", 1);

            ConfigurationSettings.minimumBook = configuration.get(CATEGORY_SERVER, "minimumBook", 5, "Minimum level of enchanting cost before requiring bookcases").getInt();

            clampSetting(CATEGORY_SERVER, "minimumBook", 0);


        }
        catch (final Exception e)
        {
            LogHelper.info("Error Loading configuration", References.MOD_NAME);
            LogHelper.info("Cause by " + e.getLocalizedMessage(), References.MOD_NAME);
        }
        finally
        {
            if (configuration.hasChanged())
            {
                configuration.save();
            }
        }
    }

    private static void clampSetting(String category, String setting, int minimum)
    {
        try
        {
            Field field = ConfigurationSettings.class.getDeclaredField(setting);

            int value = field.getInt(null);

            if (value < minimum)
            {
                configuration.getCategory(category).get(setting).set(minimum);
                field.setInt(null, minimum);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void set(String propertyName, String newValue)
    {
        configuration.load();

        final Set<String> categoryNames = configuration.getCategoryNames();

        for (final String category : categoryNames)
        {
            if (configuration.getCategory(category).containsKey(propertyName))
            {
                configuration.getCategory(category).get(propertyName).set(newValue);
            }
        }

        try
        {
            final Field field = ReflectionHelper.findField(ConfigurationSettings.class, propertyName);
            try
            {
                if (field.getType() == boolean.class)
                {
                    field.setBoolean(EnchantingPlus.instance, Boolean.parseBoolean(newValue));
                }
            }
            catch (final IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        catch (final Exception e)
        {

        }

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }

    public static void loadEnchantments()
    {
        try
        {
            configuration.load();
            for (Enchantment enchant : Enchantment.enchantmentsList)
            {
                if (enchant != null)
                {
                    ConfigurationSettings.enchantments.put(enchant.getName(), configuration.get("enchantments", enchant.getName(), true).getBoolean(true));
                    EnchantmentHelp.putToolTips(enchant, configuration.get("enchantments", enchant.getName() + "-ToolTip", "").getString());
                }
            }

        }
        catch (Exception e)
        {
            LogHelper.info("Error Loading configuration", References.MOD_NAME);
            e.printStackTrace();
        }
        finally
        {
            if (configuration.hasChanged())
            {
                configuration.save();
            }
        }

    }
}
