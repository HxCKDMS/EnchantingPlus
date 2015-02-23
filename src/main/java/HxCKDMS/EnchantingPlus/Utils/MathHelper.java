package HxCKDMS.EnchantingPlus.Utils;

public class MathHelper {
    public static double round(double value, double inc){
        return java.lang.Math.round(value / inc) * inc;
    }
}
