package me.Tiernanator.Portalz.Portal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import me.Tiernanator.Materials.BuildingMaterial;
import me.Tiernanator.Portalz.Main;

public class Portal {
	
	public static final BuildingMaterial frameMaterial = BuildingMaterial.DIAMOND;

	private static Main plugin;
	public static void setPlugin(Main main) {
		plugin = main;
	}
	
	public static List<String> allPortalNames() {

		List<String> allPortalNames = new ArrayList<String>();
		
//		BukkitRunnable runnable = new BukkitRunnable() {
//			
//			@Override
//			public void run() {
			
				String query = "SELECT Name FROM Portals;";

				Connection connection = Main.getSQL().getConnection();
				Statement statement = null;
				try {
					statement = connection.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ResultSet resultSet = null;
				try {
					resultSet = statement.executeQuery(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					if (!resultSet.isBeforeFirst()) {
						return allPortalNames;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

//				List<String> allPortals = new ArrayList<String>();
				try {
					while(resultSet.next()) {
						allPortalNames.add(resultSet.getString(1));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				try {
					statement.closeOnCompletion();
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
//			}
//		};
//		runnable.runTaskAsynchronously(plugin);
		
//		String query = "SELECT Name FROM Portals;";
//
//		Connection connection = Main.getSQL().getConnection();
//		Statement statement = null;
//		try {
//			statement = connection.createStatement();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		ResultSet resultSet = null;
//		try {
//			resultSet = statement.executeQuery(query);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		try {
//			if (!resultSet.isBeforeFirst()) {
//				return null;
//			}
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//
//		List<String> allPortals = new ArrayList<String>();
//		try {
//			while(resultSet.next()) {
//				allPortals.add(resultSet.getString(1));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

		return allPortalNames;
	}

	public static boolean isPortal(String name) {

		List<String> allPortals = new ArrayList<String>();
		allPortals = allPortalNames();
		
		if(allPortals == null) {
			return false;
		}
		
		if(allPortals.isEmpty() || allPortals.equals(null) || allPortals.size() <= 0) {
			return false;
		}
		return allPortals.contains(name);
	}
	
	public static String getPortalDestinationName(String portalName) {

		String query = "SELECT DestinationName FROM Portals WHERE Name = '"
				+ portalName + "';";

		Connection connection = Main.getSQL().getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (!resultSet.isBeforeFirst()) {
				return null;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			resultSet.next();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		String destinationName = "";
		try {
			destinationName = resultSet.getString("DestinationName");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			statement.closeOnCompletion();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return destinationName;
	}

	public static Location getPortalDestination(String portalName) {
		return PortalDestinations.getPortalDestination(portalName);
	}

	public static void setPortalName(String oldPortalName,
			String newPortalName) {
		
		BukkitRunnable runnable = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				String query = "UPDATE Portals " + "SET Name = '" + newPortalName + "'"
						+ " WHERE " + "Name = '" + oldPortalName + "';";

				Connection connection = Main.getSQL().getConnection();
				Statement statement = null;
				try {
					statement = connection.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement.execute(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement.closeOnCompletion();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
		runnable.runTaskAsynchronously(plugin);

//		String query = "UPDATE Portals " + "SET Name = '" + newPortalName + "'"
//				+ " WHERE " + "Name = '" + oldPortalName + "';";
//
//		Connection connection = Main.getSQL().getConnection();
//		Statement statement = null;
//		try {
//			statement = connection.createStatement();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			statement.execute(query);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}

	public static void setPortalDestinationName(String portalName,
			String destinationName) {
		
		BukkitRunnable runnable = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				String query = "UPDATE Portals " + "SET DestinationName = '"
						+ destinationName + "'" + " WHERE " + "Name = '" + portalName
						+ "';";

				Connection connection = Main.getSQL().getConnection();
				Statement statement = null;
				try {
					statement = connection.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement.execute(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement.closeOnCompletion();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
		runnable.runTaskAsynchronously(plugin);

//		String query = "UPDATE Portals " + "SET DestinationName = '"
//				+ destinationName + "'" + " WHERE " + "Name = '" + portalName
//				+ "';";
//
//		Connection connection = Main.getSQL().getConnection();
//		Statement statement = null;
//		try {
//			statement = connection.createStatement();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			statement.execute(query);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}
	
	public static void setPortalOriginalMaterial(String portalName,
			BuildingMaterial material) {

		BukkitRunnable runnable = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				String query = "UPDATE Portals " + "SET FrameMaterial = '" + material.name() + "'"
						+ " WHERE " + "Name = '" + portalName + "';";

				Connection connection = Main.getSQL().getConnection();
				Statement statement = null;
				try {
					statement = connection.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement.execute(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement.closeOnCompletion();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
		runnable.runTaskAsynchronously(plugin);
		
//		String query = "UPDATE Portals " + "SET FrameMaterial = '" + material.name() + "'"
//				+ " WHERE " + "Name = '" + portalName + "';";
//
//		Connection connection = Main.getSQL().getConnection();
//		Statement statement = null;
//		try {
//			statement = connection.createStatement();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			statement.execute(query);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}

	public static BuildingMaterial getPortalOriginalMaterial(String portalName) {

		String query = "SELECT FrameMaterial FROM Portals WHERE Name = '"
				+ portalName + "';";

		Connection connection = Main.getSQL().getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (!resultSet.isBeforeFirst()) {
				return null;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String materialName = "";
		try {
			materialName = resultSet.getString("FrameMaterial");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			statement.closeOnCompletion();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		BuildingMaterial material = BuildingMaterial.getBuildingMaterial(materialName);
		return material;
	}
	
	public static void addPortal(String name, String destinationName, BuildingMaterial originalFrameMaterial) {

		BukkitRunnable runnable = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				Connection connection = Main.getSQL().getConnection();
				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = connection.prepareStatement(
							"INSERT INTO Portals (Name, DestinationName, FrameMaterial) VALUES (?, ?, ?);");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.setString(1, name);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.setString(2, destinationName);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.setString(3, originalFrameMaterial.name());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.closeOnCompletion();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
		runnable.runTaskAsynchronously(plugin);
		
//		Connection connection = Main.getSQL().getConnection();
//		PreparedStatement preparedStatement = null;
//		try {
//			preparedStatement = connection.prepareStatement(
//					"INSERT INTO Portals (Name, DestinationName, FrameMaterial) VALUES (?, ?, ?);");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			preparedStatement.setString(1, name);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			preparedStatement.setString(2, destinationName);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			preparedStatement.setString(3, originalFrameMaterial.name());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			preparedStatement.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

	}
	
	public static void removePortal(String name) {
		
		BukkitRunnable runnable = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				String query = "DELETE FROM Portals WHERE Name = '" + name + "';";

				Connection connection = Main.getSQL().getConnection();
				Statement statement = null;
				try {
					statement = connection.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement.execute(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement.closeOnCompletion();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
		runnable.runTaskAsynchronously(plugin);

//		String query = "DELETE FROM Portals WHERE Name = '" + name + "';";
//
//		Connection connection = Main.getSQL().getConnection();
//		Statement statement = null;
//		try {
//			statement = connection.createStatement();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			statement.execute(query);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

	}

}
