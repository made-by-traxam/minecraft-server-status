package am.trax.discord.minecraft.config;

/**
 * Configuration for the Discord bot.
 * @author traxam
 */
public interface Configuration {
    /**
     * @return the discord bot token for the bot.
     */
    String getDiscordToken();

    /**
     * @return the user ID of the bot owners Discord account.
     */
    String getOwnerId();

    /**
     * @return a Discord invitation URL to the bots support server.
     */
    String getSupportServerLink();

    /**
     * @return an email address to be used for contacting the owner.
     */
    String getSupportEmail();

    /**
     * Checks whether all configuration entries are valid.
     * @throws InvalidConfigurationException if one of the configuration entries is invalid.
     */
    void validate() throws InvalidConfigurationException;
}
