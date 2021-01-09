package am.trax.discord.minecraft.command;

import am.trax.discord.minecraft.message.StatusMessageFormatter;
import am.trax.discord.minecraft.ping.ServerPinger;
import am.trax.discord.minecraft.ping.ServerStatus;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

/**
 * The status command responds with a server status.
 * @author traxam
 */
@Slf4j
public class StatusCommand extends Command {
    {
        this.name = "status";
        this.help = "shows the status of a minecraft server.";
        this.aliases = new String[]{"ping"};
        this.guildOnly = false;
        this.arguments = "<server-address>";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().isBlank()) {
            event.reply("Please provide a server address.\n\nUsage: `mc-status <server-address>`\n\nExample:\n```\nmc-status hypixel.net```");
        } else {
            event.reply(new EmbedBuilder().setDescription("Pinging server, please wait...").build(), m -> {
                ServerStatus status = ServerPinger.ping(event.getArgs());
                MessageEmbed embed = new StatusMessageFormatter(status).createEmbed();
                m.editMessage(embed).queue();
            });
        }
    }
}
