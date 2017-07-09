package me.Tiernanator.Portalz.Commands.Listers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Portalz.PortalzMain;
import me.Tiernanator.Portalz.Commands.Temp;
import me.Tiernanator.Utilities.Locations.Region.Region;

public class test implements CommandExecutor {

	@SuppressWarnings("unused")
	private static PortalzMain plugin;

	public test(PortalzMain main) {
		plugin = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {
		
		Player p = (Player) sender;
		
		Block b = p.getTargetBlock((Set<Material>) null, 200);
		ArrayList<Block> search_blocks = new ArrayList<Block>();
		ArrayList<Block> found = new ArrayList<Block>();
		search_blocks.add(b);
		
		while(true) {
			ArrayList<Block> to_search = new ArrayList<Block>();
			
			for(Block b2:search_blocks){
				System.out.println("Found -> " + b2.getLocation() + " = " + b2.getType().name());
				found.add(b2);
				ArrayList<Block> fetched = getSurrounding(b2);
				for(Block b3:fetched){
					if(found.contains(b3) || to_search.contains(b3)) continue;
						to_search.add(b3);
					}

				}
				if(to_search.size() == 0 || to_search.size() > 1000) {
					break;
				} else {
					search_blocks.clear();
					search_blocks.addAll(to_search);
					to_search.clear();
				}
		}
//		int i = 0;
//		for(Block b4:found){
//			b4.setType(Material.AIR);
//			b4.setData((byte) i);
//			i++;
//			if(i > 15) {
//				i = 0;
//			}
//		}
		Region r = new Region(found);
		Temp.flip(r);
		return true;
	}

	public ArrayList<Block> getSurrounding(Block b) {

		ArrayList<Block> blocks = new ArrayList<Block>();

		Material[] list = new Material[]{Material.AIR,
				Material.WATER,
				Material.STATIONARY_WATER, 
				Material.LAVA,
				Material.STATIONARY_LAVA,
				Material.GRASS,
				Material.DIRT,
				Material.STONE,
				Material.SAND,
				Material.BEDROCK,
				Material.GRAVEL,
				Material.LONG_GRASS
		};

		List<Material> banned = Arrays.asList(list);

		BlockFace[] faces = new BlockFace[]{
				BlockFace.DOWN,
				BlockFace.EAST,
				BlockFace.EAST_NORTH_EAST,
				BlockFace.EAST_SOUTH_EAST,
				BlockFace.NORTH,
				BlockFace.NORTH_EAST,
				BlockFace.NORTH_NORTH_EAST,
				BlockFace.NORTH_NORTH_WEST,
				BlockFace.NORTH_WEST,
				BlockFace.SOUTH,
				BlockFace.SOUTH_EAST,
				BlockFace.SOUTH_SOUTH_EAST,
				BlockFace.SOUTH_SOUTH_WEST,
				BlockFace.SOUTH_WEST,
				BlockFace.UP,
				BlockFace.WEST,
				BlockFace.WEST_NORTH_WEST,
				BlockFace.WEST_SOUTH_WEST
		};
		for (BlockFace f : faces) {
			Block s = b.getRelative(f);
			if (!banned.contains(s.getType())) {
				blocks.add(s);
			}
		}
		return blocks;
	}
	
}
