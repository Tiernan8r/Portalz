package me.Tiernanator.Portalz.Portal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import me.Tiernanator.Portalz.Main;

public class PortalDestinations {

	private static Main plugin;

	public static void setPlugin(Main main) {
		plugin = main;
	}

	public static void setPortalDestination(String name,
			Location destination) {
		
		if(isPortalDestination(name)) {
			removePortalDestination(name);
		}
		
		BukkitRunnable runnable = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				String worldName = destination.getWorld().getName();
				int x = destination.getBlockX();
				int y = destination.getBlockY();
				int z = destination.getBlockZ();
				float yaw = destination.getYaw();
				float pitch = destination.getPitch();
				
				Connection connection = Main.getSQL().getConnection();
				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = connection.prepareStatement(
							"INSERT INTO PortalDestinations (Name, World, X, Y, Z, Yaw, Pitch) VALUES (?, ?, ?, ?, ?, ?, ?);");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.setString(1, name);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.setString(2, worldName);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.setInt(3, x);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.setInt(4, y);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.setInt(5, z);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				int intYaw = (int) (yaw * 100);
				try {
					preparedStatement.setInt(6, intYaw);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				int intPitch = (int) (pitch * 100);
				try {
					preparedStatement.setInt(7, intPitch);
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

//		String worldName = destination.getWorld().getName();
//		int x = destination.getBlockX();
//		int y = destination.getBlockY();
//		int z = destination.getBlockZ();
//		float yaw = destination.getYaw();
//		float pitch = destination.getPitch();
//		
//		Connection connection = Main.getSQL().getConnection();
//		PreparedStatement preparedStatement = null;
//		try {
//			preparedStatement = connection.prepareStatement(
//					"INSERT INTO PortalDestinations (Name, World, X, Y, Z, Yaw, Pitch) VALUES (?, ?, ?, ?, ?, ?, ?);");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			preparedStatement.setString(1, name);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			preparedStatement.setString(2, worldName);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			preparedStatement.setInt(3, x);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			preparedStatement.setInt(4, y);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			preparedStatement.setInt(5, z);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		int intYaw = (int) (yaw * 100);
//		try {
//			preparedStatement.setInt(6, intYaw);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		int intPitch = (int) (pitch * 100);
//		try {
//			preparedStatement.setInt(7, intPitch);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			preparedStatement.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return true;
	}

	public static Location getPortalDestination(String name) {
		
		String query = "SELECT World, X, Y, Z, Yaw, Pitch FROM PortalDestinations WHERE Name = '" + name + "';";

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

		String worldName = "";
		try {
			worldName = resultSet.getString("World");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		World world = Bukkit.getWorld(worldName);
				
		int x = 0;
		try {
			x = resultSet.getInt("X");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int y = 0;
		try {
			y = resultSet.getInt("Y");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int z = 0;
		try {
			z = resultSet.getInt("Z");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		float yaw = 0;
		int intYaw = 0;
		try {
			intYaw = resultSet.getInt("Yaw");
			yaw = intYaw / 100f;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		float pitch = 0;
		int intPitch = 0;
		try {
			intPitch = resultSet.getInt("Pitch");
			pitch = intPitch / 100f;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			statement.closeOnCompletion();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Location destination = new Location(world, x, y, z, yaw, pitch);
		destination.add(0.5, 0, 0.5);
		return destination;
		
	}

	public static void setPortalDestinationWorld(String name,
			String worldName) {
		
		BukkitRunnable runnable = new BukkitRunnable() {
			
			@Override
			public void run() {
				

				String query = "UPDATE PortalDestinations " + "SET World = '"
						+ worldName + "';";

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
//
//		String query = "UPDATE PortalDestinations " + "SET World = '"
//				+ worldName + "';";
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
//		
	}

	public static void setPortalDestinationWorld(String name, World world) {
		setPortalDestinationWorld(name, world.getName());
	}

	public static World getPortalDestinationWorld(String name) {

		String query = "SELECT World FROM PortalDestinations WHERE " + "Name = '" + name + "';";

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

		String worldName = "";
		try {
			worldName = resultSet.getString("World");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			statement.closeOnCompletion();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		World world = plugin.getServer().getWorld(worldName);

		return world;
	}

	public static List<String> allPortalDestinations() {

		List<String> allPortalDestinations = new ArrayList<String>();
		
//		BukkitRunnable runnable = new BukkitRunnable() {
//			
//			@Override
//			public void run() {
				
				String query = "SELECT Name FROM PortalDestinations;";

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
						return allPortalDestinations;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

//				List<String> allPortals = new ArrayList<String>();
				try {
					while(resultSet.next()) {
						allPortalDestinations.add(resultSet.getString(1));
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
		
//		String query = "SELECT Name FROM PortalDestinations;";
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

		return allPortalDestinations;
	}
	
	public static boolean isPortalDestination(String name) {
		
		List<String> allDestinations = allPortalDestinations();
		if(allDestinations == null) {
			return false;
		}
		if(allDestinations.contains(name)) {
			return true;
		} else {
			return false;
		}
	}

	public static void removePortalDestination(String name) {

		if (!isPortalDestination(name)) {
			return;
		}
		
		BukkitRunnable runnable = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				String query = "DELETE FROM PortalDestinations WHERE Name = '" + name + "';";

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

//		String query = "DELETE FROM PortalDestinations WHERE Name = '" + name + "';";
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
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}
	
	
}
