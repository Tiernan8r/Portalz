package me.Tiernanator.Portalz.Events.Callers;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Portalz.Main;
import me.Tiernanator.Portalz.Enums.PortalColour;
import me.Tiernanator.Portalz.Events.CustomEvents.CustomPlayerPortalInitialiseEvent;
import me.Tiernanator.Portalz.Portal.Portal;
import me.Tiernanator.Portalz.Portal.PortalDestinations;
import me.Tiernanator.Utilities.Locations.Zones.ZoneName;
import me.Tiernanator.Zoning.Zone.Zone;

public class PlayerFlintAndSteelInteract implements Listener {

	@SuppressWarnings("unused")
	private Main plugin;
	private ChatColor bad = Colour.BAD.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();

	public PlayerFlintAndSteelInteract(Main main) {
		plugin = main;
	}

	// An event handler that detects when the player uses a wand on a block and
	// calls my custom wand block event
	@SuppressWarnings({"deprecation"})
	@EventHandler
	public void onPlayerFlintNSteelInteract(PlayerInteractEvent event) {

		if (event.isCancelled()) {
			return;
		}

		// get the type of interaction, we are only interested in players right
		// clicking on blocks
		Action action = event.getAction();
		if (!(action == Action.RIGHT_CLICK_BLOCK)) {
			return;
		}

		if (event.isCancelled()) {
			return;
		}

		// get the player
		Player player = event.getPlayer();

		// get the player's held item
		ItemStack item = player.getItemInHand();
		Material itemType = item.getType();
		// test if it's flint and steel as we aren't interested in anything else
		if (!(itemType == Material.FLINT_AND_STEEL)) {
			return;
		}

		// get the item's lore and check if it has any, a normal flint and steel
		// won't
		List<String> itemLore = item.getItemMeta().getLore();

		if (itemLore == null || itemLore.isEmpty()) {
			return;
		}

		if (!itemLore
				.contains(ChatColor.DARK_GREEN + "Used to Create a Portal.")) {
			return;
		}

		// check if it's a normal flint and steel or if it has been renamed
		boolean hasName = item.getItemMeta().hasDisplayName();
		if (!(hasName)) {
			return;
		}

		// the format for the flint and steel's name should be:
		// <Portal Name> -> <Destination Name> : <Portal Colour>
		// if the portal Colour is empty, it will default to COLOURLESS.
		String itemName = item.getItemMeta().getDisplayName();
		if (!itemName.contains("->")) {
			player.sendMessage(bad
					+ "The Name of this flint and steel is not properly formatted to create a portal, the format is: ");
			player.sendMessage(highlight
					+ "<Portal Name> -> <Destination Name> : <Portal Colour>");
			player.sendMessage(bad
					+ "Where the items within <> are replaced with the values you want.");
			return;
		}

		if (itemName.equalsIgnoreCase("->")) {
			player.sendMessage(warning
					+ "You have not provided the portal name and destination values");
			return;
		}

		int arrowIndex = itemName.indexOf("->");
		int colonIndex = itemName.length();
		if (itemName.contains(":")) {
			colonIndex = itemName.indexOf(":");
		}

		// get all words before the "->" and make them the portalName
		// All words between "->" and the ":" are the portal destination name
		// and everything after ":" is the portalColour

		String portalName = "";
		String portalDestinationName = "";
		String colour = "";

		char[] itemNameArray = itemName.toCharArray();
		for (int i = 0; i < itemNameArray.length; i++) {
			String thisChar = "" + itemNameArray[i];

			if (i < arrowIndex) {

				if (portalName.equals("")) {
					if (thisChar.equalsIgnoreCase(" "))
						continue;
				}
				portalName += thisChar;

			} else if (i > arrowIndex + 1 && i < colonIndex) {

				if (portalDestinationName.equals("")) {
					if (thisChar.equalsIgnoreCase(" "))
						continue;
				}
				portalDestinationName += thisChar;

			} else if (i > colonIndex) {

				if (colour.equals("")) {
					if (thisChar.equalsIgnoreCase(" "))
						continue;
				}
				colour += thisChar;

			}

		}
		// format the names so that they don't end in a space and oonly include
		// the spaces between words
		String[] portalNameParts = portalName.split(" ");
		portalName = "";
		for (String i : portalNameParts) {
			portalName += i;
			if (!i.equals(portalNameParts[portalNameParts.length - 1])) {
				portalName += " ";
			}
		}
		// replace spaces with _
		portalName = ZoneName.parseNameToZoneCode(portalName);

		// same again
		String[] portalDestinationNameParts = portalDestinationName.split(" ");
		portalDestinationName = "";
		for (String i : portalDestinationNameParts) {
			portalDestinationName += i;
			if (!i.equals(
					portalDestinationNameParts[portalDestinationNameParts.length
							- 1])) {
				portalDestinationName += " ";
			}
		}
		portalDestinationName = ZoneName
				.parseNameToZoneCode(portalDestinationName);

		if (portalName.equals("") || portalDestinationName.equals("")) {
			player.sendMessage(bad
					+ "The Name of this flint and steel is not properly formatted to create a portal, the format is: ");
			player.sendMessage(highlight
					+ "<Portal Name> -> <Destination Name> : <Portal Colour>");
			player.sendMessage(bad
					+ "Where the items within <> are replaced with the values you want.");
			return;
		}

		// and again
		String[] portalColourParts = colour.split(" ");
		colour = "";
		for (String i : portalColourParts) {
			colour += i;
			if (!i.equals(portalColourParts[portalColourParts.length - 1])) {
				colour += " ";
			}
		}
		colour = ZoneName.parseNameToZoneCode(colour);

		PortalColour portalColour = PortalColour.COLOURLESS;
		if (itemName.contains(":")) {
			portalColour = PortalColour.getPortalColour(colour);
		}
		if (portalColour == null) {
			player.sendMessage(warning + "That is not a valid portal colour!");
			return;
		}

		if (Portal.isPortal(portalName)) {
			player.sendMessage(warning + "A portal by the name: " + highlight
					+ portalName + warning + " already exists!");
			return;
		}

		if (!PortalDestinations.isPortalDestination(portalDestinationName)) {

			player.sendMessage(
					warning + "The name: " + highlight + portalDestinationName
							+ warning + " is not a valid portal destination.");
			return;

		}

//		if (!(CoreZone.allZones() == null)) {
//
//			for (String zone : CoreZone.allZones()) {
//				boolean inCoreZone = CoreZone.isInCoreZone(zone,
//						event.getClickedBlock().getLocation());
//				boolean canInteract = CoreZone.canBuild(zone, player);
//				if (inCoreZone && !canInteract) {
//					player.sendMessage(warning + "This is the " + highlight
//							+ zone + warning
//							+ " zone and you can't create a portal here!");
//					event.setCancelled(true);
//					return;
//				}
//			}
//		}

		if (!(Zone.allZones() == null)) {
			for (Zone zone : Zone.allZones()) {
				Block block = event.getClickedBlock();
				boolean inZone = zone.isInZone(block);
				boolean canInteract = zone.canBuild(player);
				
				if (inZone && !canInteract) {
				
					String zoneName = zone.getDisplayName();
					List<String> zoneOwners = zone.getOwnerNames();
					player.sendMessage(warning + "The zone " + informative
							+ zoneName + warning + " belongs to: ");
					for(String owner : zoneOwners) {
						player.sendMessage(highlight + " - " + owner);
					}
					player.sendMessage(warning
							+ " and you can't create a portal here!");
					event.setCancelled(true);
					return;
				}
			}
		}

		event.setCancelled(true);
		// then get the clicked block's location and call the event
		Block clickedBlock = event.getClickedBlock();
		// create the custom event
		CustomPlayerPortalInitialiseEvent portalInitialiseEvent = new CustomPlayerPortalInitialiseEvent(
				clickedBlock, player, portalName, portalDestinationName,
				portalColour);
		// Call the event
		Bukkit.getServer().getPluginManager().callEvent(portalInitialiseEvent);
		event.setCancelled(portalInitialiseEvent.isCancelled());

		item.setDurability((short) (item.getDurability() + 8));
		if (item.getDurability() >= 64) {
			player.getInventory().remove(item);
		}

	}

}
