package me.Tiernanator.Portalz.Events.CustomEvents;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.Tiernanator.Portalz.Portal.CustomPortal;

//This is the custom player portal use event, it just contains functions that
//return all the values needed

public final class CustomPlayerBreakPortalEvent extends Event implements Cancellable {
	
	//handlers is a variable "handled"(...) by the server
    private static final HandlerList handlers = new HandlerList();
    //the player who used the wand
    private Player player;
    private CustomPortal portal;
    private Block block;
    private boolean isCancelled;

    //constructor for the event that sets the variables
    public CustomPlayerBreakPortalEvent(Player player, CustomPortal portal, Block block) {
        this.player = player;
        this.portal = portal;
        this.block = block;
    }

    //get the player who done it
    public Player getPlayer() {
        return player;
    }
    //get the portal
    public CustomPortal getPortal() {
    	return this.portal;
    }
    //Get the block broken
    public Block getBlock() {
    	return this.block;
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