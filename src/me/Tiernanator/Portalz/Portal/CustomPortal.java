package me.Tiernanator.Portalz.Portal;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import me.Tiernanator.Materials.BuildingMaterial;
import me.Tiernanator.Portalz.Enums.PortalColour;
import me.Tiernanator.Utilities.Locations.Region.Region;
import me.Tiernanator.Utilities.MetaData.MetaData;

public class CustomPortal {

	private Region portalFrame;
	private Region portalBlocks;
	private String destinationName;
	private Location destination;
	private String portalName;
	private BuildingMaterial originalFrame;
	private PortalColour portalColour;
	private static JavaPlugin plugin;

	public static void setPlugin(JavaPlugin main) {
		plugin = main;
	}

	public CustomPortal(String portalName, Region frame, Region portalBlocks, String destinationName, Location destination, BuildingMaterial original, PortalColour portalColour, JavaPlugin plugin) {

		this.portalFrame = frame;
		this.portalBlocks = portalBlocks;
		this.portalName = portalName;
		this.destinationName = destinationName;
		this.destination = destination;
		this.originalFrame = original;
		this.portalColour = portalColour;
		
		CustomPortal.plugin = plugin;
		
		PortalConfig.savePortalFrame(portalName, frame.allBlocks());
		PortalConfig.savePortalBlocks(portalName, portalBlocks.allBlocks());
		PortalConfig.savePortalColour(portalName, portalColour);
		this.initialisePortal();

	}

	public Region getPortalFrame() {
		return this.portalFrame;
	}
	
	public String getPortalDestinationName() {
		return this.destinationName;
	}
	
	public Location getPortalDestination() {
		return this.destination;
	}

	public Region getPortalBlocks() {
		return this.portalBlocks;
	}

	public String getPortalName() {
		return this.portalName;
	}
	
	public BuildingMaterial getOriginalFramMaterial() {
		return this.originalFrame;
	}
	
	public PortalColour getPortalColour() {
		return this.portalColour;
	}

	public void initialisePortal() {
		
		Region allPortalBlocks = getPortalBlocks();
		String destinationName = getPortalDestinationName();
		String portalName = getPortalName();
		for (Block block : allPortalBlocks.allBlocks()) {
			MetaData.setMetadata(block, "isPortal", true, plugin);
			MetaData.setMetadata(block, "Portal Name", portalName, plugin);
			MetaData.setMetadata(block, "Destination Name", destinationName, plugin);
			MetaData.setMetadata(block, "Custom Portal", this, plugin);
		}
		
		for (Block block : portalFrame.allBlocks()) {
			MetaData.setMetadata(block, "isPortal", true, plugin);
			MetaData.setMetadata(block, "Portal Name", portalName, plugin);
			MetaData.setMetadata(block, "Custom Portal", this, plugin);
		}

	}

	public static void initialiseAllPortalsFromConfig() {

		List<String> allPortals = Portal.allPortalNames();
		if(allPortals == null) {
			return;
		}

		for (String portalName : allPortals) {

			String destinationName = Portal.getPortalDestinationName(portalName);
			Location destination = PortalDestinations.getPortalDestination(destinationName);
			
			BuildingMaterial originalFrameMaterial = Portal.getPortalOriginalMaterial(portalName);
			
			Region portalFrame = new Region(PortalConfig.getPortalFrame(portalName));
			Region portalBlocks = new Region(PortalConfig.getPortalBlocks(portalName));

			PortalColour portalColour = PortalConfig.getPortalColour(portalName);
			
			CustomPortal portal = new CustomPortal(portalName, portalFrame, portalBlocks, destinationName, destination, originalFrameMaterial, portalColour,
					plugin);
			portal.initialisePortal();

		}
	}
}
