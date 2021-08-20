package cx.rain.mc.leanglecore.test.data.gen;

import cx.rain.mc.leanglecore.Leangle;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Leangle.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenTest {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        if (Leangle.isDev()) {
            var gen = event.getGenerator();
            var existingHelper = event.getExistingFileHelper();

            if (event.includeClient()) {

            }

            if (event.includeServer()) {

            }

            if (event.includeDev()) {
                gen.addProvider(new LanguageZHCN(gen));
            }
        }
    }
}
