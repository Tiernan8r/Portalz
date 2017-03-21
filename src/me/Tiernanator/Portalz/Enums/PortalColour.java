package me.Tiernanator.Portalz.Enums;

import me.Tiernanator.Materials.BuildingMaterial;

public enum PortalColour {
	
	COLOURLESS,
	WHITE, 
	ORANGE, 
	MAGENTA, 
	LIGHT_BLUE,
	YELLOW, 
	LIME, 
	PINK, 
	GREY, 
	LIGHT_GREY, 
	CYAN,
	PURPLE, 
	BLUE,
	BROWN, 
	GREEN,
	RED,
	BLACK,
	IRON;
	
	public BuildingMaterial getCorrespondingBlockType() {
		
		switch(this) {
			case BLACK :
				return BuildingMaterial.BLACK_STAINED_GLASS_PANE;
			case BLUE :
				return BuildingMaterial.BLUE_STAINED_GLASS_PANE;
			case BROWN :
				return BuildingMaterial.BROWN_STAINED_GLASS_PANE;
			case COLOURLESS :
				return BuildingMaterial.GLASS_PANE;
			case CYAN :
				return BuildingMaterial.CYAN_STAINED_GLASS_PANE;
			case GREEN :
				return BuildingMaterial.GREEN_STAINED_GLASS_PANE;
			case GREY :
				return BuildingMaterial.GREY_STAINED_GLASS_PANE;
			case IRON :
				return BuildingMaterial.IRON_BARS;
			case LIGHT_BLUE :
				return BuildingMaterial.LIGHT_BLUE_STAINED_GLASS_PANE;
			case LIGHT_GREY :
				return BuildingMaterial.LIGHT_GREY_STAINED_GLASS_PANE;
			case LIME :
				return BuildingMaterial.LIME_STAINED_GLASS_PANE;
			case MAGENTA :
				return BuildingMaterial.MAGENTA_STAINED_GLASS_PANE;
			case ORANGE :
				return BuildingMaterial.ORANGE_STAINED_GLASS_PANE;
			case PINK :
				return BuildingMaterial.PINK_STAINED_GLASS_PANE;
			case PURPLE :
				return BuildingMaterial.PURPLE_STAINED_GLASS_PANE;
			case RED :
				return BuildingMaterial.RED_STAINED_GLASS_PANE;
			case WHITE :
				return BuildingMaterial.WHITE_STAINED_GLASS_PANE;
			case YELLOW :
				return BuildingMaterial.YELLOW_STAINED_GLASS_PANE;
			default :
				return null;
			
		}
	}
	
	public static PortalColour getPortalColour(String colourName) {
		
		colourName = colourName.toUpperCase();
		colourName = colourName.replaceAll(" ", "");
		
		switch(colourName) {
			
			case "BLACK" :
				return PortalColour.BLACK;
			case "BLUE" :
				return PortalColour.BLUE;
			case "BROWN" :
				return PortalColour.BROWN;
			case "COLOURLESS" :
				return PortalColour.COLOURLESS;
			case "CYAN" :
				return PortalColour.CYAN;
			case "GREEN" :
				return PortalColour.GREEN;
			case "GREY" :
				return PortalColour.GREY;
			case "IRON" :
				return PortalColour.IRON;
			case "LIGHT_BLUE" :
				return PortalColour.LIGHT_BLUE;
			case "LIGHT_GREY" :
				return PortalColour.LIGHT_GREY;
			case "LIME" :
				return PortalColour.LIME;
			case "MAGENTA" :
				return PortalColour.MAGENTA;
			case "ORANGE" :
				return PortalColour.ORANGE;
			case "PINK" :
				return PortalColour.PINK;
			case "PURPLE" :
				return PortalColour.PURPLE;
			case "RED" :
				return PortalColour.RED;
			case "WHITE" :
				return PortalColour.WHITE;
			case "YELLOW" :
				return PortalColour.YELLOW;
			default :
				return null;
		}
	}
	
}
