package am.trax.discord.minecraft.command;

import am.trax.discord.minecraft.message.StatusMessageFormatter;
import am.trax.discord.minecraft.ping.ServerPinger;
import am.trax.discord.minecraft.ping.ServerStatus;
import am.trax.discord.minecraft.scheduler.RefreshScheduler;
import am.trax.discord.minecraft.util.ExampleServerUtil;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

/**
 * The auto-status command responds with a server status and will auto-update that status message.
 * @author traxam
 */
@Slf4j
public class AutoStatusCommand extends Command {
    private final RefreshScheduler refreshScheduler;

    /**
     * Initializes this command handler.
     * @param refreshScheduler the refresh scheduler to be used.
     */
    public AutoStatusCommand(RefreshScheduler refreshScheduler)
    {
        this.refreshScheduler = refreshScheduler;
        this.name = "auto-status";
        this.help = "shows the status of a minecraft server and keeps refreshing it.";
        this.aliases = new String[]{"auto-ping", "autostatus", "autoping"};
        this.guildOnly = false;
        this.arguments = "<server-address>";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().isBlank()) {
            event.reply("Please provide a server address.\n\nUsage: `mc-auto-status <server-address>`\n\n" +
                    "Example:\n```\nmc-auto-status " + ExampleServerUtil.getRandomExampleServerAddress() + "```");
        } else {
            event.reply(new EmbedBuilder().setDescription("Pinging server, please wait...").build(), m -> {
                ServerStatus status = ServerPinger.ping(event.getArgs());
                MessageEmbed embed = new StatusMessageFormatter(status).createEmbed();
                m.editMessage(embed)
                        .queue(refreshScheduler::startPeriodicRefreshingFor);
            });
        }
    }
}
