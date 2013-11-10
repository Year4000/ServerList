package net.year4000.serverlist;

import java.util.HashMap;

import net.md_5.bungee.api.*;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerList extends Plugin implements Listener {
    HashMap<String, String> IPS = new HashMap();
    final String MOTD_ONE = "&3[&bY4K&3] &a&oSurvival &2&o| &a&oCreative &2&o| &a&oPVP &2&o| &a&oGames";
    final String MOTD_TWO = "&7Join the community! facebook.com/year4000";
    final String MOTD_PLAYER = "&7Welcome back player!";

    @Override
    public void onEnable() {
        this.getProxy().getPluginManager().registerListener( this, this );
    }

    @EventHandler
    public void onPostLogin( PostLoginEvent event ) {
        ProxiedPlayer player = event.getPlayer();
        String name = player.getName();
        String ip = player.getAddress().toString();
        this.IPS.put( ip, name );
    }

    @EventHandler
    public void onServerPing( ProxyPingEvent event ) {
        // Event classes
        ServerPing response = event.getResponse();
        PendingConnection connection = event.getConnection();

        // Event vars
        String ip = connection.getAddress().toString();
        String motd = replaceColor( MOTD_ONE );
        if ( getPlayer ( ip ) != null ) {
            String motdPlayer = replaceColor( MOTD_PLAYER );
            motdPlayer = motdPlayer.replaceAll( "player",  getPlayer( ip ) );
            motd = motd + " \n" + motdPlayer;
        }
        else {
            motd = motd + " \n" + replaceColor( MOTD_TWO );
        }

        // Set the MOTD
        response.setDescription( motd );
        event.setResponse( response );
    }

    // Make the IP Readable
    public String readIp( String ip ) {
        return ip.substring(1).split(":")[0];
    }

    // Get the player's username with the given IP
    public String getPlayer( String ip ) {
        if ( this.IPS.containsKey( ip ) ) {
            return this.IPS.get( ip );
        }
        else {
            return null;
        }
    }

    // Replace any color defined by Minecraft.
    public String replaceColor( String msg ) {
        final char COLOR_CHAR = '\u00A7';
        return msg.replaceAll("&([0-9a-fA-Fk-rK-R])", COLOR_CHAR + "$1");
    }
}
