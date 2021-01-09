package am.trax.discord.minecraft.config;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * A configuration that is read from "dotenv" (from environment variables or from a .env file).
 * @author traxam
 */
public class DotenvConfiguration implements Configuration {
    private static final String KEY_DISCORD_TOKEN = "DISCORD_TOKEN";
    private static final String KEY_OWNER_ID = "OWNER_ID";
    private static final String KEY_SUPPORT_SERVER = "SUPPORT_SERVER";
    private static final String KEY_SUPPORT_EMAIL = "SUPPORT_EMAIL";

    private final Dotenv dotenv;

    /**
     * Initializes a new configuration that reads from dotenv.
     */
    public DotenvConfiguration() {
        dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
    }

    @Override
    public String getDiscordToken() {
        return dotenv.get(KEY_DISCORD_TOKEN);
    }

    @Override
    public String getOwnerId() {
        return dotenv.get(KEY_OWNER_ID);
    }

    @Override
    public String getSupportServerLink() {
        return dotenv.get(KEY_SUPPORT_SERVER);
    }

    @Override
    public String getSupportEmail() {
        return dotenv.get(KEY_SUPPORT_EMAIL);
    }


    @Override
    public void validate() throws InvalidConfigurationException {
        String discordToken = getDiscordToken();
        if (discordToken == null || discordToken.isBlank()) {
            throw InvalidConfigurationException.missing(KEY_DISCORD_TOKEN);
        }

        String ownerId = getOwnerId();
        if (ownerId == null || ownerId.isBlank()) {
            throw InvalidConfigurationException.missing(KEY_OWNER_ID);
        }

        String supportServer = getSupportServerLink();
        if (supportServer == null || supportServer.isBlank()) {
            throw InvalidConfigurationException.missing(KEY_SUPPORT_SERVER);
        }

        String supportEmail = getSupportEmail();
        if (supportEmail == null || supportEmail.isBlank()) {
            throw InvalidConfigurationException.missing(KEY_SUPPORT_EMAIL);
        }
    }
}
