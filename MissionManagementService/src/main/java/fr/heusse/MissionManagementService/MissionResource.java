package fr.heusse.MissionManagementService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/requests")
public class MissionResource {
	@Autowired
	private RestTemplate restTemplate;
	
	@PutMapping(value="/joinRequest/{request}/{volunteer}", produces=MediaType.APPLICATION_JSON_VALUE)
	public String joinRequest(@PathVariable int request, @PathVariable int volunteer) {
		String dbHost = restTemplate.getForObject("http://ConfigService/config/db/host", String.class);
		String dbPort = restTemplate.getForObject("http://ConfigService/config/db/port", String.class);
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/projet_gei_040", "projet_gei_040", "hee9ahHe")) {	
			Statement stmt1 = conn.createStatement();
			ResultSet rs = stmt1.executeQuery("SELECT role FROM users WHERE id = "+volunteer+";");
						
			while (rs.next()) {
				int role = rs.getInt("role");
				if(role == 1) {
					PreparedStatement stmt2 = conn.prepareStatement("UPDATE requests SET volunteer = ? WHERE id = ?;");
				    
					stmt2.setInt(1, volunteer);
					stmt2.setInt(2, request);
				    stmt2.executeUpdate();
				    System.out.println("Volunteer -> updating request");
				} else {
					System.out.println("Not a Volunteer");
				}
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		
		return "200";	
	}
	
	@PutMapping(value="/giveFeedback/{request}/{user}", produces=MediaType.APPLICATION_JSON_VALUE)
	public String giveFeedback(@PathVariable int request, @PathVariable int user, @RequestBody String feedback) {
		String dbHost = restTemplate.getForObject("http://ConfigService/config/db/host", String.class);
		String dbPort = restTemplate.getForObject("http://ConfigService/config/db/port", String.class);
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/projet_gei_040", "projet_gei_040", "hee9ahHe")) {	
			Statement stmt1 = conn.createStatement();
			ResultSet rs = stmt1.executeQuery("SELECT seeker, volunteer FROM requests WHERE seeker = "+user+" OR volunteer = " +user+ ";");
						
			while (rs.next()) {
				int seeker = rs.getInt("seeker");
				int volunteer = rs.getInt("volunteer");
				if(seeker == user) {
					PreparedStatement stmt2 = conn.prepareStatement("UPDATE requests SET fb_seeker = ? WHERE id = ?;");
				    
					stmt2.setString(1, feedback);
					stmt2.setInt(2, request);
				    stmt2.executeUpdate();
				    System.out.println("HelpSeeker -> updating request");
				} else if (volunteer == user) {
					PreparedStatement stmt2 = conn.prepareStatement("UPDATE requests SET fb_volunteer = ? WHERE id = ?;");
				    
					stmt2.setString(1, feedback);
					stmt2.setInt(2, request);
				    stmt2.executeUpdate();
				    System.out.println("Volunteer -> updating request");
				} else {
					return "500";
				}
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		
		return "200";	
	}
}
