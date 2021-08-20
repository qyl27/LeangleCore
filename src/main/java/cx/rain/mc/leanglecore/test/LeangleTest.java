package cx.rain.mc.leanglecore.test;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cx.rain.mc.leanglecore.Leangle;
import cx.rain.mc.leanglecore.proxy.LeangleCommon;

public class LeangleTest extends LeangleCommon {
    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        Leangle.getInstance().getLogger().info("Test pre-initializing.");
    }

    @Override
    public void onInit(FMLInitializationEvent event) {
        Leangle.getInstance().getLogger().info("Test initializing.");
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        Leangle.getInstance().getLogger().info("Test post-initializing.");
    }
}
