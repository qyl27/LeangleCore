package cx.rain.mc.leanglecore.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cx.rain.mc.leanglecore.Leangle;
import cx.rain.mc.leanglecore.entity.LeangleEntities;
import cx.rain.mc.leanglecore.command.CommandLeangle;

public class LeangleCommon {
    public void onPreInit(FMLPreInitializationEvent event) {
        Leangle.getInstance().getLogger().info("Now let's do something.");
        LeangleEntities.registerEntities();
    }

    public void onInit(FMLInitializationEvent event) {
        Leangle.getInstance().getLogger().info("Hailo No.1.");
    }

    public void onPostInit(FMLPostInitializationEvent event) {
        Leangle.getInstance().getLogger().info("Hello Minecraft!");
    }

    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandLeangle());
    }
}
