package cx.rain.mc.leanglecore.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cx.rain.mc.leanglecore.Leangle;
import net.minecraft.client.renderer.entity.RenderFallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;

public class LeangleEntities {
    private static int ID = 0;

    protected static int nextId() {
        return ID++;
    }

    public static void registerEntities() {
        beforeRegisterEntity();
        // AS: Do entity registration below.
        registerEntity(EntityFallingTile.class, "FallingTile", 64, 3, true);
    }

    protected static void registerEntity(Class<? extends Entity> clazz, String name,
                                         int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
        EntityRegistry.registerModEntity(clazz, name, nextId(), Leangle.getInstance(),
                trackingRange, updateFrequency, sendsVelocityUpdates);
    }

    protected static void beforeRegisterEntity() {
//        ReflectionHelper.findField(EntityFallingBlock.class, "field_145811_e").setAccessible(true);
//        ReflectionHelper.findField(EntityFallingBlock.class, "field_145808_f").setAccessible(true);
//        ReflectionHelper.findField(EntityFallingBlock.class, "field_145809_g").setAccessible(true);
//        ReflectionHelper.findField(EntityFallingBlock.class, "field_145815_h").setAccessible(true);
//        ReflectionHelper.findField(EntityFallingBlock.class, "field_145816_i").setAccessible(true);
//        ReflectionHelper.findField(RenderFallingBlock.class, "field_147920_a").setAccessible(true);
    }
}
