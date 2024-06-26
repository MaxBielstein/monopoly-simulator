package components;

public class BoardLocation {
	
	private String name; 
    private int position;  // New field to store property location on the board
	
	public BoardLocation(String name, int position) {
		this.name = name;
		this.setPosition(position);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
