package cx.rain.mc.leanglecore;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cx.rain.mc.leanglecore.proxy.LeangleCommon;
import cx.rain.mc.leanglecore.test.LeangleTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Leangle.MODID, name = Leangle.NAME, version = Leangle.VERSION,
        acceptedMinecraftVersions = Leangle.MC_VERSION)
public class Leangle {
    public static final String MODID = "leanglecore";
    public static final String NAME = "Leangle Core";
    public static final String VERSION = "1.7.10-1.0.0";
    public static final String MC_VERSION = "1.7.10";

    private Logger log = LogManager.getLogger("LeangleCore");

    private boolean isDev;

    @Mod.Instance(MODID)
    private static Leangle INSTANCE;

    public Leangle() {
        isDev = Boolean.parseBoolean(System.getProperty("leangle.development", "false"));
    }

    @SidedProxy(clientSide = "cx.rain.mc.leanglecore.proxy.LeangleClient",
            serverSide = "cx.rain.mc.leanglecore.proxy.LeangleCommon")
    public static LeangleCommon proxy;

    @SidedProxy(clientSide = "cx.rain.mc.leanglecore.test.LeangleTestClient",
            serverSide = "cx.rain.mc.leanglecore.proxy.LeangleTest")
    public static LeangleTest testProxy;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        proxy.onPreInit(event);

        if (isDev()) {
            testProxy.onPreInit(event);
        }
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        proxy.onInit(event);

        if (isDev()) {
            testProxy.onInit(event);
        }
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        proxy.onPostInit(event);

        if (isDev()) {
            testProxy.onPostInit(event);
        }
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        proxy.onServerStarting(event);

        if (isDev()) {
            testProxy.onServerStarting(event);
        }
    }

    public static Leangle getInstance() {
        return INSTANCE;
    }

    public Logger getLogger() {
        return log;
    }

    public boolean isDev() {
        return isDev;
    }
}
