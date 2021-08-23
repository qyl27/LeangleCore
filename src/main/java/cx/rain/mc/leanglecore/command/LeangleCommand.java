package cx.rain.mc.leanglecore.command;

import com.mojang.brigadier.CommandDispatcher;
import cx.rain.mc.leanglecore.Leangle;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class LeangleCommand {
    public LeangleCommand(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("leangle").executes(context -> {
            context.getSource().sendSuccess(new StringTextComponent("Leangle Core by Qyl.").withStyle(TextFormatting.AQUA), false);
            context.getSource().sendSuccess(new StringTextComponent("Version: " + Leangle.VERSION).withStyle(TextFormatting.AQUA), false);
            return 1;
        }));
    }
}
