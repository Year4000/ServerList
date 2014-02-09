package net.year4000.serverlist;

import net.cubespace.Yamler.Config.Comment;
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

    @Comment("The prefix for the to line.")
    private String prefix = "&3[&bY4K&3]&r ";
    @Comment("What to show on the bottom line if a player is not known.")
    private String noplayer = "&7Join us today!";
    @Comment("What to show on the bottom line when a player is known.")
    private String player = "&7Welcome back player!";

    @Comment("The list to show when a known player hovers over the sample.")
    private List<String> players = new ArrayList<String>() {{
        add("&6====================");
        add("&7Welcome!");
        add("&6====================");
    }};

    @Comment("The messages to be picked at random for the top line.")
    private List<String> messages = new ArrayList<String>() {{
        add("&6Join our Survival Server!");
        add("&6Join our Creative Server!");
        add("&6Join our Games Server!");
    }};

    /**
     * Get the prefix for the fist line
     * @return prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Get the bottom line when no player is found.
     * @return noplayer
     */
    public String getNoPlayer() {
        return noplayer;
    }

    /**
     * Get the message when the player is found.
     * @return player
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Get the messages to show on the fist line.
     * @return messages
     */
    public List<String> getMessages() {
        return messages;
    }

    /**
     * Get the player list
     * @return players
     */
    public List<String> getPlayers() {
        return players;
    }
}
