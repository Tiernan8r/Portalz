package me.Tiernanator.Portalz.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import me.Tiernanator.Utilities.Locations.Region.Region;

public class Temp {
	
	public void command(Player sender) {

		Player player = (Player) sender;

		Block block = player.getTargetBlock((Set<Material>) null, 200);
		ArrayList<Block> searchBlocks = new ArrayList<Block>();
		ArrayList<Block> allBlocks = new ArrayList<Block>();
		searchBlocks.add(block);
		while(true) {
			ArrayList<Block> toSearch = new ArrayList<Block>();
			for(Block b : searchBlocks) {
				System.out.println("Found -> " + b.getLocation() + "=" + b.getType().name());
				allBlocks.add(b);
				ArrayList<Block> fetched = getSurrounding(b);
				for(Block fetchedBlock : fetched) {
					if(allBlocks.contains(fetchedBlock) || toSearch.contains(fetchedBlock)) continue;
					toSearch.add(fetchedBlock);
				}

			}
			if(toSearch.size() == 0) {
				break;
			} else {
				searchBlocks.clear();
				searchBlocks.addAll(toSearch);
				toSearch.clear();
			}
		}
       
		for(Block b : allBlocks){
			b.setType(Material.WOOL);
		}
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
				Material.LEAVES
		};

		List<Material> banned = Arrays.asList(list);

		BlockFace[] faces = new BlockFace[]{BlockFace.UP, 
				BlockFace.DOWN,
				BlockFace.NORTH,
				BlockFace.EAST,
				BlockFace.SOUTH,
				BlockFace.WEST
		};
		for (BlockFace f : faces) {
			Block s = b.getRelative(f);
			if (!banned.contains(s.getType())) {
				blocks.add(s);
			}
		}
		return blocks;
	}

	@SuppressWarnings("deprecation")
	public void flip(List<Block> found) {
		Block highest = null;
		Block lowest = null;
		for (Block block : found) {
			// Find Highest Block
			// Find Lowest block
			if (highest == null || lowest == null) {
				highest = block;
				lowest = block;
				continue;
			}
			if (highest.getLocation().getBlockY() < block.getLocation()
					.getBlockY()) {
				highest = block;
			}
			if (lowest.getLocation().getBlockY() > block.getLocation()
					.getBlockY()) {
				lowest = block;
			}
			// block.setType(Material.WOOL);
		}

		HashMap<Location, Material> materialHashmap = new HashMap<Location, Material>();
		HashMap<Location, Byte> dataHashmap = new HashMap<Location, Byte>();
		for (int i = 0; i <= highest.getLocation().getBlockY()
				- lowest.getLocation().getBlockY(); i++) {
			for (Block block : found) {
				if (block.getLocation()
						.getBlockY() == lowest.getLocation().getBlockY() + i) {
					Block target = null;
					for (Block b : found) {
						if (b.getLocation().getBlockY() == highest
								.getLocation().getBlockY() - i) {
							if (b.getLocation().getBlockX() == block.getLocation()
									.getBlockX()) {
								if (b.getLocation().getBlockZ() == block
										.getLocation().getBlockZ()) {
									target = b;
								}
							}
						}
					}
					if (target == null) {
						materialHashmap.put(block.getLocation(), Material.AIR);
						dataHashmap.put(block.getLocation(), (byte) 0);
						Location location = highest.getLocation().subtract(0, i, 0);
						location.setX(block.getLocation().getBlockX());
						location.setZ(block.getLocation().getBlockZ());

						materialHashmap.put(location, block.getType());
						dataHashmap.put(location, block.getData());

						materialHashmap.put(block.getLocation(), Material.AIR);
						dataHashmap.put(block.getLocation(), (byte) 0);

					} else {
						materialHashmap.put(block.getLocation(), target.getType());
						dataHashmap.put(block.getLocation(), target.getData());
					}
				}
			}
		}

		List<Material> flipBlocks = new ArrayList<Material>();
		List<Material> slabBlocks = new ArrayList<Material>();
		for (Material m : Material.values()) {
			if (m.name().toLowerCase().contains("stairs")) {
				flipBlocks.add(m);
			}
			if (m.name().toLowerCase().contains("step")
					&& !m.name().toLowerCase().contains("double")) {
				slabBlocks.add(m);
			}
		}
		for (Entry<Location, Material> e : materialHashmap.entrySet()) {
			if (e.getValue() == Material.AIR) {
				e.getKey().getBlock().setType(e.getValue());
				int data = 0;
				Byte d = dataHashmap.get(e.getKey());
				int current = 0;
				if (d != null) {
					current = d.intValue();
				} else {
					System.out.println("NULL");
				}
				data = current;
				if (flipBlocks.contains(e.getValue())) {
					if (current < 4) {
						data = current + 4;
					} else {
						data = current - 4;
					}
				}
				if (slabBlocks.contains(e.getValue())) {
					if (current < 9) {
						data = current + 8;
					} else {
						data = current - 8;
					}
				}
				e.getKey().getBlock().setTypeIdAndData(e.getValue().getId(),
						(byte) data, true);
			}
		}
		for (Entry<Location, Material> e : materialHashmap.entrySet()) {
			if (e.getValue().isSolid() && e.getValue() != Material.AIR) {
				e.getKey().getBlock().setType(e.getValue());
				int data = 0;
				Byte d = dataHashmap.get(e.getKey());
				int current = 0;
				if (d != null) {
					current = d.intValue();
				} else {
					System.out.println("NULL");
				}
				data = current;
				if (flipBlocks.contains(e.getValue())) {
					if (current < 4) {
						data = current + 4;
					} else {
						data = current - 4;
					}
				}
				if (slabBlocks.contains(e.getValue())) {
					if (current < 9) {
						data = current + 8;
					} else {
						data = current - 8;
					}
				}
				e.getKey().getBlock().setTypeIdAndData(e.getValue().getId(),
						(byte) data, true);
			}
		}
		for (Entry<Location, Material> e : materialHashmap.entrySet()) {
			if (!e.getValue().isSolid() && e.getValue() != Material.AIR) {
				e.getKey().getBlock().setType(e.getValue());
				int data = 0;
				Byte d = dataHashmap.get(e.getKey());
				int current = 0;
				if (d != null) {
					current = d.intValue();
				} else {
					System.out.println("NULL");
				}
				data = current;
				if (flipBlocks.contains(e.getValue())) {
					if (current < 4) {
						data = current + 4;
					} else {
						data = current - 4;
					}
				}
				if (slabBlocks.contains(e.getValue())) {
					if (current < 9) {
						data = current + 8;
					} else {
						data = current - 8;
					}
				}
				e.getKey().getBlock().setTypeIdAndData(e.getValue().getId(),
						(byte) data, true);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void flip(Region region) {
		
		List<Block> allBlocks = region.allBlocks();
		
		Block highest = null;
		Block lowest = null;
		for (Block block : allBlocks) {
			// Find Highest Block
			// Find Lowest block
			if (highest == null || lowest == null) {
				highest = block;
				lowest = block;
				continue;
			}
			if (highest.getLocation().getBlockY() < block.getLocation()
					.getBlockY()) {
				highest = block;
			}
			if (lowest.getLocation().getBlockY() > block.getLocation()
					.getBlockY()) {
				lowest = block;
			}
			// block.setType(Material.WOOL);
		}

		HashMap<Location, Material> materialHashmap = new HashMap<Location, Material>();
		HashMap<Location, Byte> dataHashmap = new HashMap<Location, Byte>();
		for (int i = 0; i <= highest.getLocation().getBlockY() - lowest.getLocation().getBlockY(); i++) {
			
			for (Block block : allBlocks) {
				
				if (block.getLocation().getBlockY() == lowest.getLocation().getBlockY() + i) {
					
					Block target = null;
					for (Block b : allBlocks) {
						if (b.getLocation().getBlockY() == highest.getLocation().getBlockY() - i) {
							if (b.getLocation().getBlockX() == block.getLocation().getBlockX()) {
								if (b.getLocation().getBlockZ() == block.getLocation().getBlockZ()) {
									target = b;
								}
							}
						}
					}
					if (target == null) {
						
						materialHashmap.put(block.getLocation(), Material.AIR);
						dataHashmap.put(block.getLocation(), (byte) 0);
						Location location = highest.getLocation().subtract(0, i, 0);
						location.setX(block.getLocation().getBlockX());
						location.setZ(block.getLocation().getBlockZ());

						materialHashmap.put(location, block.getType());
						dataHashmap.put(location, block.getData());

						materialHashmap.put(block.getLocation(), Material.AIR);
						dataHashmap.put(block.getLocation(), (byte) 0);

					} else {
						materialHashmap.put(block.getLocation(), target.getType());
						dataHashmap.put(block.getLocation(), target.getData());
					}
				}
			}
		}

		List<Material> flipBlocks = new ArrayList<Material>();
		List<Material> slabBlocks = new ArrayList<Material>();
		for (Material m : Material.values()) {
			if (m.name().toLowerCase().contains("stairs")) {
				flipBlocks.add(m);
			}
			if (m.name().toLowerCase().contains("step") && !m.name().toLowerCase().contains("double")) {
				slabBlocks.add(m);
			}
		}
		for (Entry<Location, Material> e : materialHashmap.entrySet()) {
			if (e.getValue() == Material.AIR) {
				e.getKey().getBlock().setType(e.getValue());
				int data = 0;
				Byte d = dataHashmap.get(e.getKey());
				int current = 0;
				if (d != null) {
					current = d.intValue();
				} else {
					System.out.println("NULL");
				}
				data = current;
				if (flipBlocks.contains(e.getValue())) {
					if (current < 4) {
						data = current + 4;
					} else {
						data = current - 4;
					}
				}
				if (slabBlocks.contains(e.getValue())) {
					if (current < 9) {
						data = current + 8;
					} else {
						data = current - 8;
					}
				}
				e.getKey().getBlock().setTypeIdAndData(e.getValue().getId(),
						(byte) data, true);
			}
		}
		for (Entry<Location, Material> e : materialHashmap.entrySet()) {
			if (e.getValue().isSolid() && e.getValue() != Material.AIR) {
				e.getKey().getBlock().setType(e.getValue());
				int data = 0;
				Byte d = dataHashmap.get(e.getKey());
				int current = 0;
				if (d != null) {
					current = d.intValue();
				} else {
					System.out.println("NULL");
				}
				data = current;
				if (flipBlocks.contains(e.getValue())) {
					if (current < 4) {
						data = current + 4;
					} else {
						data = current - 4;
					}
				}
				if (slabBlocks.contains(e.getValue())) {
					if (current < 9) {
						data = current + 8;
					} else {
						data = current - 8;
					}
				}
				e.getKey().getBlock().setTypeIdAndData(e.getValue().getId(),
						(byte) data, true);
			}
		}
		for (Entry<Location, Material> e : materialHashmap.entrySet()) {
			if (!e.getValue().isSolid() && e.getValue() != Material.AIR) {
				e.getKey().getBlock().setType(e.getValue());
				int data = 0;
				Byte d = dataHashmap.get(e.getKey());
				int current = 0;
				if (d != null) {
					current = d.intValue();
				} else {
					System.out.println("NULL");
				}
				data = current;
				if (flipBlocks.contains(e.getValue())) {
					if (current < 4) {
						data = current + 4;
					} else {
						data = current - 4;
					}
				}
				if (slabBlocks.contains(e.getValue())) {
					if (current < 9) {
						data = current + 8;
					} else {
						data = current - 8;
					}
				}
				e.getKey().getBlock().setTypeIdAndData(e.getValue().getId(),
						(byte) data, true);
			}
		}
	}
	
}
