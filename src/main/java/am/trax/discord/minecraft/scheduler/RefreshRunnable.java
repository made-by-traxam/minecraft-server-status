package am.trax.discord.minecraft.scheduler;

import am.trax.discord.minecraft.message.StatusMessageFormatter;
import am.trax.discord.minecraft.ping.ServerPinger;
import am.trax.discord.minecraft.ping.ServerStatus;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class RefreshRunnable implements Runnable {
    /**
     * The status message that should be refreshed.
     */
    private Message message;

    /**
     * Creates a new status message refresh runnable. Call {@link #run()} to execute the refresh.
     * @param message the status message that should be refreshed.
     */
    public RefreshRunnable(Message message) {
        if (!StatusMessageFormatter.isStatusMessage(message)) {
            throw new IllegalArgumentException("message is not a status message!");
        }
        this.message = message;
    }

    @Override
    public void run() {
        String address = message.getEmbeds().get(0).getTitle();
        ServerStatus status = ServerPinger.ping(address);
        MessageEmbed newEmbed = new StatusMessageFormatter(status).createEmbed();

        message.editMessage(newEmbed).queue(newMessage -> {
            message = newMessage;
        });
    }
}
