package me.Tiernanator.Portalz.Events.Callers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.Tiernanator.Portalz.PortalzMain;
import me.Tiernanator.Portalz.Events.CustomEvents.CustomPlayerPortalUseEvent;
import me.Tiernanator.Portalz.Portal.CustomPortal;
import me.Tiernanator.Utilities.MetaData.MetaData;
import me.Tiernanator.Utilities.Players.PlayerTime;

public class PlayerMoveIntoPortal implements Listener {

	private PortalzMain plugin;

	public PlayerMoveIntoPortal(PortalzMain main) {
		plugin = main;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerCustomPortalUse(PlayerMoveEvent event) {
		
		Player player = event.getPlayer();
		
		Location playerLocation = player.getLocation();
		
		Block block = playerLocation.getBlock();
		
		boolean isValidPortalBlock = false;
		if(block.getType() == Material.LEGACY_STAINED_GLASS_PANE || block.getType() == Material.LEGACY_THIN_GLASS || block.getType() == Material.LEGACY_IRON_FENCE || block.getType() == Material.LEGACY_PORTAL) {
			isValidPortalBlock = true;
		}
		
		if(!isValidPortalBlock) {
			return;
		}
		
		long currentTime = System.currentTimeMillis();
		PlayerTime playerTime = new PlayerTime(player);
		long previousTime = playerTime.getPreviousPlayerTime();
		long difference = currentTime - previousTime;
		
		if(difference < 4000) {
			return;
		}
		playerTime.addPlayerTime(currentTime);
		
		Object blockMeta = MetaData.getMetadata(block, "isPortal", plugin);
		if(blockMeta == null) {
			return;
		}
//		Object portalDestinationName = MetaData.getMetadata(block, "Destination Name", plugin);
//		if(portalDestinationName == null) {
//			return;
//		}
//		Location destination = PortalDestinations.getPortalDestination((String) portalDestinationName);
		CustomPortal customPortal = (CustomPortal) MetaData.getMetadata(block, "Custom Portal", plugin);
		if(customPortal == null) {
			return;
		}
		
		CustomPlayerPortalUseEvent playerPortalUseEvent = new CustomPlayerPortalUseEvent(player, customPortal);
		plugin.getServer().getPluginManager().callEvent(playerPortalUseEvent);
		event.setCancelled(playerPortalUseEvent.isCancelled());
		
	}

}
