package me.Tiernanator.Portalz;

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
import me.Tiernanator.Utilities.SQL.SQLServer;

public class PortalzMain extends JavaPlugin {
	
	@Override
	public void onEnable() {
		
		initialiseSQL();
		setPlugin();
		registerCommands();
		registerEvents();
		
		CustomPortal.initialiseAllPortalsFromConfig();
		
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

	private void initialiseSQL() {

		String query = "CREATE TABLE IF NOT EXISTS Portals ( "
				+ "Name varchar(30) NOT NULL,"
				+ "DestinationName varchar(30) NOT NULL,"
				+ "FrameMaterial varchar(255)"
				+ ");";
		SQLServer.executeQuery(query);
		
		query = "CREATE TABLE IF NOT EXISTS PortalDestinations ( "
				+ "Name varchar(30) NOT NULL,"
				+ "World varchar(15) NOT NULL, "
				+ "X int NOT NULL, "
				+ "Y int NOT NULL, "
				+ "Z int NOT NULL,"
				+ "Yaw int,"
				+ "Pitch int "
				+ ");";
		SQLServer.executeQuery(query);
		
	}

}
