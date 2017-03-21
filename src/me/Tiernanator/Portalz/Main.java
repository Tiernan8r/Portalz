package me.Tiernanator.Portalz;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Tiernanator.Portalz.Commands.PortalCreate;
import me.Tiernanator.Portalz.Commands.SetPortalDestination;
import me.Tiernanator.Portalz.Commands.Listers.ListPortalColours;
import me.Tiernanator.Portalz.Commands.Listers.ListPortalDestinations;
import me.Tiernanator.Portalz.Events.Callers.ExplosionNearPortal;
import me.Tiernanator.Portalz.Events.Callers.PlayerBreakPortalBlock;
import me.Tiernanator.Portalz.Events.Callers.PlayerFlintAndSteelInteract;
import me.Tiernanator.Portalz.Events.Callers.PlayerMoveIntoPortal;
import me.Tiernanator.Portalz.Events.Handlers.PlayerPortalBreak;
import me.Tiernanator.Portalz.Events.Handlers.PlayerPortalCreate;
import me.Tiernanator.Portalz.Events.Handlers.PlayerPortalInitialise;
import me.Tiernanator.Portalz.Events.Handlers.PlayerPortalUse;
import me.Tiernanator.Portalz.Events.Handlers.PortalExplode;
import me.Tiernanator.Portalz.Portal.CustomPortal;
import me.Tiernanator.Portalz.Portal.Portal;
import me.Tiernanator.Portalz.Portal.PortalConfig;
import me.Tiernanator.Portalz.Portal.PortalDestinations;
import me.Tiernanator.SQL.SQLServer;
import me.Tiernanator.SQL.MySQL.MySQL;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		
		initialiseSQL();
		setPlugin();
		registerCommands();
		registerEvents();
		
		CustomPortal.initialiseAllPortalsFromConfig();
		
	}

	@Override
	public void onDisable() {
		try {
			getSQL().closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void registerCommands() {
		getCommand("portal").setExecutor(new PortalCreate(this));
		getCommand("createDestination").setExecutor(new SetPortalDestination(this));
		getCommand("portalDestinations").setExecutor(new ListPortalDestinations(this));
		getCommand("portalColours").setExecutor(new ListPortalColours(this));
	}

	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new PlayerMoveIntoPortal(this), this);
		pm.registerEvents(new PlayerFlintAndSteelInteract(this), this);
		pm.registerEvents(new PlayerBreakPortalBlock(this), this);
		pm.registerEvents(new ExplosionNearPortal(this), this);
		
		pm.registerEvents(new PlayerPortalInitialise(this), this);
		pm.registerEvents(new PlayerPortalUse(this), this);
		pm.registerEvents(new PlayerPortalBreak(this), this);
		pm.registerEvents(new PlayerPortalCreate(this), this);
		pm.registerEvents(new PortalExplode(this), this);
		
	}
	
	private void setPlugin() {
		
		PortalConfig.setPlugin(this);
		CustomPortal.setPlugin(this);
		Portal.setPlugin(this);
		PortalDestinations.setPlugin(this);
		
	}

	private static MySQL mySQL;

	private void initialiseSQL() {

		mySQL = new MySQL(SQLServer.HOSTNAME, SQLServer.PORT, SQLServer.DATABASE,
				SQLServer.USERNAME, SQLServer.PASSWORD);
		
//		String query = "CREATE DATABASE IF NOT EXISTS portals;";
		
		Connection connection = null;
		try {
			connection = mySQL.openConnection();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		try {
//			statement.execute(query);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
//		query = "USE portals;";
		String query = "USE " + SQLServer.DATABASE.getInfo() + ";";
		
		statement = null;
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

		query = "CREATE TABLE IF NOT EXISTS Portals ( "
				+ "Name varchar(30) NOT NULL,"
				+ "DestinationName varchar(30) NOT NULL,"
				+ "FrameMaterial varchar(255)"
				+ ");";
		
		statement = null;
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
		
		query = "CREATE TABLE IF NOT EXISTS PortalDestinations ( "
				+ "Name varchar(30) NOT NULL,"
				+ "World varchar(15) NOT NULL, "
				+ "X int NOT NULL, "
				+ "Y int NOT NULL, "
				+ "Z int NOT NULL,"
				+ "Yaw int,"
				+ "Pitch int "
				+ ");";
		
		statement = null;
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static MySQL getSQL() {
		return mySQL;
	}

//	public static Connection getSQLConnection() {
//
//		try {
//			if (!getSQL().checkConnection()) {
//			return getSQL().openConnection();
//		} else {
//			return getSQL().getConnection();
//		}
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//		Connection connection = null;
//		try {
//			if (!getSQL().checkConnection()) {
//				connection = getSQL().openConnection();
//			} else {
//				connection = getSQL().getConnection();
//			}
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		}
//		String query = "USE " + SQLServer.DATABASE.getInfo() + ";";
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
//		return connection;
//	}
}
