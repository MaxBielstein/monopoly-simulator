package actives;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import components.Board;
import components.Player;
import components.Property;
import components.PropertyColor;

public class MaxDecisionMaker extends DecisionMaker {
    private Random random = new Random();
    private Board board;

    @Override
    public boolean buyProperty(Property property) {
        Player player = property.getOwner();
        // If player doesn't have a full color group yet, buy as many properties as possible
        if (!hasCompleteColorGroup(player)) {
            return true;
        }

        // After achieving a full color group, only buy properties to prevent other players from completing their color group
        return wouldPreventOpponentColorGroup(property, player);
    }

    @Override
    public boolean buildHouse(Property property) {
        Player player = property.getOwner();
        // Build houses if the player has a complete color group and can afford it
        return hasCompleteColorGroup(player) && player.getBalance() >= property.getHouseCost();
    }

    @Override
    public boolean buildHotel(Property property) {
        Player player = property.getOwner();
        // Build hotels if the player has a complete color group and can afford it
        return hasCompleteColorGroup(player) && player.getBalance() >= property.getHotelCost();
    }

    @Override
    public boolean mortgageProperty(Property property) {
        Player player = property.getOwner();
        // If player has a complete color group, mortgage all other properties to maintain at least $600 or highest current rent
        if (hasCompleteColorGroup(player)) {
            int highestRent = getHighestRentProperty(player).getRent();
            int requiredAmount = Math.max(600, highestRent);
            return player.getBalance() < requiredAmount;
        }
        return false;
    }

    @Override
    public boolean unmortgageProperty(Property property) {
        // Only unmortgage if the player has enough money and it is part of the complete color group
        Player player = property.getOwner();
        return player.getBalance() >= (property.getPrice() / 2 + property.getPrice() / 10) && isPartOfCompleteColorGroup(player, property);
    }

    @Override
    public boolean payBail(Player player) {
        // Pay bail if no complete color group, otherwise roll three times to get out
        return !hasCompleteColorGroup(player);
    }

    @Override
    public boolean useGetOutOfJailFreeCard() {
        // Always use a "Get Out of Jail Free" card if available
        return true;
    }

    private boolean hasCompleteColorGroup(Player player) {
        List<Property> properties = player.getProperties();
        Map<PropertyColor, Integer> colorCount = new HashMap<>();

        // Count the number of properties owned by color
        for (Property property : properties) {
            PropertyColor color = property.getColor();
            colorCount.put(color, colorCount.getOrDefault(color, 0) + 1);
        }

        // Check if the player has all properties in any color group
        for (PropertyColor color : colorCount.keySet()) {
            List<Property> colorGroup = board.getColorGroup(color);
            if (colorGroup != null && colorCount.get(color) == colorGroup.size()) {
                return true;
            }
        }

        return false;
    }

    private boolean wouldPreventOpponentColorGroup(Property property, Player player) {
        // Retrieve all properties in the color group of the property in question
        List<Property> groupProperties = board.getColorGroup(property.getColor());

        // Check if any opponent is one property away from completing this color group
        Map<Player, Integer> ownershipCount = new HashMap<>();
        for (Property p : groupProperties) {
            Player owner = p.getOwner();
            if (owner != null && !owner.equals(player)) {
                ownershipCount.put(owner, ownershipCount.getOrDefault(owner, 0) + 1);
            }
        }

        // Determine if preventing strategy is applicable
        for (Map.Entry<Player, Integer> entry : ownershipCount.entrySet()) {
            if (entry.getValue() == groupProperties.size() - 1) { // Opponent owns all but one of the properties in the group
                return true;
            }
        }

        return false;
    }

    private boolean isPartOfCompleteColorGroup(Player player, Property property) {
        // Retrieve all properties in the color group of the property in question
        List<Property> groupProperties = board.getColorGroup(property.getColor());

        // Check if the player owns all properties in this color group
        for (Property p : groupProperties) {
            if (p.getOwner() == null || !p.getOwner().equals(player)) {
                return false;
            }
        }

        return true;
    }


    private Property getHighestRentProperty(Player player) {
        // Get the property with the highest rent
        Property highestRentProperty = null;
        int highestRent = 0;
        for (Property property : player.getProperties()) {
            if (property.getRent() > highestRent) {
                highestRent = property.getRent();
                highestRentProperty = property;
            }
        }
        return highestRentProperty;
    }
}
