package cx.rain.mc.leanglecore.test.data.gen;

import cx.rain.mc.leanglecore.Leangle;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Leangle.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenTest {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        if (Leangle.isDev()) {
            DataGenerator gen = event.getGenerator();
            ExistingFileHelper existingHelper = event.getExistingFileHelper();

            if (event.includeClient()) {

            }

            if (event.includeServer()) {
                gen.addProvider(new AdvancementProvider(gen, existingHelper));
            }

            if (event.includeDev()) {
                gen.addProvider(new LanguageZHCN(gen));
            }
        }
    }
}
