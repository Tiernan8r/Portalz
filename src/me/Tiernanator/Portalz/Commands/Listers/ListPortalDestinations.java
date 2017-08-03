package me.Tiernanator.Portalz.Commands.Listers;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Portalz.PortalzMain;
import me.Tiernanator.Portalz.Portal.PortalDestinations;
import me.Tiernanator.Utilities.Colours.Colour;
import me.Tiernanator.Utilities.Locations.Zones.ZoneName;

public class ListPortalDestinations implements CommandExecutor {

	@SuppressWarnings("unused")
	private static PortalzMain plugin;

	public ListPortalDestinations(PortalzMain main) {
		plugin = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		ChatColor warning = Colour.WARNING.getColour();
		ChatColor bad = Colour.BAD.getColour();
		ChatColor good = Colour.GOOD.getColour();
		ChatColor informative = Colour.INFORMATIVE.getColour();
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(warning + "You can't use this command.");
			return false;
		}
		
		Player player = (Player) sender;
		
		List<String> allDestinations = PortalDestinations.allPortalDestinations();
		if(allDestinations.isEmpty()) {
			player.sendMessage(bad + "There are no portal destinations...");
		} else {
			player.sendMessage(good + "The destinations are: ");
			for(String i : allDestinations) {
				i = ZoneName.parseZoneCodeToName(i);
				player.sendMessage(informative + " - " + i);
			}
		}
		
		return true;
	}

}
