package am.trax.discord.minecraft.scheduler;

import net.dv8tion.jda.api.entities.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Manages the periodic execution of refresh tasks.
 * @author traxam
 */
public class RefreshScheduler {
    /**
     * The period at which status messages are refreshed, in the {@link #PERIOD_UNIT} time unit.
     */
    private static final long PERIOD = 5;
    /**
     * The time unit for {@link #PERIOD}.
     */
    private static final TimeUnit PERIOD_UNIT = TimeUnit.MINUTES;

    /**
     * The executor service that runs the refresh tasks periodically.
     */
    private final ScheduledExecutorService executorService;
    /**
     * Maps messages objects to periodic tasks. Careful: Message objects are not necessarily equal to each other when
     * even if they represent the same message. Use {@link #findFutureEntryFor(Message)} to find the entry with the
     * corresponding message.
     */
    private final Map<Message, ScheduledFuture<?>> refreshFutures;

    /**
     * Starts a new refresh scheduler.
     */
    public RefreshScheduler() {
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        this.refreshFutures = new HashMap<>();
    }

    /**
     * Finds the refresh task for a given status message.
     * @param message the status message to find the refresh task for.
     * @return the pair of the message object associated with the task (must not be equal to the given message) and the
     * task associated with both the given and returned message.
     */
    private Map.Entry<Message, ScheduledFuture<?>> findFutureEntryFor(Message message) {
        for (Map.Entry<Message, ScheduledFuture<?>> entry : refreshFutures.entrySet()) {
            if (entry.getKey().getChannel().getId().equals(message.getChannel().getId())
                    && entry.getKey().getId().equals(message.getId())) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Starts periodically refreshing the given status message. The message will be first refreshed *after* the first
     * period.
     * @param message the status message to start refreshing periodically.
     * @throws IllegalArgumentException if the message is not a status message.
     */
    public void startPeriodicRefreshingFor(Message message) {
        if (executorService.isShutdown()) {
            throw new RejectedExecutionException("this scheduler is already shut down.");
        }

        RefreshRunnable runnable = new RefreshRunnable(message);

        stopPeriodicRefreshingFor(message);
        ScheduledFuture<?> future = executorService.scheduleWithFixedDelay(runnable, PERIOD, PERIOD, PERIOD_UNIT);
        refreshFutures.put(message, future);
    }

    /**
     * Stops the periodic refreshing for a certain message.
     * @param message the status message to stop periodic refreshing for.
     * @return {@code true} if a task was stopped and {@code false} if there was no refresh task for the message.
     */
    public boolean stopPeriodicRefreshingFor(Message message) {
        Map.Entry<Message, ScheduledFuture<?>> entry = findFutureEntryFor(message);
        if (entry != null) {
            entry.getValue().cancel(false);
            refreshFutures.entrySet().remove(entry);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Terminates all running tasks
     */
    public void shutdownNow() {
        executorService.shutdownNow();
    }
}
