package fr.heusse.UserManagementService;

import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/users")
public class UserResource {
	@Autowired
	private RestTemplate restTemplate;
	
	@PostMapping(value="/manageHelpSeeker/{name}", produces=MediaType.APPLICATION_JSON_VALUE)
	public User manageHelpSeeker(@PathVariable String name) {
		String dbHost = restTemplate.getForObject("http://ConfigService/config/db/host", String.class);
		String dbPort = restTemplate.getForObject("http://ConfigService/config/db/port", String.class);
		HelpSeeker user = new HelpSeeker(name);
		String sql = "INSERT INTO users(name, role) VALUES (?, 0)";
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/projet_gei_040", "projet_gei_040", "hee9ahHe");
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		    
			stmt.setString(1, name);
		    stmt.executeUpdate();
	        
	        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                user.setID(generatedKeys.getInt(1));
	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		
		return user;	
	}
	
	@PostMapping(value="/manageVolunteer/{name}", produces=MediaType.APPLICATION_JSON_VALUE)
	public User manageVolunteer(@PathVariable String name) {
		String dbHost = restTemplate.getForObject("http://ConfigService/config/db/host", String.class);
		String dbPort = restTemplate.getForObject("http://ConfigService/config/db/port", String.class);
		Volunteer user = new Volunteer(name);
		String sql = "INSERT INTO users(name, role) VALUES (?, 1)";
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/projet_gei_040", "projet_gei_040", "hee9ahHe");
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		    
			stmt.setString(1, name);
		    stmt.executeUpdate();
	        
	        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                user.setID(generatedKeys.getInt(1));
	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		
		return user;	
	}
	
	@PostMapping(value="/manageAdmin/{name}", produces=MediaType.APPLICATION_JSON_VALUE)
	public User manageAdmin(@PathVariable String name) {
		String dbHost = restTemplate.getForObject("http://ConfigService/config/db/host", String.class);
		String dbPort = restTemplate.getForObject("http://ConfigService/config/db/port", String.class);
		Admin user = new Admin(name);
		String sql = "INSERT INTO users(name, role) VALUES (?, 2)";
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+dbPort+"/projet_gei_040", "projet_gei_040", "hee9ahHe");
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		    
			stmt.setString(1, name);
		    stmt.executeUpdate();
	        
	        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                user.setID(generatedKeys.getInt(1));
	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		
		return user;	
	}
}
