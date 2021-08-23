package cx.rain.mc.leanglecore.data.gen.provider;

import cx.rain.mc.leanglecore.data.gen.provider.base.IResourceLocationProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public abstract class SoundDataProvider extends SoundDefinitionsProvider implements IResourceLocationProvider {
    protected String id;

    public SoundDataProvider(DataGenerator generator, String modId, ExistingFileHelper helper) {
        super(generator, modId, helper);
        id = modId;
    }

    @Override
    public String getModId() {
        return id;
    }

    @Override
    public String getName() {
        return "Sound Data Provider";
    }

    // Helper methods below.
    // Todo: AS: May be someone need help.
}
