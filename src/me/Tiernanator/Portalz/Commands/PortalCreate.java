package me.Tiernanator.Portalz.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Portalz.PortalzMain;

public class PortalCreate implements CommandExecutor {

	@SuppressWarnings("unused")
	private static PortalzMain plugin;

	ChatColor warning;
	ChatColor informative;
	ChatColor good;

	public PortalCreate(PortalzMain main) {
		plugin = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		allocate();

		if (!(sender instanceof Player)) {
			sender.sendMessage(warning + "You can't use this command.");
			return true;
		}

		Player player = (Player) sender;

		ItemStack flintAndSteel = new ItemStack(Material.FLINT_AND_STEEL);
		ItemMeta itemMeta = flintAndSteel.getItemMeta();
		
		String itemName = "<> -> <> : <>";
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Used to Create a Portal.");
		itemMeta.setLore(lore);
		itemMeta.setDisplayName(itemName);
		flintAndSteel.setItemMeta(itemMeta);
		player.getInventory().addItem(flintAndSteel);
		
		player.sendMessage(good + "To create a portal, rename this item in the format: ");
		player.sendMessage(informative + "<Portal Name> -> <Destination Name> : <Portal Colour>");
		player.sendMessage(good + "Where the items within <> are replaced with the values you want.");

		return true;
	}

	private void allocate() {

		warning = Colour.WARNING.getColour();
		informative = Colour.INFORMATIVE.getColour();
		good = Colour.GOOD.getColour();

	}

}
