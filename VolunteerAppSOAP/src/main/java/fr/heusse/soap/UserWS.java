package fr.heusse.soap;

import javax.jws.*;

@WebService(serviceName="user")
public class UserWS {
	@WebMethod(operationName="manageHelpSeeker")
	public int manageHelpSeeker(String name) {
		HelpSeeker user = new HelpSeeker(name);
		System.out.println("Added help seeker with ID: "+user.getID());
		return user.getID();
	}
	
	@WebMethod(operationName="manageVolunteer")
	public int manageVolunteer(String name) {
		Volunteer user = new Volunteer(name);
		System.out.println("Added volunteer with ID: "+user.getID());
		return user.getID();
	}
	
	@WebMethod(operationName="manageAdmin")
	public int manageAdmin(String name) {
		Admin user = new Admin(name);
		System.out.println("Added admin with ID: "+user.getID());
		return user.getID();
	}
}
