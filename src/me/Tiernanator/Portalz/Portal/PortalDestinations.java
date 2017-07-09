package me.Tiernanator.Portalz.Portal;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

import me.Tiernanator.Portalz.PortalzMain;
import me.Tiernanator.SQL.SQLServer;

public class PortalDestinations {

	private static PortalzMain plugin;

	public static void setPlugin(PortalzMain main) {
		plugin = main;
	}

	public static void setPortalDestination(String name, Location destination) {

		if (isPortalDestination(name)) {
			removePortalDestination(name);
		}

		String worldName = destination.getWorld().getName();
		int x = destination.getBlockX();
		int y = destination.getBlockY();
		int z = destination.getBlockZ();
		float yaw = destination.getYaw();
		float pitch = destination.getPitch();
		int intYaw = (int) (yaw * 100);
		int intPitch = (int) (pitch * 100);

		String statement = "INSERT INTO PortalDestinations (Name, World, X, Y, Z, Yaw, Pitch) VALUES (?, ?, ?, ?, ?, ?, ?);";
		Object[] values = new Object[]{name, worldName, x, y, z, intYaw,
				intPitch};
		SQLServer.executePreparedStatement(statement, values);

	}

	public static Location getPortalDestination(String name) {

		String query = "SELECT * FROM PortalDestinations WHERE Name = '" + name
				+ "';";

		Location destination = SQLServer.getLocation(query);
		float yaw = 0;
		float pitch = 0;
		yaw = SQLServer.getFloat(query, "Yaw");
		pitch = SQLServer.getFloat(query, "Pitch");

		destination.setYaw(yaw);
		destination.setPitch(pitch);
		destination.add(0.5, 0, 0.5);
		return destination;

	}

	public static void setPortalDestinationWorld(String name,
			String worldName) {

		String query = "UPDATE PortalDestinations SET World = '" + worldName
				+ "';";
		SQLServer.executeQuery(query);

	}

	public static void setPortalDestinationWorld(String name, World world) {
		setPortalDestinationWorld(name, world.getName());
	}

	public static World getPortalDestinationWorld(String name) {

		String query = "SELECT World FROM PortalDestinations WHERE Name = '"
				+ name + "';";

		String worldName = SQLServer.getString(query, "World");

		World world = plugin.getServer().getWorld(worldName);

		return world;
	}

	public static List<String> allPortalDestinations() {

		List<String> allPortalDestinations = new ArrayList<String>();

		String query = "SELECT Name FROM PortalDestinations;";

		List<Object> results = SQLServer.getList(query, "Name");
		if(results == null) {
			return null;
		}
		for (Object result : results) {
			allPortalDestinations.add((String) result);
		}

		return allPortalDestinations;
	}

	public static boolean isPortalDestination(String name) {

		List<String> allDestinations = allPortalDestinations();
		if (allDestinations == null) {
			return false;
		}
		if (allDestinations.contains(name)) {
			return true;
		} else {
			return false;
		}
	}

	public static void removePortalDestination(String name) {

		if (!isPortalDestination(name)) {
			return;
		}

		String query = "DELETE FROM PortalDestinations WHERE Name = '" + name
				+ "';";
		SQLServer.executeQuery(query);

	}

}
