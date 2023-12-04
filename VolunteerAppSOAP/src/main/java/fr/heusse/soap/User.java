package fr.heusse.soap;

public class User {
	static int current = 0;
	
	private String name;
	private int id;
	
	public User(String name) {
		this.name = name;
		this.id = current++;
	}
	
	public String getName() {
		return name;
	}

	public int getID() {
		return id;
	}
}
