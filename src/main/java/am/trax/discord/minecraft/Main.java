package am.trax.discord.minecraft;

import am.trax.discord.minecraft.config.Configuration;
import am.trax.discord.minecraft.config.DotenvConfiguration;
import am.trax.discord.minecraft.config.InvalidConfigurationException;

import javax.security.auth.login.LoginException;

/**
 * Entry point to start the "Minecraft Server Status" Discord bot.
 * @author traxam
 */
public class Main {
    /**
     * Starts up the bot.
     * @param args ignored.
     */
    public static void main(String[] args) throws InvalidConfigurationException, LoginException {
        Configuration config = new DotenvConfiguration();
        config.validate();

        Bot bot = new Bot(config);
    }
}
