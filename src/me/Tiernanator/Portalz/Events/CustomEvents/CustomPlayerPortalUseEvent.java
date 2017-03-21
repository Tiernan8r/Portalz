package me.Tiernanator.Portalz.Events.CustomEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.Tiernanator.Portalz.Portal.CustomPortal;

//This is the custom player portal use event, it just contains functions that
//return all the values needed

public final class CustomPlayerPortalUseEvent extends Event implements Cancellable {
	
	//handlers is a variable "handled"(...) by the server
    private static final HandlerList handlers = new HandlerList();
    //the player who used the wand
    private Player player;
    private CustomPortal portal;
    private boolean isCancelled;

    //constructor for the event that sets the variables
    public CustomPlayerPortalUseEvent(Player player, CustomPortal portal) {
        this.player = player;
        this.portal = portal;
    }

    //get the player who done it
    public Player getPlayer() {
        return player;
    }
    //get the portal
    public CustomPortal getPortal() {
    	return this.portal;
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