package cx.rain.mc.leanglecore.test.command;

import cx.rain.mc.leanglecore.Leangle;
import cx.rain.mc.leanglecore.entity.EntityFallingTile;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

public class CommandTestLeangle {
    public static void processTestCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            throw new WrongUsageException("commands.leangle.test.usage");
        }

        if (args.length == 1) {
            if (args[0].equals("entity")) {
                throw new WrongUsageException("commands.leangle.test.entity.usage");
            }
        }

        if (args.length == 2) {
            if (args[0].equals("entity")) {
                if (args[1].equals("dropChest")) {
                    // Drop chest.
                    Leangle.getInstance().getLogger().info("Start drop chest test.");

                    if (sender instanceof EntityPlayerMP) {
                        EntityPlayerMP player = (EntityPlayerMP) sender;

                        TileEntityChest chest = new TileEntityChest();
                        chest.setInventorySlotContents(0, new ItemStack(Items.apple, 27));
                        NBTTagCompound tag = new NBTTagCompound();
                        chest.writeToNBT(tag);
                        EntityFallingTile entity = new EntityFallingTile(player.getEntityWorld(),
                                player.posX, 233, player.posZ, Blocks.chest, 0, tag);
                        player.worldObj.spawnEntityInWorld(entity);
                    } else {
                        throw new WrongUsageException("commands.leangle.test.entity.usage");
                    }
                } else if (args[1].equals("dropTile")) {
                    // Drop entity with tile.
                    Leangle.getInstance().getLogger().info("Start drop entity with tile test.");

                    if (sender instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayerMP) sender;
                        World world = player.getEntityWorld();

                        TileEntityFurnace furnace = new TileEntityFurnace();
                        furnace.setInventorySlotContents(0, new ItemStack(Items.apple, 27));
                        NBTTagCompound tag = new NBTTagCompound();
                        furnace.writeToNBT(tag);
                        EntityFallingTile entity = new EntityFallingTile(world, player.posX, 233, player.posZ,
                                Blocks.furnace, 0, tag);
                        world.spawnEntityInWorld(entity);
                    } else {
                        throw new WrongUsageException("commands.leangle.test.entity.usage");
                    }
                } else if (args[1].equals("dropBlock")) {
                    // Drop entity with tile.
                    Leangle.getInstance().getLogger().info("Start drop block test.");

                    if (sender instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayerMP) sender;
                        World world = player.getEntityWorld();

                        EntityFallingTile entity = new EntityFallingTile(world, player.posX, 233, player.posZ,
                                Blocks.coal_block, 0);
                        world.spawnEntityInWorld(entity);
                    } else {
                        throw new WrongUsageException("commands.leangle.test.entity.usage");
                    }
                }
            }
        }
    }
}
