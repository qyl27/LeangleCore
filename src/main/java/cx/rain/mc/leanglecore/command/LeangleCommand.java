package cx.rain.mc.leanglecore.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import cx.rain.mc.leanglecore.Leangle;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class LeangleCommand {
    public LeangleCommand(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal("leangle").executes(context -> {
            if (context.getSource() instanceof ICommandSource) {
                ICommandSource source = (ICommandSource) context.getSource();
                source.sendMessage(new StringTextComponent("Leangle Core by Qyl.").withStyle(TextFormatting.AQUA), Util.NIL_UUID);
                source.sendMessage(new StringTextComponent("Version: " + Leangle.VERSION).withStyle(TextFormatting.AQUA), Util.NIL_UUID);
            }
            return 1;
        }));
    }
}
