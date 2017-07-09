package me.Tiernanator.Portalz.Portal;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import me.Tiernanator.Materials.BuildingMaterial;
import me.Tiernanator.Portalz.PortalzMain;
import me.Tiernanator.SQL.SQLServer;

public class Portal {

	public static final BuildingMaterial frameMaterial = getDefaultFrameMaterial();

	private static PortalzMain plugin;
	public static void setPlugin(PortalzMain main) {
		plugin = main;
	}

	public static List<String> allPortalNames() {

		List<String> allPortalNames = new ArrayList<String>();

		String query = "SELECT Name FROM Portals;";
		List<Object> portalResults = SQLServer.getList(query, "Name");
		if(portalResults == null) {
			return null;
		}
		for (Object result : portalResults) {
			allPortalNames.add((String) result);
		}

		return allPortalNames;
	}

	public static boolean isPortal(String name) {

		List<String> allPortals = new ArrayList<String>();
		allPortals = allPortalNames();

		if (allPortals == null) {
			return false;
		}

		if (allPortals.isEmpty() || allPortals.equals(null)
				|| allPortals.size() <= 0) {
			return false;
		}
		return allPortals.contains(name);
	}

	public static String getPortalDestinationName(String portalName) {

		String query = "SELECT DestinationName FROM Portals WHERE Name = '"
				+ portalName + "';";

		return SQLServer.getString(query, "DestinationName");
	}

	public static Location getPortalDestination(String portalName) {
		return PortalDestinations.getPortalDestination(portalName);
	}

	public static void setPortalName(String oldPortalName,
			String newPortalName) {

		String statement = "UPDATE Portals SET Name = ? WHERE Name = ?;";
		Object[] values = new Object[]{newPortalName, oldPortalName};
		SQLServer.executePreparedStatement(statement, values);

	}

	public static void setPortalDestinationName(String portalName,
			String destinationName) {

		String statement = "UPDATE Portals SET DestinationName = ? WHERE Name = ?;";
		Object[] values = new Object[]{destinationName, portalName};
		SQLServer.executePreparedStatement(statement, values);
	}

	public static void setPortalOriginalMaterial(String portalName,
			BuildingMaterial material) {

		String statement = "UPDATE Portals SET FrameMaterial = ? WHERE Name = ?;";
		Object[] values = new Object[]{material.name(), portalName};
		SQLServer.executePreparedStatement(statement, values);

	}

	public static BuildingMaterial getPortalOriginalMaterial(
			String portalName) {

		String query = "SELECT FrameMaterial FROM Portals WHERE Name = '"
				+ portalName + "';";

		String materialName = SQLServer.getString(query, "FrameMaterial");

		BuildingMaterial material = BuildingMaterial
				.getBuildingMaterial(materialName);
		return material;

	}

	public static void addPortal(String name, String destinationName,
			BuildingMaterial originalFrameMaterial) {

		String statement = "INSERT INTO Portals (Name, DestinationName, FrameMaterial) VALUES (?, ?, ?);";
		Object[] values = new Object[]{name, destinationName,
				originalFrameMaterial.name()};
		SQLServer.executePreparedStatement(statement, values);

	}

	public static void removePortal(String name) {

		String query = "DELETE FROM Portals WHERE Name = '" + name + "';";

		SQLServer.executeQuery(query);

	}

	private static BuildingMaterial getDefaultFrameMaterial() {

		String materialName = null;
		try {
			plugin.getConfig().getString("DefaultFrameMaterial");
		} catch (Exception e) {
			materialName = "Diamond";
		}
		BuildingMaterial frameMaterial = BuildingMaterial
				.getBuildingMaterial(materialName);
		if (frameMaterial != null) {
			return frameMaterial;
		}

		return BuildingMaterial.DIAMOND;

	}

}
