package cx.rain.mc.leanglecore.entity.renderer;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cx.rain.mc.leanglecore.entity.EntityFallingTile;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

public class LeangleEntitiesRenderer {
    @SideOnly(Side.CLIENT)
    public static void register() {
        register(EntityFallingTile.class, new EntityFallingTileRender());
    }

    @SideOnly(Side.CLIENT)
    private static <T extends Entity> void register(Class<T> entityClass, Render render)
    {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, render);
    }
}
