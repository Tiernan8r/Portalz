package me.Tiernanator.Portalz.Events.Callers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import me.Tiernanator.Portalz.PortalzMain;
import me.Tiernanator.Portalz.Events.CustomEvents.CustomPortalExplodeEvent;
import me.Tiernanator.Portalz.Portal.CustomPortal;
import me.Tiernanator.Portalz.Portal.Portal;
import me.Tiernanator.Portalz.Portal.PortalConfig;
import me.Tiernanator.Portalz.Portal.PortalDestinations;
import me.Tiernanator.Utilities.Blocks.BlockDirection;
import me.Tiernanator.Utilities.MetaData.MetaData;

public class ExplosionNearPortal implements Listener {

	private static PortalzMain plugin;
	private static List<Block> allBlocks = new ArrayList<Block>();

	
	public ExplosionNearPortal(PortalzMain main) {
		plugin = main;
	}

	@EventHandler
	public void playerCustomPortalDestroy(ExplosionPrimeEvent event) {
		
		if(event.isCancelled()) {
			return;
		}

		Entity entity = event.getEntity();
		Location location = entity.getLocation();
		Block block = location.getBlock();
		World world = block.getWorld();
		int x = block.getX();
		int y = block.getY();
		int z = block.getZ();
		int radius = (int) Math.ceil(event.getRadius());
		
		boolean hasPortalBlock = false;
		for(int i = x - radius; i < x + radius; i++) {
			for(int j = y - radius; j < y + radius; j++) {
				for(int k = z - radius; k < z + radius; k++) {
					Location loc = new Location(world, i, j, k);
					Block bl = loc.getBlock();
					Object metaData = MetaData.getMetadata(bl, "isPortal", plugin);
					if(metaData != null) {
						hasPortalBlock = true;
						block = bl;
						break;
					}
				}
			}
		}
		
		if(!hasPortalBlock) {
			return;
		}
		
		BlockDirection[] allowedDirections = new BlockDirection[] {
				BlockDirection.DOWN,
				BlockDirection.UP,
				BlockDirection.EAST,
				BlockDirection.WEST,
				
				BlockDirection.DOWN,
				BlockDirection.UP,
				BlockDirection.NORTH,
				BlockDirection.SOUTH,
		};
		
		allBlocks.clear();
		allBlocks.add(block);

		getBlockDirectionsRecursive(block, allowedDirections);

		String portalName = (String) MetaData.getMetadata(block, "Portal Name", plugin);
		Portal.removePortal(portalName);
		PortalDestinations.removePortalDestination(portalName);
		PortalConfig.deleteConfig(portalName);
		event.setCancelled(true);
		
		CustomPortal customPortal = (CustomPortal) MetaData.getMetadata(block, "Custom Portal", plugin);
		CustomPortalExplodeEvent portalExplodeEvent = new CustomPortalExplodeEvent(customPortal, location, radius, entity);
		plugin.getServer().getPluginManager().callEvent(portalExplodeEvent);
		event.setCancelled(portalExplodeEvent.isCancelled());
//		customPortal.getPortalBlocks().fill(BuildingMaterial.AIR);
//		
//		for(Block frameBlock : customPortal.getPortalFrame().allBlocks()) {
//			MetaData.setMetadata(frameBlock, "isPortal", null, plugin);
//			MetaData.setMetadata(frameBlock, "Portal Name", null, plugin);
//			MetaData.setMetadata(frameBlock, "Custom Portal", null, plugin);
//		}
//		
//		for(Block portalBlock : customPortal.getPortalBlocks().allBlocks()) {
//			MetaData.setMetadata(portalBlock, "isPortal", null, plugin);
//			MetaData.setMetadata(portalBlock, "Portal Name", null, plugin);
//			MetaData.setMetadata(portalBlock, "Destination Name", null, plugin);
//			MetaData.setMetadata(portalBlock, "Custom Portal", null, plugin);
//		}
//		BuildingMaterial originalFramMaterial = customPortal.getOriginalFramMaterial();
//		Region portalFrame = customPortal.getPortalFrame();
//		portalFrame.fill(originalFramMaterial);
//		
//		block.breakNaturally();
//		world.createExplosion(location, 4f);
		
	}

	@SuppressWarnings("deprecation")
	private void getBlockDirectionsRecursive(Block origin, BlockDirection[] allowedDirections) {
		
		if(allBlocks.size() > 100) {
			return;
		}
		Material anchorMaterial = origin.getType();
		int anchorData = origin.getData();
		for (BlockDirection direction : allowedDirections) {
			Block block = BlockDirection.getRelativeBlock(origin, direction);
			if (block.getType() == anchorMaterial && block.getData() == (byte) anchorData) {
				Object isPortalPart = MetaData.getMetadata(block, "isPortal", plugin);
				if(!allBlocks.contains(block) && isPortalPart != null) {
					allBlocks.add(block);
					getBlockDirectionsRecursive(block, allowedDirections);
				}
			}
		}
	}
	
}
