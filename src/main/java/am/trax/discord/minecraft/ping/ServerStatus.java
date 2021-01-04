package am.trax.discord.minecraft.ping;

import br.com.azalim.mcserverping.MCPingResponse;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A server status contains all status information from pinging a minecraft server.
 * @author traxam
 */
public class ServerStatus {
    /**
     * The address of the Minecraft server this status is from.
     */
    @Getter
    private final String address;
    /**
     * The response from pinging the server or null if the server is {@link #isOnline() offline}.
     */
    private final MCPingResponse ping;
    /**
     * Whether the server was online when it was pinged.
     */
    @Getter
    private final boolean isOnline;
    /**
     * The time that this status information was collected.
     */
    @Getter
    private final Instant timestamp;

    /**
     * Constructs a server status from all of it's attributes.
     * @param address the address of the minecraft server.
     * @param ping the ping response.
     * @param isOnline whether the ping was successful.
     */
    public ServerStatus(String address, MCPingResponse ping, boolean isOnline, Instant timestamp) {
        this.address = address;
        this.ping = ping;
        this.isOnline = isOnline;
        this.timestamp = timestamp;
    }

    /**
     * @return the maximum amount of players allowed on the server.
     * @throws IllegalStateException if the server is offline.
     */
    public int getMaxPlayerAmount() {
        if (!isOnline) {
            throw new IllegalStateException("can not get the maximum amount of players of an offline server!");
        } else {
            return ping.getPlayers().getMax();
        }
    }

    /**
     * @return the amount of players that are on the server.
     * @throws IllegalStateException if the server is offline.
     */
    public int getOnlinePlayerAmount() {
        if (!isOnline) {
            throw new IllegalStateException("can not get the amount of players of an offline server!");
        } else {
            return ping.getPlayers().getOnline();
        }
    }

    /**
     * @return a list of names from players that are on the server.
     * @throws IllegalStateException if the server is offline.
     */
    public List<String> getOnlinePlayerNamesSample() {
        if (!isOnline) {
            throw new IllegalStateException("can not get an online players sample of an offline server!");
        } else if (ping.getPlayers().getSample() == null) {
            // MCServerPing sometimes returns null for the sample
            return new ArrayList<>();
        } else {
            return ping.getPlayers().getSample().stream()
                    .map(MCPingResponse.Player::getName)
                    .collect(Collectors.toList());
        }
    }

    /**
     * @return the ping to the server.
     * @throws IllegalStateException if the server is offline.
     */
    public long getPing() {
        if (!isOnline) {
            throw new IllegalStateException("can not get the ping of an offline server!");
        } else {
            return ping.getPing();
        }
    }

    /**
     * @return the MOTD of the server in plain text (without formatting characters).
     * @throws IllegalStateException if the server is offline.
     */
    public String getPlainTextMotd() {
        if (!isOnline) {
            throw new IllegalStateException("can not get the MOTD of an offline server!");
        } else {
            return ping.getDescription().getStrippedText();
        }
    }
}
