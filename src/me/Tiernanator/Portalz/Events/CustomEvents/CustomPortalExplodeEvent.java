package me.Tiernanator.Portalz.Events.CustomEvents;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.Tiernanator.Portalz.Portal.CustomPortal;

//This is the custom player portal use event, it just contains functions that
//return all the values needed

public final class CustomPortalExplodeEvent extends Event implements Cancellable {
	
	//handlers is a variable "handled"(...) by the server
    private static final HandlerList handlers = new HandlerList();
    private CustomPortal portal;
    //the location of the explosion
    private Location explosionLocation;
    //The radius of the explosion
    private float radius;
    //If not null, the entity that caused the explosion
    private Entity entity;
    private boolean isCancelled;

    //constructor for the event that sets the variables
    public CustomPortalExplodeEvent(CustomPortal portal, Location explosionLocation, float explosionRadius, Entity entity) {
        this.portal = portal;
        this.explosionLocation = explosionLocation;
        this.radius = explosionRadius;
        this.entity = entity;
    }

    //get the portal
    public CustomPortal getPortal() {
    	return this.portal;
    }
    //return the block places
    public Location getExplosionLocation() {
        return this.explosionLocation;
    }
    //return the radius of the explosion
    public float getExplosionRadius() {
    	return this.radius;
    }
    //Return the entity that caused the explosion
    public Entity getEntity() {
    	return this.entity;
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