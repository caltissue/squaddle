package classes;

import java.util.ArrayList;
import java.util.HashMap;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class User {
	private int id;
	private String email;
	private String password;
	private String displayName;

	public User(int id, String email, String password, String displayName) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.displayName = displayName;
	}

	public int getId() { return id; }
	public String getEmail() { return email; }
	public String getPassword() { return password; }
	public String getDisplayName() { return displayName; }	

	public static boolean isValid(User user, String password) {
		return (	user != null && 
					user.getId() != 0 && 
					password.equals(user.getPassword())
				);
	}

	public static void save(User user) { // TODO: make boolean for feedback on fail
		JsonNode userJson = Json.toJson(user);
		String path = IOdevice.getUserPath(); // TODO: poor separation of concerns here
		IOdevice.save(userJson, path + user.getId());
	}

	public static User getByEmail(String email) {
		String path = IOdevice.getUserFolderPath();
		JsonNode userNode = IOdevice.getNode(path, "email", email);
		User user = Json.fromJson(userNode, User.class);
		return user;
	}

	public static User getById(int id) {
		String path = IOdevice.getUserPath();
		JsonNode node = IOdevice.load(path + id);
		User user = Json.fromJson(node, User.class);
		return user;
	}
}
