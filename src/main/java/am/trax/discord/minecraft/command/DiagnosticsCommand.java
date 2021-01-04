package am.trax.discord.minecraft.command;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * The diagnostics command responds with some measurements about the bot.
 * @author traxam
 */
public class DiagnosticsCommand extends Command {
    private static final long MEBIBYTE = 1 << 20;

    {
        this.name = "diagnostics";
        this.help = "responds with the bots latency";
        this.guildOnly = false;
        this.hidden = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply(new EmbedBuilder().setDescription("Measuring, please wait...").build(), m -> {
            long ping = event.getMessage().getTimeCreated().until(m.getTimeCreated(), ChronoUnit.MILLIS);
            long gatewayPing = event.getJDA().getGatewayPing();
            event.getJDA().getRestPing().queue(restPing -> {
                int guildCount = event.getJDA().getGuilds().size();
                int cachedUserCount = event.getJDA().getUsers().size();
                MessageEmbed message = createDiagnosticsEmbed(ping,
                        gatewayPing,
                        restPing,
                        guildCount,
                        cachedUserCount,
                        Runtime.getRuntime().totalMemory(),
                        Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
                m.editMessage(message).queue();
            });
        });
    }

    private MessageEmbed createDiagnosticsEmbed(long timeToResponse,
                                                long gatewayPing,
                                                long restPing,
                                                int guildCount,
                                                int cachedUserCount,
                                                long totalMemory,
                                                long usedMemory) {
        double totalMemoryMebiBytes = ((double) totalMemory) / MEBIBYTE;
        double usedMemoryMebiBytes = ((double) usedMemory) / MEBIBYTE;

        double usedMemoryPercentage = (usedMemoryMebiBytes / totalMemoryMebiBytes) * 100;

        return new EmbedBuilder()
                .setTitle("Diagnostics")
                .addField("Time-to-response", String.format("%dms", timeToResponse), true)
                .addField("Gateway ping", String.format("%dms", gatewayPing), true)
                .addField("API ping", String.format("%dms", restPing), true)
                .addField("Amount of servers", String.format("%d", guildCount), true)
                .addField("Amount of cached users", String.format("%d", cachedUserCount), true)
                .addField("JVM memory", String.format(Locale.ENGLISH, "%.1f / %.1f MiB (%.1f%%)", usedMemoryMebiBytes, totalMemoryMebiBytes, usedMemoryPercentage), true)
                .setTimestamp(Instant.now())
                .build();
    }
}
