package cx.rain.mc.leanglecore.command;

import com.mojang.brigadier.CommandDispatcher;
import cx.rain.mc.leanglecore.Leangle;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

public class LeangleCommand {
    public LeangleCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("leangle").executes(context -> {
            context.getSource().sendSuccess(new TextComponent("Leangle Core by Qyl.").withStyle(ChatFormatting.AQUA), false);
            context.getSource().sendSuccess(new TextComponent("Version: " + Leangle.VERSION).withStyle(ChatFormatting.AQUA), false);
            return 1;
        }));
    }
}
