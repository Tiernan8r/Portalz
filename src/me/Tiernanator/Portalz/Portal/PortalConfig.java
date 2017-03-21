package me.Tiernanator.Portalz.Portal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import me.Tiernanator.File.ConfigAccessor;
import me.Tiernanator.Portalz.Main;
import me.Tiernanator.Portalz.Enums.PortalColour;

public class PortalConfig {

	private static Main plugin;

	public static void setPlugin(Main main) {
		plugin = main;
	}

	public static void savePortalFrame(String portalName, List<Block> frame) {

		ConfigAccessor portalAccessor = new ConfigAccessor(plugin,
				portalName + ".yml", "portalz");
		int i = 1;
		for (Block block : frame) {

			Location location = block.getLocation();
			int x = location.getBlockX();
			int y = location.getBlockY();
			int z = location.getBlockZ();
			World world = location.getWorld();
			String worldName = world.getName();

			portalAccessor.getConfig().set("Frame." + i + ".x", x);
			portalAccessor.getConfig().set("Frame." + i + ".y", y);
			portalAccessor.getConfig().set("Frame." + i + ".z", z);
			portalAccessor.getConfig().set("Frame." + i + ".world", worldName);

			i++;

		}
		portalAccessor.getConfig().set("Frame.Amount", i - 1);
		portalAccessor.saveConfig();
		
	}

	public static List<Block> getPortalFrame(String portalName) {

		List<Block> frame = new ArrayList<Block>();
		
		ConfigAccessor portalAccessor = new ConfigAccessor(plugin,
				portalName + ".yml", "portalz");

		int total = portalAccessor.getConfig().getInt("Frame.Amount");
		for(int i = 1; i <= total; i++) {

			int x = portalAccessor.getConfig().getInt("Frame." + i + ".x");
			int y = portalAccessor.getConfig().getInt("Frame." + i + ".y");
			int z = portalAccessor.getConfig().getInt("Frame." + i + ".z");
			String worldName = portalAccessor.getConfig().getString("Frame." + i + ".world");
			World world = plugin.getServer().getWorld(worldName);

			Location location = new Location(world, x, y, z);
			Block block = location.getBlock();
			frame.add(block);

		}
		return frame;
	}
	
	public static void savePortalBlocks(String portalName, List<Block> portalBlocks) {

		ConfigAccessor portalAccessor = new ConfigAccessor(plugin,
				portalName + ".yml", "portalz");
		int i = 1;
		for (Block block : portalBlocks) {

			Location location = block.getLocation();
			int x = location.getBlockX();
			int y = location.getBlockY();
			int z = location.getBlockZ();
			World world = location.getWorld();
			String worldName = world.getName();

			portalAccessor.getConfig().set("Block." + i + ".x", x);
			portalAccessor.getConfig().set("Block." + i + ".y", y);
			portalAccessor.getConfig().set("Block." + i + ".z", z);
			portalAccessor.getConfig().set("Block." + i + ".world", worldName);

			i++;

		}
		portalAccessor.getConfig().set("Block.Amount", i - 1);
		portalAccessor.saveConfig();

	}
	
	public static List<Block> getPortalBlocks(String portalName) {

		List<Block> portalBlocks = new ArrayList<Block>();
		
		ConfigAccessor portalAccessor = new ConfigAccessor(plugin,
				portalName + ".yml", "portalz");

		int total = portalAccessor.getConfig().getInt("Block.Amount");
		for(int i = 1; i <= total; i++) {

			int x = portalAccessor.getConfig().getInt("Block." + i + ".x");
			int y = portalAccessor.getConfig().getInt("Block." + i + ".y");
			int z = portalAccessor.getConfig().getInt("Block." + i + ".z");
			String worldName = portalAccessor.getConfig().getString("Block." + i + ".world");
			World world = plugin.getServer().getWorld(worldName);

			Location location = new Location(world, x, y, z);
			Block block = location.getBlock();
			portalBlocks.add(block);

		}
		return portalBlocks;
	}

	public static PortalColour getPortalColour(String portalName) {

		ConfigAccessor portalAccessor = new ConfigAccessor(plugin,
				portalName + ".yml", "portalz");

		String colourName = portalAccessor.getConfig().getString("Block.Colour");
		if(colourName == null) {
			colourName = PortalColour.COLOURLESS.name();
		}
		PortalColour portalColour = PortalColour.getPortalColour(colourName);
		
		return portalColour;
	}
	
	public static void savePortalColour(String portalName, PortalColour portalColour) {

		ConfigAccessor portalAccessor = new ConfigAccessor(plugin,
				portalName + ".yml", "portalz");

		portalAccessor.getConfig().set("Block.Colour", portalColour.name());
		portalAccessor.saveConfig();
	}
	
	public static void deleteConfig(String portalName) {
		
	    ConfigAccessor portalAccessor = new ConfigAccessor(plugin, portalName + ".yml", "portalz");
	    for(String key : portalAccessor.getConfig().getKeys(false)) {
	    	portalAccessor.getConfig().set(key, null);
	    }
	    portalAccessor.saveConfig(); 
	    
	    File configFile = new File(plugin.getDataFolder().toString() +  File.separator + "portalz", portalName + ".yml");
	    configFile.delete();
		
	}
	
}
