package net.year4000.serverlist;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.connection.PendingConnection;
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
    HashMap<String, String> IPS = new HashMap<String, String>();
    Configuration config;

    @Override
    public void onEnable() {
        config = new Configuration(this);
        getProxy().getPluginManager().registerListener(this, this);
        log("Enabled");
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        IPS.put(player.getAddress().toString(), player.getName());
    }

    @EventHandler
    public void onServerPing(ProxyPingEvent event) {
        // Event variables
        ServerPing response = event.getResponse();
        PendingConnection connection = event.getConnection();
        String ip = connection.getAddress().toString();
        String motd = replaceColor(config.getPrefix());
        Random rand = new Random(System.currentTimeMillis());

        // Load the random messages top layer.
        List<String> messages = config.getMessages();
        String message = messages.get(Math.abs(rand.nextInt() % messages.size()));
        motd += replaceColor(message);

        // Load the player bottom layer.
        if (getPlayer(ip) != null) {
            String motdPlayer = replaceColor(config.getPlayer());
            motd += " \n" + motdPlayer.replaceAll("player", getPlayer(ip));
        } else {
            motd += " \n" + replaceColor(config.getNonplayer());
        }

        // Set the MOTD
        response.setDescription(motd);
        event.setResponse(response);
    }

    // Get the player's username with the given IP
    public String getPlayer(String ip) {
        if (IPS.containsKey(ip)) {
            return IPS.get(ip);
        } else {
            return null;
        }
    }

    // Replace any color defined by Minecraft.
    public String replaceColor(String msg) {
        final char COLOR_CHAR = '\u00A7';
        return msg.replaceAll("&([0-9a-fA-Fk-rK-R])", COLOR_CHAR + "$1");
    }

    // Log an info message
    public void log(String message) {
        getLogger().log(Level.INFO, message);
    }
}
