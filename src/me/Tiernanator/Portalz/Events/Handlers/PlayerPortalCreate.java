package me.Tiernanator.Portalz.Events.Handlers;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Materials.BuildingMaterial;
import me.Tiernanator.Portalz.Main;
import me.Tiernanator.Portalz.Enums.PortalColour;
import me.Tiernanator.Portalz.Events.CustomEvents.CustomPlayerPortalCreateEvent;
import me.Tiernanator.Portalz.Portal.CustomPortal;
import me.Tiernanator.Portalz.Portal.Portal;
import me.Tiernanator.Portalz.Portal.PortalDestinations;
import me.Tiernanator.Utilities.Locations.Region.Region;

public class PlayerPortalCreate implements Listener {

	@SuppressWarnings("unused")
	private static Main plugin;
	private ChatColor bad = Colour.BAD.getColour();
	
	public PlayerPortalCreate(Main main) {
		plugin = main;
	}

	@EventHandler
	public void playerCustomPortalCreate(CustomPlayerPortalCreateEvent event) {
		
		Player player = event.getPlayer();
		CustomPortal customPortal = event.getPortal();
		String portalName = customPortal.getPortalName();
		String destinationName = customPortal.getPortalDestinationName();
		BuildingMaterial originalFrameMaterial = customPortal.getOriginalFramMaterial();
		
		Portal.addPortal(portalName, destinationName, originalFrameMaterial);
		
		int lowestX = 999999999;
		int highestX = -999999999;
		int lowestY = 512;
		int highestY = -1;
		int lowestZ = 999999999;
		int highestZ = -999999999;
		
		for(Block block : customPortal.getPortalFrame().allBlocks()) {
			int x = block.getLocation().getBlockX();
			int y = block.getLocation().getBlockY();
			int z = block.getLocation().getBlockZ();
			
			if(x < lowestX) {
				lowestX = x;
			}
			if(x > highestX) {
				highestX = x;
			}
			if(y < lowestY) {
				lowestY = y;
			}
			if(y > highestY) {
				highestY = y;
			}
			if(z < lowestZ) {
				lowestZ = z;
			}
			if(z > highestZ) {
				highestZ = z;
			}
		}
		
		if(lowestY == highestY) {
			player.sendMessage(bad + "You have no portal frame, the portal cannot be made.");
			event.setCancelled(true);
			return;
		}
		
		int middleX = (lowestX + highestX) / 2;
		int middleY = (lowestY + highestY) / 2;
		int middleZ = (lowestZ + highestZ) / 2;
		World world = player.getLocation().getWorld();
		Location centre = new Location(world, middleX, middleY, middleZ);
		
		PortalDestinations.setPortalDestination(portalName, centre);
		Region portalBlocks = customPortal.getPortalBlocks();
		Region portalFrame = customPortal.getPortalFrame();
		PortalColour portalColour = customPortal.getPortalColour();
		portalBlocks.fill(portalColour.getCorrespondingBlockType());
		
		portalFrame.fill(Portal.frameMaterial);
		
	}

}
