package HxCKDMS.EnchantingPlus.Compat;

import HxCKDMS.EnchantingPlus.Utils.EnchantmentHelp;

public class ThaumcraftCompat {
    public static void PreInit() {
        EnchantmentHelp.putToolTips("enchantment.repair", "Consumes vis from the local aura to repair the item with this enchantment");
        EnchantmentHelp.putToolTips("enchantment.charging", "Allows your \"Tool\" and \"Weapon\" wands to recharge from the local aura.");
        EnchantmentHelp.putToolTips("enchantment.frugal", "Wand equivalent of unbreaking.");
        EnchantmentHelp.putToolTips("enchantment.potency", "Increases damage or range that wands have.");
        EnchantmentHelp.putToolTips("enchantment.haste", "Usable on boots and the Thaumostatic Harness only. Makes you move faster. Very effective on Boots of the Traveller, or the Thaumostatic Harness.");
    }
}
