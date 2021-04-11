package me.Tiernanator.Portalz.Events.Handlers;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.Tiernanator.Portalz.PortalzMain;
import me.Tiernanator.Portalz.Events.CustomEvents.CustomPortalExplodeEvent;
import me.Tiernanator.Portalz.Portal.CustomPortal;
import me.Tiernanator.Utilities.Locations.Region.Region;
import me.Tiernanator.Utilities.Materials.BuildingMaterial;
import me.Tiernanator.Utilities.MetaData.MetaData;

public class PortalExplode implements Listener {

	private static PortalzMain plugin;
	
	public PortalExplode(PortalzMain main) {
		plugin = main;
	}

	@EventHandler
	public void playerCustomPortalDestroy(CustomPortalExplodeEvent event) {
		
		if(event.isCancelled()) {
			return;
		}

		Location explosionLocation = event.getExplosionLocation();
		Block block = explosionLocation.getBlock();
		World world = explosionLocation.getWorld();
		
		CustomPortal customPortal = event.getPortal();
		customPortal.getPortalBlocks().fill(BuildingMaterial.getBuildingMaterial("AIR"));
		
		for(Block frameBlock : customPortal.getPortalFrame().allBlocks()) {
			MetaData.setMetadata(frameBlock, "isPortal", null, plugin);
			MetaData.setMetadata(frameBlock, "Portal Name", null, plugin);
			MetaData.setMetadata(frameBlock, "Custom Portal", null, plugin);
		}
		
		for(Block portalBlock : customPortal.getPortalBlocks().allBlocks()) {
			MetaData.setMetadata(portalBlock, "isPortal", null, plugin);
			MetaData.setMetadata(portalBlock, "Portal Name", null, plugin);
			MetaData.setMetadata(portalBlock, "Destination Name", null, plugin);
			MetaData.setMetadata(portalBlock, "Custom Portal", null, plugin);
		}
		BuildingMaterial originalFramMaterial = customPortal.getOriginalFramMaterial();
		Region portalFrame = customPortal.getPortalFrame();
		portalFrame.fill(originalFramMaterial);
		
		block.breakNaturally();
		world.createExplosion(explosionLocation, 4f);
		
	}

}
