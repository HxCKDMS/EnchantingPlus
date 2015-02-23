package HxCKDMS.EnchantingPlus.Proxy;

import cpw.mods.fml.relauncher.Side;

public abstract class CommonProxy {
    public Side getSide() {
        return this instanceof ClientProxy ? Side.CLIENT : Side.SERVER;
    }
}
