package me.Tiernanator.Portalz.Events.Callers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.Tiernanator.Portalz.PortalzMain;
import me.Tiernanator.Portalz.Events.CustomEvents.CustomPlayerBreakPortalEvent;
import me.Tiernanator.Portalz.Portal.CustomPortal;
import me.Tiernanator.Portalz.Portal.Portal;
import me.Tiernanator.Portalz.Portal.PortalConfig;
import me.Tiernanator.Portalz.Portal.PortalDestinations;
import me.Tiernanator.Utilities.Blocks.BlockDirection;
import me.Tiernanator.Utilities.MetaData.MetaData;

public class PlayerBreakPortalBlock implements Listener {

	private static PortalzMain plugin;
	private static List<Block> allBlocks = new ArrayList<Block>();

	
	public PlayerBreakPortalBlock(PortalzMain main) {
		plugin = main;
	}

	@EventHandler
	public void playerCustomPortalDestroy(BlockBreakEvent event) {
		
		if(event.isCancelled()) {
			return;
		}

		Block block = event.getBlock();
		
		Object metaData = MetaData.getMetadata(block, "isPortal", plugin);
		if(metaData == null) {
			return;
		}
		Player player = event.getPlayer();
		
		float yaw = player.getLocation().getYaw();
		
		BlockDirection blockDirection = BlockDirection.getCardinalBlockDirection(yaw);
		
		BlockDirection[] allowedDirections = null;
		
		if(blockDirection == BlockDirection.NORTH || blockDirection == BlockDirection.SOUTH) {
			
			allowedDirections = new BlockDirection[] {
					BlockDirection.DOWN,
					BlockDirection.UP,
					BlockDirection.EAST,
					BlockDirection.WEST
			};
			
			
		} else if(blockDirection == BlockDirection.WEST || blockDirection == BlockDirection.EAST) {
			
			allowedDirections = new BlockDirection[] {
					BlockDirection.DOWN,
					BlockDirection.UP,
					BlockDirection.NORTH,
					BlockDirection.SOUTH,
			};

		}
		
		allBlocks.clear();
		allBlocks.add(block);

		getBlockDirectionsRecursive(block, allowedDirections);

		String portalName = (String) MetaData.getMetadata(block, "Portal Name", plugin);
		Portal.removePortal(portalName);
		PortalDestinations.removePortalDestination(portalName);
		PortalConfig.deleteConfig(portalName);
		event.setCancelled(true);
		
		CustomPortal customPortal = (CustomPortal) MetaData.getMetadata(block, "Custom Portal", plugin);
		CustomPlayerBreakPortalEvent playerBreakPortalEvent = new CustomPlayerBreakPortalEvent(player, customPortal, block);
		plugin.getServer().getPluginManager().callEvent(playerBreakPortalEvent);
		event.setCancelled(playerBreakPortalEvent.isCancelled());
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
				if(!allBlocks.contains(block)) {
					allBlocks.add(block);
					getBlockDirectionsRecursive(block, allowedDirections);
				}
			}
		}
	}
	
}
