package am.trax.discord.minecraft.command;

import am.trax.discord.minecraft.message.InvitationMessageFormatter;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.MessageEmbed;

/**
 * Formats a help message for adding this bot to a Discord server into a {@link MessageEmbed}.
 * @author traxam
 */
public class InviteCommand extends Command {
    {
        this.name = "invite";
        this.help = "helps you with adding this bot to your own server.";
        this.aliases = new String[]{"iwantthistoo", "invitation", "add"};
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply(InvitationMessageFormatter.createEmbed(event.getJDA()));
    }
}
