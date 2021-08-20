package cx.rain.mc.leanglecore;

import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Leangle.MODID)
public class Leangle {
    public static final String MODID = "leanglecore";
    public static final String NAME = "Leangle Core";
    public static final String VERSION = "1.16.4-1.0.0";
    public static final String MC_VERSION = "1.16.4";

    private static Leangle INSTANCE;

    private final Logger logger = LogManager.getLogger(Leangle.NAME);

    public Leangle() {
        if (INSTANCE != null) {
            throw new RuntimeException("Why did you do that? Naming blame!");
        }

        INSTANCE = this;

        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::setup);
        bus.addListener(this::clientSetup);
        bus.addListener(this::serverSetup);
    }

    private void setup(FMLCommonSetupEvent event) {
    }

    private void clientSetup(FMLClientSetupEvent event) {
    }

    private void serverSetup(FMLDedicatedServerSetupEvent event) {
    }

    public static Leangle getInstance() {
        return INSTANCE;
    }

    public Logger getLogger() {
        return logger;
    }

    public static boolean isDev() {
        return Boolean.parseBoolean(System.getProperty("leangle.development", "false"));
    }
}
