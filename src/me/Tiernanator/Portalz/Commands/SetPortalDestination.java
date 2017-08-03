package me.Tiernanator.Portalz.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Portalz.PortalzMain;
import me.Tiernanator.Portalz.Portal.PortalDestinations;
import me.Tiernanator.Utilities.Colours.Colour;
import me.Tiernanator.Utilities.Locations.RelativeLocation;
import me.Tiernanator.Utilities.Locations.Zones.ZoneName;

public class SetPortalDestination implements CommandExecutor {

	@SuppressWarnings("unused")
	private static PortalzMain plugin;

	double x;
	double y;
	double z;
	World world;

	ChatColor warning;
	ChatColor informative;
	ChatColor highlight;
	ChatColor good;
	ChatColor regal;
	ChatColor bad;

	public SetPortalDestination(PortalzMain main) {
		plugin = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		allocate();

		if (!(sender instanceof Player)) {
			sender.sendMessage(warning + "You can't use this command.");
			return false;
		}
		
		boolean override = false;
		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase("override")) {
				override = true;
			}
		}

		if (args.length < 4) {
			sender.sendMessage(warning
					+ "You must provide the destination name and the x, y, z co-ordinates for the destination.");
			return false;
		}

		Player player = (Player) sender;

		String destinationName = args[0];
		if (destinationName.contains("£") || destinationName.contains("%")
				|| destinationName.contains("&")) {
			player.sendMessage(
					warning + "That is an invalid destination name.");
			return false;
		}

		Location relativeLocation = RelativeLocation
				.getRelativeLocationsFromString(player, args[1], args[2],
						args[3]);
		
		if(relativeLocation == null) {
			player.sendMessage(warning + "Those are not valid co-ordinates.");
			return false;
		}
		
		x = relativeLocation.getX();
		y = relativeLocation.getY();
		z = relativeLocation.getZ();

		world = player.getWorld();
		Location destination = relativeLocation;
		float yaw = player.getLocation().getYaw();
		float pitch = player.getLocation().getPitch();
		
		relativeLocation.setYaw(yaw);
		relativeLocation.setPitch(pitch);


		if (!(override)) {
			if(PortalDestinations.isPortalDestination(destinationName)) {
				sender.sendMessage(warning + "The destination "
						+ informative + destinationName + warning
						+ " already exists! To change it's location, append the value "
						+ highlight + "override " + warning
						+ "to the command.");
				return false;
			}
		}
		PortalDestinations.setPortalDestination(destinationName, destination);
		
		String formattedPortalName = ZoneName.parseZoneCodeToName(destinationName);

		player.sendMessage(good + "The destination " + highlight
				+ formattedPortalName + good + " has been set to " + regal
				+ Double.toString(x) + good + " ," + regal + Double.toString(y)
				+ good + " ," + regal + Double.toString(z) + good + ".");

		return true;
	}

	private void allocate() {

		warning = Colour.WARNING.getColour();
		informative = Colour.INFORMATIVE.getColour();
		highlight = Colour.HIGHLIGHT.getColour();
		good = Colour.GOOD.getColour();
		regal = Colour.REGAL.getColour();
		bad = Colour.BAD.getColour();

	}

}
