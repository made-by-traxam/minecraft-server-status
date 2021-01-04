package am.trax.discord.minecraft.ping;

import br.com.azalim.mcserverping.MCPing;
import br.com.azalim.mcserverping.MCPingResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;

/**
 * This class is responsible for pinging Minecraft servers.
 * @author traxam
 */
@Slf4j
public class ServerPinger {
    /**
     * Pings the Minecraft server at the given address.
     * @param address the address of the Minecraft server to ping.
     * @return the status of the pinged server.
     */
    public static ServerStatus ping(String address) {
        Instant timestamp = Instant.now();
        MCPingResponse ping = null;

        try {
            ping = MCPing.getPing(address);
        } catch (IOException e) {
            //TODO differentiate between different types of exceptions, i.e. UnknownHostException
            log.error("Error while pinging Minecraft server", e);
        }

        return new ServerStatus(address, ping, ping != null, timestamp);
    }
}
