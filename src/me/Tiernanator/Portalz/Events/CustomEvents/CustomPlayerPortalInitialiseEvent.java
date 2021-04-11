package me.Tiernanator.Portalz.Events.CustomEvents;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.Tiernanator.Portalz.Enums.PortalColour;

//This is the custom player create portal event with a flint and steel event that is called by PlayerFlintAndSteelInteract, it just contains functions that
//return all the values needed

public final class CustomPlayerPortalInitialiseEvent extends Event implements Cancellable {
	
	//handlers is a variable "handled"(...) by the server
    private static final HandlerList handlers = new HandlerList();
    //the clicked location
    private Block clickedBlock;
    //the player who used the wand
    private Player player;
    private String portalName;
    private String portalDestinationName;
    private PortalColour portalColour;
    private boolean isCancelled;

    //constructor for the event that sets the variables
    public CustomPlayerPortalInitialiseEvent(Block clickedBlock, Player player, String portalName, String portalDestinationName, PortalColour portalColour) {
        this.clickedBlock = clickedBlock;
        this.player = player;
        this.portalName = portalName;
        this.portalDestinationName = portalDestinationName;
        this.portalColour = portalColour;
    }

    //return the location clicked
    public Block getClickedBlock() {
        return clickedBlock;
    }
    //get the player who done it
    public Player getPlayer() {
        return player;
    }
    //get the new portal's name
    public String getPortalName() {
    	return this.portalName;
    }
    //get the new portal's destination's name
    public String getPortalDestinationName() {
    	return this.portalDestinationName;
    }
    //get the new portal's colour 
    public PortalColour getPortalColour() {
    	return this.portalColour;
    }
    //the next two are necessary for the server to use the event
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		isCancelled = cancelled;
	}
	
}