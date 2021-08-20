package cx.rain.mc.leanglecore.entity;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.io.IOException;
import java.util.Iterator;

public class EntityFallingTile extends EntityFallingBlock implements IEntityAdditionalSpawnData {
    protected boolean dropSelf = true;
    private boolean canSetAsBlock = false;

    public EntityFallingTile(World world) {
        super(world );
    }

    public EntityFallingTile(World world, double xPos, double yPos, double zPos, Block block) {
        super(world, xPos, yPos, zPos, block);
    }

    public EntityFallingTile(World world, double xPos, double yPos, double zPos, Block block, int meta) {
        super(world, xPos, yPos, zPos, block, meta);
    }

    public EntityFallingTile(World world, double xPos, double yPos, double zPos, Block block, int meta, NBTTagCompound nbt) {
        super(world, xPos, yPos, zPos, block, meta);
        field_145810_d = nbt;
    }

    public EntityFallingTile(World world, double xPos, double yPos, double zPos, Block block, int meta, NBTTagCompound nbt, boolean dropSelfIn) {
        super(world, xPos, yPos, zPos, block, meta);
        field_145810_d = nbt;
        dropSelf = dropSelfIn;
    }

    @Override
    public void onUpdate() {
        if (worldObj.isRemote) {
            if (func_145805_f() == null) {
                return;
            }
        }

        if (func_145805_f().getMaterial() == Material.air) {
            setDead();
        } else {
            prevPosX = posX;
            prevPosY = posY;
            prevPosZ = posZ;
            ++field_145812_b;
            motionY -= 0.03999999910593033D;
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.9800000190734863D;
            motionY *= 0.9800000190734863D;
            motionZ *= 0.9800000190734863D;

            if (!worldObj.isRemote) {
                int x = MathHelper.floor_double(posX);
                int y = MathHelper.floor_double(posY);
                int z = MathHelper.floor_double(posZ);

                // AS: For fabricated entity.
//                if (field_145812_b == 1) {
//                    if (worldObj.getBlock(i, j, k) != block) {
//                        setDead();
//                        return;
//                    }
//
//                    worldObj.setBlockToAir(i, j, k);
//                }

                if (onGround) {
                    motionX *= 0.699999988079071D;
                    motionZ *= 0.699999988079071D;
                    motionY *= -0.5D;

                    if (worldObj.getBlock(x, y, z) != Blocks.piston_extension) {
                        setDead();

                        if (canSetAsBlock
                                || !worldObj.canPlaceEntityOnSide(func_145805_f(), x, y, z,
                                true, 1, null, null) ||
                                BlockFalling.func_149831_e(worldObj, x, y - 1, z) ||
                                !worldObj.setBlock(x, y, z, func_145805_f(), field_145814_a, 3)) {
                            if (field_145813_c && !canSetAsBlock) {
                                dropItemsWithEntity();
                            }
                        } else {
                            if (func_145805_f() instanceof BlockFalling) {
                                ((BlockFalling) func_145805_f()).func_149828_a(worldObj, x, y, z, field_145814_a);
                            }

                            if (field_145810_d != null && func_145805_f() instanceof ITileEntityProvider) {
                                TileEntity tileentity = worldObj.getTileEntity(x, y, z);

                                if (tileentity != null) {
                                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                                    tileentity.writeToNBT(nbttagcompound);
                                    Iterator iterator = field_145810_d.func_150296_c().iterator();

                                    while (iterator.hasNext()) {
                                        String s = (String) iterator.next();
                                        NBTBase nbtbase = field_145810_d.getTag(s);

                                        if (!s.equals("x") && !s.equals("y") && !s.equals("z")) {
                                            nbttagcompound.setTag(s, nbtbase.copy());
                                        }
                                    }

                                    tileentity.readFromNBT(nbttagcompound);
                                    tileentity.markDirty();
                                }
                            }
                        }
                    }
                } else if (field_145812_b > 100 && !worldObj.isRemote && (y < 1 || y > 256) || field_145812_b > 600) {
                    if (field_145813_c) {
                        dropItemsWithEntity();
                    }

                    setDead();
                }
            }
        }
    }

    @Override
    protected void fall(float f) {
        super.fall(f);

        int i = MathHelper.ceiling_float_int(f - 1.0F);

        if (i > 0)
        {
            boolean flag = func_145805_f() == Blocks.anvil;

            if (flag && (double)this.rand.nextFloat() < 0.05000000074505806D + (double)i * 0.05D)
            {
                int j = this.field_145814_a >> 2;
                ++j;

                if (j > 2)
                {
                    canSetAsBlock = true;
                }
            }
        }
    }

    private void dropItemsWithEntity() {
        if (dropSelf) {
            entityDropItem(new ItemStack(func_145805_f(), 1,
                    func_145805_f().damageDropped(field_145814_a)), 0.0F);
        }

        if (func_145805_f() instanceof ITileEntityProvider || func_145805_f().hasTileEntity(field_145814_a)) {
            TileEntity tile = func_145805_f().createTileEntity(worldObj, field_145814_a);
            tile.readFromNBT(field_145810_d);

            if (tile instanceof IInventory) {
                IInventory inv = (IInventory) tile;
                for (int i = 0; i < inv.getSizeInventory(); i++) {
                    if (inv.getStackInSlot(i) != null) {
                        entityDropItem(inv.getStackInSlot(i), 1);
                    }
                }
            }
        }
    }

    public void setNBT(NBTTagCompound nbt) {
        field_145810_d = nbt;
    }

    public NBTTagCompound getNBT() {
        return field_145810_d;
    }

    public boolean doesDropSelf() {
        return dropSelf;
    }

    public void setDropSelf(boolean value) {
        dropSelf = value;
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("DropSelf", dropSelf);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        if (nbt.hasKey("DropSelf")) {
            dropSelf = nbt.getBoolean("DropSelf");
        }
    }

    @Override
    public void writeSpawnData(ByteBuf byteBuf) {
        PacketBuffer buf = new PacketBuffer(byteBuf);
        NBTTagCompound nbt = new NBTTagCompound();
        writeEntityToNBT(nbt);
        try {
            buf.writeNBTTagCompoundToBuffer(nbt);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void readSpawnData(ByteBuf byteBuf) {
        PacketBuffer buf = new PacketBuffer(byteBuf);
        NBTTagCompound nbt = new NBTTagCompound();
        try {
            buf.readNBTTagCompoundFromBuffer();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        readEntityFromNBT(nbt);
    }
}
