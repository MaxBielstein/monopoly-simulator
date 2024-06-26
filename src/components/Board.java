package components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
	private List<BoardLocation> boardLocations;
	private Map<PropertyColor, List<Property>> colorGroups; // Map of color to list of Property objects

	public Board() {
		boardLocations = new ArrayList<>();
		initializeProperties();
		initializeColorGroups();
		sortPropertiesByPosition();
	}

	private void initializeProperties() {
		boardLocations.add(new Property("Mediterranean Avenue", 60, 2, false, 50, 50, PropertyColor.BROWN, 1));
		boardLocations.add(new Property("Baltic Avenue", 60, 4, false, 50, 50, PropertyColor.BROWN, 3));
		boardLocations.add(new Property("Oriental Avenue", 100, 6, false, 50, 50, PropertyColor.LIGHT_BLUE, 6));
		boardLocations.add(new Property("Vermont Avenue", 100, 6, false, 50, 50, PropertyColor.LIGHT_BLUE, 8));
		boardLocations.add(new Property("Connecticut Avenue", 120, 8, false, 50, 50, PropertyColor.LIGHT_BLUE, 9));
		boardLocations.add(new Property("St. Charles Place", 140, 10, false, 100, 100, PropertyColor.PINK, 11));
		boardLocations.add(new Property("States Avenue", 140, 10, false, 100, 100, PropertyColor.PINK, 13));
		boardLocations.add(new Property("Virginia Avenue", 160, 12, false, 100, 100, PropertyColor.PINK, 14));
		boardLocations.add(new Property("St. James Place", 180, 14, false, 100, 100, PropertyColor.ORANGE, 16));
		boardLocations.add(new Property("Tennessee Avenue", 180, 14, false, 100, 100, PropertyColor.ORANGE, 18));
		boardLocations.add(new Property("New York Avenue", 200, 16, false, 100, 100, PropertyColor.ORANGE, 19));
		boardLocations.add(new Property("Kentucky Avenue", 220, 18, false, 150, 150, PropertyColor.RED, 21));
		boardLocations.add(new Property("Indiana Avenue", 220, 18, false, 150, 150, PropertyColor.RED, 23));
		boardLocations.add(new Property("Illinois Avenue", 240, 20, false, 150, 150, PropertyColor.RED, 24));
		boardLocations.add(new Property("Atlantic Avenue", 260, 22, false, 150, 150, PropertyColor.YELLOW, 26));
		boardLocations.add(new Property("Ventnor Avenue", 260, 22, false, 150, 150, PropertyColor.YELLOW, 27));
		boardLocations.add(new Property("Marvin Gardens", 280, 24, false, 150, 150, PropertyColor.YELLOW, 29));
		boardLocations.add(new Property("Pacific Avenue", 300, 26, false, 200, 200, PropertyColor.GREEN, 31));
		boardLocations.add(new Property("North Carolina Avenue", 300, 26, false, 200, 200, PropertyColor.GREEN, 32));
		boardLocations.add(new Property("Pennsylvania Avenue", 320, 28, false, 200, 200, PropertyColor.GREEN, 34));
		boardLocations.add(new Property("Park Place", 350, 35, false, 200, 200, PropertyColor.DARK_BLUE, 37));
		boardLocations.add(new Property("Boardwalk", 400, 50, false, 200, 200, PropertyColor.DARK_BLUE, 39));
		// Add railroads
		boardLocations.add(new Property("Reading Railroad", 200, 25, false, 0, 0, PropertyColor.RAILROAD, 5));
		boardLocations.add(new Property("Pennsylvania Railroad", 200, 25, false, 0, 0, PropertyColor.RAILROAD, 15));
		boardLocations.add(new Property("B. & O. Railroad", 200, 25, false, 0, 0, PropertyColor.RAILROAD, 25));
		boardLocations.add(new Property("Short Line", 200, 25, false, 0, 0, PropertyColor.RAILROAD, 35));
		// Add utilities
		boardLocations.add(new Property("Electric Company", 150, 4, true, 0, 0, PropertyColor.UTILITY, 12));
		boardLocations.add(new Property("Water Works", 150, 4, true, 0, 0, PropertyColor.UTILITY, 28));

	}

	private void sortPropertiesByPosition() {
		Collections.sort(boardLocations, Comparator.comparingInt(BoardLocation::getPosition));
	}

	private void initializeColorGroups() {
		colorGroups = new HashMap<>();
		for (BoardLocation boardLocation : boardLocations) {
			if (boardLocation instanceof Property) {
				Property property = (Property) boardLocation;
				colorGroups.computeIfAbsent(property.getColor(), k -> new ArrayList<>()).add(property);
			}
		}
	}

	public List<BoardLocation> getBoardEntities() {
		return boardLocations;
	}

	public BoardLocation getBoardLocation(String name) {
		for (BoardLocation boardLocation : boardLocations) {
			if (boardLocation.getName().equals(name)) {
				return boardLocation;
			}
		}
		return null; // Entity not found
	}

	public List<Property> getColorGroup(PropertyColor color) {
		return colorGroups.getOrDefault(color, new ArrayList<>());
	}
	
	public int getColorGroupSizeByOwner(PropertyColor color, Player owner) {
	    List<Property> properties = colorGroups.getOrDefault(color, new ArrayList<>());
	    int count = 0;
	    for (Property property : properties) {
	        if (property.getOwner() != null && property.getOwner().equals(owner)) {
	            count++;
	        }
	    }
	    return count;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Board Properties:\n");
		for (BoardLocation boardLocation : boardLocations) {
			sb.append(boardLocation).append("\n");
		}
		return sb.toString();
	}

}
