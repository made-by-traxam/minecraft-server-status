package am.trax.discord.minecraft.command;

import am.trax.discord.minecraft.message.IntroductoryMessageFormatter;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

/**
 * The status command responds with an introductory message.
 * @author traxam
 */
public class IntroductionCommand extends Command {
    {
        this.name = "introduction";
        this.help = "explains how you can use this bot.";
        this.aliases = new String[]{"intro", "help"};
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply(IntroductoryMessageFormatter.createEmbed(event.getJDA()));
    }
}
