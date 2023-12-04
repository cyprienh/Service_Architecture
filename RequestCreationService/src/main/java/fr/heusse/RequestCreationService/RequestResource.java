package fr.heusse.RequestCreationService;

import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/requests")
public class RequestResource {
	@Autowired
	private RestTemplate restTemplate;
	
	@PostMapping(value="/addRequest/{seeker}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Request addRequest(@PathVariable int seeker) {
		String dbHost = restTemplate.getForObject("http://ConfigService/config/db/host", String.class);
		String dbPort = restTemplate.getForObject("http://ConfigService/config/db/port", String.class);
		Request request = new Request(seeker);
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/projet_gei_040", "projet_gei_040", "hee9ahHe")) {	
			Statement stmt1 = conn.createStatement();
			ResultSet rs = stmt1.executeQuery("SELECT role FROM users WHERE id = "+seeker+";");
						
			while (rs.next()) {
				int role = rs.getInt("role");
				if(role == 0) {
					PreparedStatement stmt = conn.prepareStatement("INSERT INTO requests(seeker, status) VALUES (?, 0)", Statement.RETURN_GENERATED_KEYS);
					    
					stmt.setInt(1, seeker);
				    stmt.executeUpdate();
				    System.out.println("HelpSeeker -> creating request");
				} else {
					System.out.println("Not a HelpSeeker");
				}
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		
		return request;	
	}
	
	@PostMapping(value="/offerHelp/{volunteer}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Request offerHelp(@PathVariable int volunteer) {
		String dbHost = restTemplate.getForObject("http://ConfigService/config/db/host", String.class);
		String dbPort = restTemplate.getForObject("http://ConfigService/config/db/port", String.class);
		Request request = new Request(volunteer);
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/projet_gei_040", "projet_gei_040", "hee9ahHe")) {	
			Statement stmt1 = conn.createStatement();
			ResultSet rs = stmt1.executeQuery("SELECT role FROM users WHERE id = "+volunteer+";");
						
			while (rs.next()) {
				int role = rs.getInt("role");
				if(role == 1) {
					PreparedStatement stmt = conn.prepareStatement("INSERT INTO requests(volunteer, status) VALUES (?, 0)", Statement.RETURN_GENERATED_KEYS);
					    
					stmt.setInt(1, volunteer);
				    stmt.executeUpdate();
				    System.out.println("Volunteer -> offering help");
				} else {
					System.out.println("Not a Volunteer");
				}
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		
		return request;	
	}
}
