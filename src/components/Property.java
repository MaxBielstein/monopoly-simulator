package components;

public class Property extends BoardLocation {
    private int price;
    private int rent;
    private int houseCost;
    private int hotelCost;
    private int numberOfHouses;
    private int numberOfHotels;
    private Player owner;
    private boolean isMortgaged;
    private boolean isDynamicRent;
    private PropertyColor color;

    public Property(String name, int price, int rent, boolean isDynamicRent, int houseCost, int hotelCost, PropertyColor color, int location) {
        super(name, location);
        this.price = price;
        this.rent = rent;
        this.houseCost = houseCost;
        this.hotelCost = hotelCost;
        this.numberOfHouses = 0;
        this.numberOfHotels = 0;
        this.setPosition(location);
        this.owner = null;
        this.isMortgaged = false;
        this.isDynamicRent = isDynamicRent;
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public int getRent() {
        return rent;
    }

    public int getHouseCost() {
        return houseCost;
    }

    public int getHotelCost() {
        return hotelCost;
    }

    public int getNumberOfHouses() {
        return numberOfHouses;
    }

    public void addHouse() {
        if (numberOfHouses < 4) {
            numberOfHouses++;
        }
    }

    public int getNumberOfHotels() {
        return numberOfHotels;
    }

    public void addHotel() {
        if (numberOfHouses == 4) {
            numberOfHouses = 0; // Convert 4 houses to 1 hotel
            numberOfHotels++;
        }
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isMortgaged() {
        return isMortgaged;
    }

    public void mortgage() {
        this.isMortgaged = true;
    }

    public void unmortgage() {
        this.isMortgaged = false;
    }

    public boolean isDynamicRent() {
        return isDynamicRent;
    }

    public PropertyColor getColor() {
        return color;
    }

	@Override
    public String toString() {
        return "Property{" +
                "name='" + this.getName() + '\'' +
                ", price=" + price +
                ", rent=" + rent +
                ", houseCost=" + houseCost +
                ", hotelCost=" + hotelCost +
                ", numberOfHouses=" + numberOfHouses +
                ", numberOfHotels=" + numberOfHotels +
                ", owner=" + (owner != null ? owner.getName() : "None") +
                ", isMortgaged=" + isMortgaged +
                ", isDynamicRent=" + isDynamicRent +
                ", color='" + color + '\'' +
                '}';
    }

}
