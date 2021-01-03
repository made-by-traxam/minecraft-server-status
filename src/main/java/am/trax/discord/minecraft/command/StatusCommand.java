package am.trax.discord.minecraft.command;

import br.com.azalim.mcserverping.MCPing;
import br.com.azalim.mcserverping.MCPingResponse;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.stream.Collectors;

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
            event.reply("Please provide a server address.\n\nUsage: `+ping <server-address>`\n\nExample:\n```\n+ping hypixel.net```");
        } else {
            event.reply(new EmbedBuilder().setDescription("Pinging server, please wait...").build(), m -> {
                try {
                    MCPingResponse ping = MCPing.getPing(event.getArgs());
                    m.editMessage(createStatusEmbed(event.getArgs(), ping)).queue();
                } catch (IOException e) {
                    e.printStackTrace();
                    m.editMessage(new EmbedBuilder().setDescription("Error!").build()).queue();
                }
            });
        }
    }

    private MessageEmbed createStatusEmbed(String address, MCPingResponse ping) {
        return new EmbedBuilder()
                .setTitle(address)
                .setDescription(ping.getDescription().getStrippedText())
                .addField("Players", String.format("%d / %d", ping.getPlayers().getOnline(), ping.getPlayers().getMax()), true)
                .addField("Online players", ping.getPlayers().getSample().stream().map(MCPingResponse.Player::getName).collect(Collectors.joining(", ")), true)
                .addField("Ping", String.format("%dms", ping.getPing()), true)
                .setTimestamp(Instant.now())
                .setFooter("Last updated")
                .setColor(new Color(46, 204, 113))
                .setThumbnail(String.format("https://eu.mc-api.net/v3/server/favicon/%s", address))
                .build();
    }
}
