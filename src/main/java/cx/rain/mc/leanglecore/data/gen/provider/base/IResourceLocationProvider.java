package cx.rain.mc.leanglecore.data.gen.provider.base;

import net.minecraft.resources.ResourceLocation;

public interface IResourceLocationProvider {
    public String getModId();

    public default ResourceLocation modLoc(String path) {
        return new ResourceLocation(getModId(), path);
    }

    public default ResourceLocation mcLoc(String path) {
        return new ResourceLocation("minecraft", path);
    }
}
