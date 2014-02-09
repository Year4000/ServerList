package net.year4000.serverlist;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public class ServerList extends Plugin implements Listener {
    private Random rand = new Random(System.currentTimeMillis());
    private HashMap<String, String> IPS = new HashMap<String, String>();
    private Configuration config;

    @Override
    public void onEnable() {
        config = new Configuration(this);
        getProxy().getPluginManager().registerListener(this, this);
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        IPS.put(player.getAddress().toString(), player.getName());
    }

    @EventHandler
    public void onServerPing(ProxyPingEvent event) {
        // Event variables
        config = new Configuration(this);
        ServerPing response = event.getResponse();
        ServerPing.PlayerInfo[] players;
        String ip = event.getConnection().getAddress().toString();
        String motd = replaceColor(config.getPrefix());

        // Load the random messages top layer.
        List<String> messages = config.getMessages();
        String message = messages.get(Math.abs(rand.nextInt() % messages.size()));
        motd += replaceColor(message);

        // Load the player bottom layer and player sample.
        if (getPlayer(ip) != null) {
            // Bottom row is a player is found.
            String motdPlayer = replaceColor(config.getPlayer());
            motd += " \n" + motdPlayer.replaceAll("player", getPlayer(ip));

            // Set the player's ping to the one in the config.
            players = new ServerPing.PlayerInfo[config.getPlayers().size()];
            for (int i = 0; i < players.length; i++) {
                String line = config.getPlayers().get(i);
                players[i] = new ServerPing.PlayerInfo(replaceColor(line), "");
            }
        }
        else {
            // Bottom row if no player is found.
            motd += " \n" + replaceColor(config.getNoPlayer());

            // Set the player's ping for players on the server.
            ProxyServer proxy = ProxyServer.getInstance();
            int playerCount = proxy.getOnlineCount();
            players = new ServerPing.PlayerInfo[playerCount];
            for (int i = 0; i < playerCount; i++) {
                String line = proxy.getPlayers().toArray()[i].toString();
                players[i] = new ServerPing.PlayerInfo(replaceColor(line), "");
            }
        }

        // Set the Ping Response
        response.getPlayers().setSample(players);
        response.setDescription(motd);
        event.setResponse(response);
    }

    /**
     * Get the player's username with the given IP
     * @param ip The address to check with.
     * @return null|player username
     */
    public String getPlayer(String ip) {
        return IPS.containsKey(ip) ? IPS.get(ip) : null;
    }

    /**
     * Replace any color defined by Minecraft.
     * @param message The message to convert.
     * @return The message with Minecraft colors.
     */
    public String replaceColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Log an info message
     */
    public void log(String message) {
        getLogger().log(Level.INFO, message);
    }
}
