package am.trax.discord.minecraft.trigger;

import am.trax.discord.minecraft.Bot;
import am.trax.discord.minecraft.message.StatusMessageFormatter;
import am.trax.discord.minecraft.ping.ServerPinger;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * The
 * @author traxam
 */
@Slf4j
public class ReactionTrigger extends ListenerAdapter {
    private static final String REFRESH_EMOJI = "\ud83d\udd04";

    private final Bot bot;

    /**
     * Initializes the reaction trigger listener.
     * @param bot the bot instance.
     */
    public ReactionTrigger(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        log.warn(event.getReactionEmote().getAsCodepoints() + event.getReactionEmote().getAsReactionCode() + event.getReactionEmote().getEmoji());
        if (isRefreshEmoji(event.getReactionEmote())) {
            event.retrieveMessage().queue(message -> {
                if (isServerStatusMessage(message)) {
                    refreshServerStatus(message);
                }
            });
        }
    }

    private boolean isServerStatusMessage(Message message) {
        return message.getAuthor().equals(message.getJDA().getSelfUser())
                && !message.getEmbeds().isEmpty(); //TODO this is not accurate! the bot also sends embedded messages that do not describe a server status
    }

    private void refreshServerStatus(Message message) {
        MessageEmbed newEmbed = new StatusMessageFormatter(ServerPinger.ping(message.getEmbeds().get(0).getTitle())).createEmbed();
        message.editMessage(newEmbed).queue();
    }

    private static boolean isRefreshEmoji(MessageReaction.ReactionEmote emote) {
        return emote.isEmoji() && emote.getEmoji().equals(REFRESH_EMOJI);
    }
}
