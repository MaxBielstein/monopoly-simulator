package actives;

import components.Player;
import components.Property;

public abstract class DecisionMaker {

    // Decides whether to buy a property, based on the current game state
    public abstract boolean buyProperty(Property property);

    // Decides whether to build a house on a property
    public abstract boolean buildHouse(Property property);

    // Decides whether to build a hotel on a property
    public abstract boolean buildHotel(Property property);

    // Decides whether to mortgage a property
    public abstract boolean mortgageProperty(Property property);

    // Decides whether to unmortgage a property
    public abstract boolean unmortgageProperty(Property property);

    // Decides whether to pay bail to get out of jail
    public abstract boolean payBail(Player player);

    // Decides whether to use a "Get Out of Jail Free" card
    public abstract boolean useGetOutOfJailFreeCard();
}

