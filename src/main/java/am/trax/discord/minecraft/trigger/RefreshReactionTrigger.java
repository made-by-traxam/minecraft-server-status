package am.trax.discord.minecraft.trigger;

import am.trax.discord.minecraft.Bot;
import am.trax.discord.minecraft.message.StatusMessageFormatter;
import am.trax.discord.minecraft.ping.ServerPinger;
import am.trax.discord.minecraft.scheduler.RefreshRunnable;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * Refreshes a server status embed when someone reacts with the {@link #REFRESH_EMOJI refresh emoji} to it.
 * @author traxam
 */
@Slf4j
public class RefreshReactionTrigger extends ListenerAdapter {
    private static final String REFRESH_EMOJI = "\ud83d\udd04";

    private final Bot bot;

    /**
     * Initializes the reaction trigger listener.
     * @param bot the bot instance.
     */
    public RefreshReactionTrigger(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        if (isRefreshEmoji(event.getReactionEmote())) {
            event.retrieveMessage().queue(message -> {
                if (StatusMessageFormatter.isStatusMessage(message)) {
                    new RefreshRunnable(message).run();
                }
            });
        }
    }

    private static boolean isRefreshEmoji(MessageReaction.ReactionEmote emote) {
        return emote.isEmoji() && emote.getEmoji().equals(REFRESH_EMOJI);
    }
}
