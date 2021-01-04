package am.trax.discord.minecraft;

import am.trax.discord.minecraft.command.DiagnosticsCommand;
import am.trax.discord.minecraft.command.StatusCommand;
import am.trax.discord.minecraft.command.SupportCommand;
import am.trax.discord.minecraft.config.Configuration;
import am.trax.discord.minecraft.trigger.ReactionTrigger;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

/**
 * This class manages the JDA instance for the bot.
 * @author traxam
 */
@Slf4j
public class Bot {
    private final Configuration config;
    private final JDA jda;

    /**
     * Initializes and starts the discord bot.
     * @param config the configuration for the bot.
     * @throws LoginException if the Discord bot can not authenticate with Discord.
     */
    public Bot(Configuration config) throws LoginException {
        this.config = config;
        this.jda = JDABuilder
                .createDefault(config.getDiscordToken())
                .addEventListeners(buildCommandClient())
                .addEventListeners(new ReactionTrigger(this))
                .build();
        log.info("Startup complete!");
    }

    private CommandClient buildCommandClient() {
        return new CommandClientBuilder()
                .setActivity(Activity.watching("minecraft servers"))
                .setPrefix("+")
                .setOwnerId(config.getOwnerId())
                .addCommand(new DiagnosticsCommand())
                .addCommand(new StatusCommand())
                .addCommand(new SupportCommand(this))
                .build();
    }

    /**
     * @return the configuration of this bot.
     */
    public Configuration getConfig() {
        return config;
    }
}
