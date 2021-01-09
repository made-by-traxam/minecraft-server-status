package am.trax.discord.minecraft.message;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

/**
 * Formats an introductory message into a {@link MessageEmbed}.
 * @author traxam
 */
public class IntroductoryMessageFormatter {
    private static final String WAVING_HAND_EMOJI = "\uD83D\uDC4B";

    /**
     * Creates a message embed that explains the bot.
     * @return the created embed.
     */
    public static MessageEmbed createEmbed(JDA jda) {
        return new EmbedBuilder()
                .setTitle(String.format("Hey there, I'm @%s! %s", jda.getSelfUser().getName(), WAVING_HAND_EMOJI))
                .setDescription("I can show you the online status and more information about your favourite " +
                        "minecraft servers.")
                .addField("Try me out by sending the following message:",
                        "```\nmc-status hypixel.net\n```" +
                                "or send `mc-commands` for a complete commands list.",
                        false)
                .setFooter("made by traxam in twenty-twenty-one",
                        "https://assets.gitlab-static.net/uploads/-/system/user/avatar/547248/avatar.png")
                .setColor(new Color(52, 152, 219))
                .build();
    }
}
