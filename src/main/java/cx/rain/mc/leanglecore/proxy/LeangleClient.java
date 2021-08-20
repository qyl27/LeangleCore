package cx.rain.mc.leanglecore.proxy;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cx.rain.mc.leanglecore.entity.renderer.LeangleEntitiesRenderer;

public class LeangleClient extends LeangleCommon {
    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        super.onPreInit(event);

        LeangleEntitiesRenderer.register();
    }
}
