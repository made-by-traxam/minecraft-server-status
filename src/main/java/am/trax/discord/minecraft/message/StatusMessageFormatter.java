package am.trax.discord.minecraft.message;

import am.trax.discord.minecraft.ping.ServerStatus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.List;

/**
 * Formats a {@link ServerStatus} into a Discord {@link MessageEmbed}.
 * @author traxam
 */
public class StatusMessageFormatter {
    private static final Color ONLINE_COLOR = new Color(46, 204, 113);
    private static final Color OFFLINE_COLOR = new Color(231, 76, 60);
    private static final String INFINITY_SIGN = "\u221E";

    private final ServerStatus status;

    /**
     * Creates a new status message formatter for a given server status.
     * @param status the ping status of the server.
     */
    public StatusMessageFormatter(ServerStatus status) {
        this.status = status;
    }

    /**
     * Creates a message embed that represents the server status.
     * @return the created embed.
     */
    public MessageEmbed createEmbed() {
        return new EmbedBuilder()
                .setTitle(status.getAddress())
                .setDescription(formatDescription())
                .addField("Players", formatPlayersAmount(), true)
                .addField("Online players", formatOnlinePlayers(), true)
                .addField("Ping", formatPing(), true)
                .setTimestamp(status.getTimestamp())
                .setFooter("Last updated")
                .setColor(getEmbedColor())
                .setThumbnail(getIconUrl())
                .build();
    }

    private String formatDescription() {
        if (status.isOnline()) {
            return String.format("```%s```", status.getPlainTextMotd());
        } else {
            return "```Can't connect to server.```";
        }
    }

    private String formatPlayersAmount() {
        if (status.isOnline()) {
            return String.format("%d / %d", status.getOnlinePlayerAmount(), status.getMaxPlayerAmount());
        } else {
            return "- / -";
        }
    }

    private String formatOnlinePlayers() {
        if (status.isOnline()) {
            List<String> sample = status.getOnlinePlayerNamesSample();
            if (sample.isEmpty()) {
                return "-";
            } else {
                return String.join("\n", sample);
            }
        } else {
            return "-";
        }
    }

    private String formatPing() {
        if (status.isOnline()) {
            return String.format("%dms", status.getPing());
        } else {
            return INFINITY_SIGN;
        }
    }

    private Color getEmbedColor() {
        return status.isOnline() ? ONLINE_COLOR : OFFLINE_COLOR;
    }

    private String getIconUrl() {
        if (status.isOnline()) {
            return String.format("https://eu.mc-api.net/v3/server/favicon/%s", status.getAddress());
        } else {
            // default server icon from the Minecraft Wiki: https://minecraft.gamepedia.com/File:Unknown_server.png
            // image is copyrighted by Mojang Studios
            return "https://static.wikia.nocookie.net/minecraft_gamepedia/images/f/f2/Unknown_server.png/revision/latest?cb=20200929054425&format=original";
        }
    }
}
