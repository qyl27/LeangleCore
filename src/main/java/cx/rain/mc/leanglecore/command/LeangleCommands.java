package cx.rain.mc.leanglecore.command;

import cx.rain.mc.leanglecore.Leangle;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Leangle.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LeangleCommands {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        new LeangleCommand(event.getDispatcher());
    }
}
