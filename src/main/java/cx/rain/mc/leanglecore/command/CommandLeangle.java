package cx.rain.mc.leanglecore.command;

import cx.rain.mc.leanglecore.Leangle;
import cx.rain.mc.leanglecore.test.command.CommandTestLeangle;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

public class CommandLeangle extends CommandBase {
    @Override
    public String getCommandName() {
        return "leangle";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.leangle.usage";
    }

    @Override
    public List<String> getCommandAliases() {
        return new ArrayList<>();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            ChatStyle style = new ChatStyle().setColor(EnumChatFormatting.AQUA);
            sender.addChatMessage(new ChatComponentText("Leangle Core by Qyl.").setChatStyle(style));
            sender.addChatMessage(new ChatComponentText("Version: " + Leangle.VERSION).setChatStyle(style));
            sender.addChatMessage(new ChatComponentText("Source: https://github.com/AmemiyaSigure/LeangleCore").setChatStyle(style));
            return;
        }

        if (Leangle.getInstance().isDev()) {
            CommandTestLeangle.processTestCommand(sender, args);
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length == 0) {
            // Todo: Add tab completions.
        }

        if (Leangle.getInstance().isDev()) {
            // Do not show dev command.
            // Fixme: AS: Okay, will fix.
            //list = CommandTestLeangle.addTabCompletion(sender, args);
        }

        return list;
    }

    @Override
    public int getRequiredPermissionLevel() {
        if (Leangle.getInstance().isDev()) {
            return 2;
        } else {
            return 1;
        }
    }
}
