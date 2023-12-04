package fr.heusse.UserManagementService;

public class User {
	static int current = 0;
	
	private String name;
	private int id;
	
	public User(String name) {
		this.name = name;
		this.id = current++;
	}
	
	public User() {
		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	public int getID() {
		return id;
	}
}
