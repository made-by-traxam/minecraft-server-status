package am.trax.discord.minecraft.config;

/**
 * An InvalidConfigurationException indicates that a configuration is invalid.
 * @author traxam
 */
public class InvalidConfigurationException extends Exception {
    /**
     * Constructs a new InvalidConfigurationException with the given message.
     * @param message the message describing the exception.
     */
    public InvalidConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidConfigurationException that indicates a missing configuration entry.
     * @param propertyName the name of the configuration entry that is missing.
     * @return the newly created exception.
     */
    public static InvalidConfigurationException missing(String propertyName) {
        return new InvalidConfigurationException(String.format("Missing configuration entry '%s'!", propertyName));
    }
}
