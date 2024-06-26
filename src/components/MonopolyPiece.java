package components;

public enum MonopolyPiece {
    DOG("Dog"),
    CAR("Car"),
    HAT("Hat"),
    SHIP("Ship"),
    THIMBLE("Thimble"),
    BOOT("Boot"),
    WHEELBARROW("Wheelbarrow"),
    CAT("Cat");

    private final String pieceName;

    MonopolyPiece(String pieceName) {
        this.pieceName = pieceName;
    }

    public String getPieceName() {
        return pieceName;
    }

    @Override
    public String toString() {
        return pieceName;
    }
}