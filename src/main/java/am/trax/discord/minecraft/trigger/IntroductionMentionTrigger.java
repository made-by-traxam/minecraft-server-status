package am.trax.discord.minecraft.trigger;

import am.trax.discord.minecraft.message.IntroductoryMessageFormatter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * Sends an introductory message when mentioned.
 * @author traxam
 */
@Slf4j
public class IntroductionMentionTrigger extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()
                && isBotMention(event.getMessage().getContentRaw(), event.getJDA().getSelfUser())) {
            event.getMessage().reply(IntroductoryMessageFormatter.createEmbed(event.getJDA())).queue();
        }
    }

    private boolean isBotMention(String text, SelfUser bot) {
        return text.equals(bot.getAsMention()) || text.equals(String.format("<@!%s>", bot.getId()));
    }

}
