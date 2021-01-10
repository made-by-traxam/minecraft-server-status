package am.trax.discord.minecraft.message;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.SelfUser;

import java.awt.*;

/**
 * Formats an introductory message into a {@link MessageEmbed}.
 * @author traxam
 */
public class InvitationMessageFormatter {
    /**
     * Creates a message embed that explains the bot.
     * @return the created embed.
     */
    public static MessageEmbed createEmbed(JDA jda) {
        return new EmbedBuilder()
                .setTitle("Take me somewhere!")
                .setDescription("You can use this bot on your own Discord server or even use in private messages. " +
                        "Click the following link to add this bot to a server.\n" +
                        "\n" +
                        "*Note: You can only add the bot to servers that you have `Manage Server` rights on.*")
                .addField("Invitation link",
                        getInviteLink(jda.getSelfUser()),
                        false)
                .setColor(new Color(52, 152, 219))
                .build();
    }

    private static String getInviteLink(SelfUser bot) {
        String link = String.format("https://discord.com/oauth2/authorize?client_id=%s&scope=bot&permission=347200",
                bot.getId());
        return String.format("[Click me!](%s)", link);
    }
}
