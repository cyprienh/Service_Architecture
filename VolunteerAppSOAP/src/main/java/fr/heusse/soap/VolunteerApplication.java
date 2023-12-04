package fr.heusse.soap;

import java.net.MalformedURLException;
import javax.xml.ws.Endpoint;

public class VolunteerApplication {
	
	public static String host = "localhost";
	public static short port = 8080;
	
	public void demarrerService() {
		String url = "http://"+host+":"+port+"/";
		Endpoint.publish(url, new UserWS());
	}
	
	public static void main(String[] args) throws MalformedURLException {
		new VolunteerApplication().demarrerService();
		System.out.println("Service started.");
	}
}
