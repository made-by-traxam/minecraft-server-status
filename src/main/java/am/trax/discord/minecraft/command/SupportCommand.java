package am.trax.discord.minecraft.command;

import am.trax.discord.minecraft.Bot;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

/**
 * Responds with links to support articles and contact info.
 * @author traxam
 */
public class SupportCommand extends Command {
    private final Bot bot;

    /**
     * Initializes this command.
     * @param bot the bot instance that this command is part of.
     */
    public SupportCommand(Bot bot) {
        this.bot = bot;
        this.name = "support";
        this.help = "shows a list of helpful sources.";
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply(createSupportEmbed());
    }

    private MessageEmbed createSupportEmbed() {
        return new EmbedBuilder()
                .setTitle("Need help?")
                .setDescription("If you need assistance with using the bot or if you have found a bug, please " +
                        "contact me in any of the following ways:\n\n" +
                        "\u2022 Join the [support Discord server](" + bot.getConfig().getSupportServerLink() + ")\n" +
                        "\u2022 Send me an email to `" + bot.getConfig().getSupportEmail() + "`")
                .setColor(new Color(52, 152, 219))
                .build();
    }
}
