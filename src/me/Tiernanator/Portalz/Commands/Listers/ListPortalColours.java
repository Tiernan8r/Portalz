package me.Tiernanator.Portalz.Commands.Listers;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Portalz.Main;
import me.Tiernanator.Portalz.Enums.PortalColour;

public class ListPortalColours implements CommandExecutor {

	@SuppressWarnings("unused")
	private static Main plugin;

	public ListPortalColours(Main main) {
		plugin = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		ChatColor good = Colour.GOOD.getColour();
		ChatColor informative = Colour.INFORMATIVE.getColour();
		
		sender.sendMessage(good + "The available portal colours are: ");
		
		PortalColour[] allMaterials = PortalColour.MAGENTA.getClass().getEnumConstants();
		
		for(PortalColour colour : allMaterials) {
			sender.sendMessage(informative + " - " + colour.name());
		}
		
		return true;
	}

}
