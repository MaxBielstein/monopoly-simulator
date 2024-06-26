package actives;

import java.util.Random;

import components.Board;
import components.BoardLocation;
import components.Player;
import components.Property;
import components.PropertyColor;

public class Turn {
    private Player player;
    private Board board;
    private DecisionMaker decisionMaker;
    private Random random;
	private int diceRoll;

    public Turn(Player player, Board board, DecisionMaker decisionMaker) {
        this.player = player;
        this.board = board;
        this.decisionMaker = decisionMaker;
        this.random = new Random();
    }
    
    public int rollDice() {
        // Roll two six-sided dice
        return random.nextInt(6) + 1 + random.nextInt(6) + 1;
    }

    public void execute() {
        if (player.isInJail()) {
            handleJail();
        } else {
            this.diceRoll = rollDice();
            player.move(diceRoll);
            handleLanding(player.getPosition());
        }
    }

    private void handleJail() {
        if (decisionMaker.useGetOutOfJailFreeCard()) {
            // Assume player has a "Get Out of Jail Free" card
            player.setInJail(false);
            return;
        }
        if (decisionMaker.payBail(player)) {
            player.updateBalance(-50); // Assume bail is $50
            player.setInJail(false);
        } else {
            // Player remains in jail, could add more complex behavior here
        }
    }

    private void handleLanding(int position) {
        BoardLocation boardLocationAtPosition = board.getBoardEntities().get(position);
        if (boardLocationAtPosition instanceof Property) {
        	Property property = (Property) boardLocationAtPosition;
            if (property.getOwner() == null) {
                if (decisionMaker.buyProperty(property)) {
                    player.updateBalance(-property.getPrice());
                    property.setOwner(player);
                    player.addProperty(property);
                }
            } else if (!property.getOwner().equals(player) && !property.isMortgaged()) {
                int rent = calculateRent(property);
                player.updateBalance(-rent);
                property.getOwner().updateBalance(rent);
            }
        } else {
        	
        }

        // Handle building houses and hotels
        for (Property p : player.getProperties()) {
            if (p.getNumberOfHouses() < 4 && decisionMaker.buildHouse(p)) {
                // Build house
                player.updateBalance(-p.getHouseCost());
                p.addHouse();
            } else if (p.getNumberOfHouses() == 4 && decisionMaker.buildHotel(p)) {
                // Build hotel
                player.updateBalance(-p.getHotelCost());
                p.addHotel();
            }
        }

        // Handle mortgaging properties
        for (Property p : player.getProperties()) {
            if (decisionMaker.mortgageProperty(p)) {
                player.updateBalance(p.getPrice() / 2); // Assume mortgage value is half the price
                p.mortgage();
            }
        }

        // Handle unmortgaging properties
        for (Property p : player.getProperties()) {
            if (p.isMortgaged() && decisionMaker.unmortgageProperty(p)) {
                player.updateBalance(-(p.getPrice() / 2 + p.getPrice() / 10)); // Assume 10% fee for unmortgaging
                p.unmortgage();
            }
        }
    }

    private int calculateRent(Property property) {
        int baseRent = property.getRent();
        if (property.isDynamicRent()) {
            if (property.getColor() == PropertyColor.UTILITY) {
                int utilitiesOwned = board.getColorGroupSizeByOwner(property.getColor(), property.getOwner());
                return this.diceRoll * (utilitiesOwned == 1 ? 4 : 10);
            } else if (property.getColor() == PropertyColor.RAILROAD) {
                int ownedRailroads = board.getColorGroupSizeByOwner(PropertyColor.RAILROAD, property.getOwner());
                baseRent *= Math.pow(2, ownedRailroads - 1);
            }
        }
        return baseRent;
    }
}
