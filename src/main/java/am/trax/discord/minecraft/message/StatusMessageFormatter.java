package am.trax.discord.minecraft.message;

import am.trax.discord.minecraft.ping.ServerStatus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.List;

/**
 * Formats a {@link ServerStatus} into a Discord {@link MessageEmbed}.
 * @author traxam
 */
public class StatusMessageFormatter {
    private static final String FOOTER = "Status last updated";
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
                .setFooter(FOOTER)
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

    /**
     * Checks whether a given message is formatted like a status message. You should also check whether the message was
     * sent by this bot.
     * @param message the message to check.
     * @return true if the message is a status message.
     */
    public static boolean isStatusMessage(Message message) {
        // this can't be a status message if it wasn't written by us
        if (!message.getAuthor().equals(message.getJDA().getSelfUser())) {
            return false;
        }

        // our status messages only contain one embed
        if (message.getEmbeds().size() != 1) {
            return false;
        }

        // our status messages have a text footer
        MessageEmbed.Footer footer = message.getEmbeds().get(0).getFooter();
        if (footer == null || footer.getText() == null) {
            return false;
        }

        // only status embeds use the FOOTER
        return footer.getText().equals(FOOTER);
    }
}
