package me.Tiernanator.Portalz.Events.Handlers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Materials.BuildingMaterial;
import me.Tiernanator.Portalz.PortalzMain;
import me.Tiernanator.Portalz.Enums.PortalColour;
import me.Tiernanator.Portalz.Events.CustomEvents.CustomPlayerPortalCreateEvent;
import me.Tiernanator.Portalz.Events.CustomEvents.CustomPlayerPortalInitialiseEvent;
import me.Tiernanator.Portalz.Portal.CustomPortal;
import me.Tiernanator.Portalz.Portal.PortalDestinations;
import me.Tiernanator.Utilities.Blocks.BlockDirection;
import me.Tiernanator.Utilities.Locations.Region.Region;

public class PlayerPortalInitialise implements Listener {

	ChatColor bad = Colour.BAD.getColour();
	
	private static PortalzMain plugin;
	private static List<Block> frameBlocks = new ArrayList<Block>();
	private static List<Block> fillBlocks = new ArrayList<Block>();

	
	public PlayerPortalInitialise(PortalzMain main) {
		plugin = main;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerCustomPortalCreate(CustomPlayerPortalInitialiseEvent event) {
		
		Block block = event.getClickedBlock();
		
		Player player = event.getPlayer();
		
		float yaw = player.getLocation().getYaw();
		
		BlockDirection blockDirection = BlockDirection.getCardinalBlockDirection(yaw);
		
		BlockDirection[] allowedDirections = null;
		BlockDirection[] allowedFillDirections = null;
		
		if(blockDirection == BlockDirection.NORTH || blockDirection == BlockDirection.SOUTH) {
			
			allowedDirections = new BlockDirection[] {
					
					BlockDirection.DOWN,
					BlockDirection.DOWN_EAST,
					BlockDirection.DOWN_WEST,
						
					BlockDirection.UP,
					BlockDirection.UP_EAST,
					BlockDirection.UP_WEST,
						
					BlockDirection.EAST,
					BlockDirection.WEST
			};
			
			allowedFillDirections = new BlockDirection[] {
					BlockDirection.DOWN,
					BlockDirection.UP,
					BlockDirection.EAST,
					BlockDirection.WEST
			};
			
			
		} else if(blockDirection == BlockDirection.WEST || blockDirection == BlockDirection.EAST) {
			
			
			allowedDirections = new BlockDirection[] {
					
					BlockDirection.DOWN,
					BlockDirection.DOWN_NORTH,
					BlockDirection.DOWN_SOUTH,
						
					BlockDirection.UP,
					BlockDirection.UP_NORTH,
					BlockDirection.UP_SOUTH,
						
					BlockDirection.NORTH,
					BlockDirection.SOUTH,
			};
			
			allowedFillDirections = new BlockDirection[] {
					BlockDirection.DOWN,
					BlockDirection.UP,
					BlockDirection.NORTH,
					BlockDirection.SOUTH,
			};

		}
		frameBlocks.clear();
		fillBlocks.clear();
		
		frameBlocks.add(block);
		
		frameBlockDirectionsRecursive(block, allowedDirections);

		int lowestX = 999999999;
		int highestX = -999999999;
		int lowestY = 512;
		int highestY = -1;
		int lowestZ = 999999999;
		int highestZ = -999999999;
		
		for(Block bl : frameBlocks) {
			int x = bl.getLocation().getBlockX();
			int y = bl.getLocation().getBlockY();
			int z = bl.getLocation().getBlockZ();
			
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
		
		
		block = block.getRelative(BlockFace.UP);
		
		fillBlocks.add(block);
		
		if (block.getType() == Material.AIR) { // on
			fillerBlockDirectionsRecursive(block, allowedFillDirections, lowestX, lowestY, lowestZ, highestX, highestY, highestZ);
		}
		
		String destinationName = event.getPortalDestinationName();
		Location destination = PortalDestinations.getPortalDestination(destinationName);
		
		Region frame = new Region(frameBlocks);
		Region portalBlocks = new Region(fillBlocks);
		String portalName = event.getPortalName();
		
		Block block1 = frame.allBlocks().get(0);
		Material material = block1.getType();
		int damage = block1.getData();
		BuildingMaterial originalFrameMaterial = BuildingMaterial.getBuildingMaterial(material, (byte) damage);
		
		PortalColour portalColour = event.getPortalColour();
		
		CustomPortal customPortal = new CustomPortal(portalName, frame, portalBlocks, destinationName, destination, originalFrameMaterial, portalColour, plugin);
		CustomPlayerPortalCreateEvent portalCreateEvent = new CustomPlayerPortalCreateEvent(player, customPortal);
		plugin.getServer().getPluginManager().callEvent(portalCreateEvent);
		event.setCancelled(portalCreateEvent.isCancelled());
		
//		customPortal.initialisePortal();
//		Portal.addPortal(portalName, destinationName, originalFrameMaterial);
//		
//		int middleX = (lowestX + highestX) / 2;
//		int middleY = (lowestY + highestY) / 2;
//		int middleZ = (lowestZ + highestZ) / 2;
//		World world = player.getLocation().getWorld();
//		Location centre = new Location(world, middleX, middleY, middleZ);
//		
//		PortalDestinations.setPortalDestination(portalName, centre);
//		portalBlocks.fill(event.getPortalColour().getCorrespondingBlockType());
//		
//		frame.fill(Portal.frameMaterial);
		
	}

	@SuppressWarnings("deprecation")
	public static void frameBlockDirectionsRecursive(Block origin, BlockDirection[] allowedDirections) {
		
		if(frameBlocks.size() > 100) {
			return;
		}
		Material anchorMaterial = origin.getType();
		int anchorData = origin.getData();
		for (BlockDirection direction : allowedDirections) {
			Block block = BlockDirection.getRelativeBlock(origin, direction);
			if (block.getType() == anchorMaterial && block.getData() == (byte) anchorData) {
				if(!frameBlocks.contains(block)) {
					frameBlocks.add(block);
					frameBlockDirectionsRecursive(block, allowedDirections);
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void fillerBlockDirectionsRecursive(Block origin, BlockDirection[] allowedDirections,
			int lowestX, int lowestY, int lowestZ, int highestX, int highestY, int highestZ) {

		if(fillBlocks.size() > 1000) {
			return;
		}
		
		Material anchorMaterial = origin.getType();
		int anchorData = origin.getData();
		for (BlockDirection direction : allowedDirections) {
			Block block = BlockDirection.getRelativeBlock(origin, direction);
			if (block.getType() == anchorMaterial && block.getData() == (byte) anchorData) {
				
				int blockX = block.getLocation().getBlockX();
				int blockY = block.getLocation().getBlockY();
				int blockZ = block.getLocation().getBlockZ();
				
				if(blockX > highestX || blockX < lowestX || blockY > highestY || blockY < lowestY || blockZ > highestZ || blockZ < lowestZ) {
					continue;
				}
				
				if(!fillBlocks.contains(block)) {
					fillBlocks.add(block);
					fillerBlockDirectionsRecursive(block, allowedDirections, lowestX, lowestY, lowestZ, highestX, highestY, highestZ);
				}
			}
		}
	}
	
}
