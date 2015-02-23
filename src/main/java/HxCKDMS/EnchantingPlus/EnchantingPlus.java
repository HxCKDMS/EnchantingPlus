package HxCKDMS.EnchantingPlus;

import HxCKDMS.EnchantingPlus.Api.HxCRegistry;
import HxCKDMS.EnchantingPlus.Lib.References;
import HxCKDMS.EnchantingPlus.Proxy.CommonProxy;
import HxCKDMS.EnchantingPlus.Registry.ModRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = References.MOD_ID, name = References.MOD_NAME, version = References.VERSION)
public class EnchantingPlus {
    @Mod.Instance(References.MOD_ID)
    public static EnchantingPlus instance;
    
    @SidedProxy(clientSide = References.CLIENT_PROXY_LOCATION, serverSide = References.SERVER_PROXY_LOCATION)
    public static CommonProxy proxy;
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        ModRegistry.init(event.getAsmData().getAll(HxCRegistry.class.getCanonicalName()), event.getModState());
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        
    }
}
