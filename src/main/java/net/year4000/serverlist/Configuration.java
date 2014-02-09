package net.year4000.serverlist;

import net.cubespace.Yamler.Config.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Configuration extends Config {
    public Configuration(ServerList plugin) {
        CONFIG_HEADER = new String[]{"ServerList Configuration"};
        CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");
        try {
            init();
        } catch (Exception e) {
            plugin.log("The config has an error in it could not init it.");
        }
    }

    private String prefix = "&3[&bY4K&3]&r ";
    private String noplayer = "&7Join us today!";
    private String player = "&7Welcome back player!";

    private List<String> messages = new ArrayList<String>() {{
        add("&6Join our Survival Server!");
        add("&6Join our Creative Server!");
        add("&6Join our Games Server!");
    }};

    // Get the prefix for the fist line
    public String getPrefix() {
        return prefix;
    }

    // Get the bottom line when no player is found.
    public String getNoPlayer() {
        return noplayer;
    }

    // Get the message when the player is found.
    public String getPlayer() {
        return player;
    }

    // Get the messages to show on the fist line.
    public List<String> getMessages() {
        return messages;
    }
}
