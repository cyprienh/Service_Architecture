package fr.heusse.RequestManagementService;

import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/requests")
public class RequestManagementResource {
	@Autowired
	private RestTemplate restTemplate;
	
	@PutMapping(value="/validateRequest/{admin}/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public String validateRequest(@PathVariable int admin, @PathVariable int id) {
		String dbHost = restTemplate.getForObject("http://ConfigService/config/db/host", String.class);
		String dbPort = restTemplate.getForObject("http://ConfigService/config/db/port", String.class);
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/projet_gei_040", "projet_gei_040", "hee9ahHe")) {	
			Statement stmt1 = conn.createStatement();
			ResultSet rs = stmt1.executeQuery("SELECT role FROM users WHERE id = "+admin+";");
						
			while (rs.next()) {
				int role = rs.getInt("role");
				if(role == 2) {
					PreparedStatement stmt2 = conn.prepareStatement("UPDATE requests SET status = 1 WHERE id = ?;");
				    
					stmt2.setInt(1, id);
				    stmt2.executeUpdate();
				    System.out.println("Admin -> updating request");
				} else {
					System.out.println("Not an Admin");
				}
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	return "500";
	    }
		
		return "200";	
	}
	
	@PutMapping(value="/rejectRequest/{admin}/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public String rejectRequest(@PathVariable int admin, @PathVariable int id) {
		String dbHost = restTemplate.getForObject("http://ConfigService/config/db/host", String.class);
		String dbPort = restTemplate.getForObject("http://ConfigService/config/db/port", String.class);	
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/projet_gei_040", "projet_gei_040", "hee9ahHe")) {	
			Statement stmt1 = conn.createStatement();
			ResultSet rs = stmt1.executeQuery("SELECT role FROM users WHERE id = "+admin+";");
						
			while (rs.next()) {
				int role = rs.getInt("role");
				if(role == 2) {
					PreparedStatement stmt2 = conn.prepareStatement("UPDATE requests SET status = 2 WHERE id = ?;");
				    
					stmt2.setInt(1, id);
				    stmt2.executeUpdate();
				    System.out.println("Admin -> updating request");
				} else {
					System.out.println("Not an Admin");
				}
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	return "500";
	    }
		
		return "200";
	}
	
	@PutMapping(value="/updateRequestStatus/{admin}/{id}/{status}", produces=MediaType.APPLICATION_JSON_VALUE)
	public String updateRequestStatus(@PathVariable int admin, @PathVariable int id, @PathVariable int status) {
		String dbHost = restTemplate.getForObject("http://ConfigService/config/db/host", String.class);
		String dbPort = restTemplate.getForObject("http://ConfigService/config/db/port", String.class);
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/projet_gei_040", "projet_gei_040", "hee9ahHe")) {	
			Statement stmt1 = conn.createStatement();
			ResultSet rs = stmt1.executeQuery("SELECT role FROM users WHERE id = "+admin+";");
						
			while (rs.next()) {
				int role = rs.getInt("role");
				if(role == 2) {
					PreparedStatement stmt2 = conn.prepareStatement("UPDATE requests SET status = ? WHERE id = ?;");
				    
					stmt2.setInt(1, status);
					stmt2.setInt(2, id);
				    stmt2.executeUpdate();
				    System.out.println("Admin -> updating request");
				} else {
					System.out.println("Not an Admin");
				}
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	return "500";
	    }
		
		return "200";	
	}
	
}
