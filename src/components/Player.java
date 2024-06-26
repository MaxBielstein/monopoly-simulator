package components;

import java.util.ArrayList;
import java.util.List;

import actives.DecisionMaker;

public class Player {
    private String name;
    private MonopolyPiece piece;
    private int position;
    private int balance;
    private boolean isInJail;
    private List<Property> properties;
    private DecisionMaker decisionMaker;

    public Player(String name, MonopolyPiece piece, DecisionMaker decisionMaker) {
        this.name = name;
        this.piece = piece;
        this.position = 0; // Start position
        this.balance = 1500; // Starting balance
        this.isInJail = false; // Initially not in jail
        this.properties = new ArrayList<>();
        this.decisionMaker = decisionMaker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MonopolyPiece getPiece() {
        return piece;
    }

    public void setPiece(MonopolyPiece piece) {
        this.piece = piece;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isInJail() {
        return isInJail;
    }

    public void setInJail(boolean inJail) {
        isInJail = inJail;
    }

    public void move(int steps) {
        this.position = (this.position + steps) % 40; // Assuming standard Monopoly board with 40 spaces
    }

    public void updateBalance(int amount) {
        this.balance += amount;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void addProperty(Property property) {
        this.properties.add(property);
    }
    
    public DecisionMaker getDecisionMaker() {
    	return decisionMaker;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", piece=" + piece +
                ", position=" + position +
                ", balance=" + balance +
                ", isInJail=" + isInJail +
                '}';
    }
}

