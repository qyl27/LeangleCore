package cx.rain.mc.leanglecore.entity.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cx.rain.mc.leanglecore.entity.EntityFallingTile;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderFallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class EntityFallingTileRender extends RenderFallingBlock {
    protected RenderBlocks renderBlocks = new RenderBlocks();

    public void doRender(EntityFallingTile fallingTile, double xPos, double yPos, double zPos) {
        World world = fallingTile.worldObj;
        Block block = fallingTile.func_145805_f();

        int x = MathHelper.floor_double(xPos);
        int y = MathHelper.floor_double(yPos);
        int z = MathHelper.floor_double(zPos);

        GL11.glPushMatrix();
        GL11.glTranslatef((float)xPos, (float)yPos, (float)zPos);
        bindEntityTexture(fallingTile);
        GL11.glDisable(GL11.GL_LIGHTING);

        if (block != null)
        {
            renderBlocks.setRenderBoundsFromBlock(block);
            renderBlocks.renderBlockSandFalling(block, world, x, y, z, fallingTile.field_145814_a);
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double xPos, double yPos, double zPos, float p_76986_8_, float p_76986_9_) {
        doRender((EntityFallingTile) entity, xPos, yPos, zPos);
    }
}
