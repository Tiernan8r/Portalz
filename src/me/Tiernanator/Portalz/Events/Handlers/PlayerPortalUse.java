package me.Tiernanator.Portalz.Events.Handlers;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.Tiernanator.Portalz.PortalzMain;
import me.Tiernanator.Portalz.Events.CustomEvents.CustomPlayerPortalUseEvent;
import me.Tiernanator.Portalz.Portal.CustomPortal;
import me.Tiernanator.Utilities.Colours.Colour;

public class PlayerPortalUse implements Listener {

	@SuppressWarnings("unused")
	private PortalzMain plugin;
	private ChatColor good = Colour.GOOD.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();

	public PlayerPortalUse(PortalzMain main) {
		plugin = main;
	}
	
	@EventHandler
	public void playerCustomPortalUse(CustomPlayerPortalUseEvent event) {
		
		Player player = event.getPlayer();
		
		CustomPortal customPortal = event.getPortal();
		Location destination = customPortal.getPortalDestination();
		
		player.teleport(destination);
		
		String portalDestinationName = customPortal.getPortalDestinationName();
		
		String x = Double.toString(destination.getX());
		String y = Double.toString(destination.getY());
		String z = Double.toString(destination.getZ());
		String worldName = destination.getWorld().getName();
		
		player.sendMessage(good + "Teleported to " + highlight + portalDestinationName + good + " at: " + informative + "(" + worldName + ")" + " " + x + good + ", " + informative + y + good + ", " + informative + z + good + ".");
		
	}

}
