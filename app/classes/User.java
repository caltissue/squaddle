package classes;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
	private int id;
	private String email;
	private String password;
	private String displayName;

	private User(HashMap<String, String> data) {
		this(Integer.parseInt(data.get("id")), data.get("email"), data.get("password"), data.get("displayName"));
	}

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

	public String[] getStringArray() { 
		return new String[] {email, displayName}; 
	}

	public static User getByEmail(String email) { // TODO: use IOdevice to fetch by file (...we beg for normalization now)
		ArrayList<String[]> users = new ArrayList<>();

		users.add(new String[] {"1", "caltissue@gmail.com", "x", "calalzy"});
		users.add(new String[] {"2", "zaktissue@gmail.com", "z", "klex"});

		User u = new User(0, "", "", "");

		for (String[] user : users) {
			if (user[1].equals(email)) 
				u = new User(Integer.parseInt(user[0]), user[1], user[2], user[3]);
		}

		return u;
	}

	public static User getById(int id) {
		String path = IOdevice.getUserPath();
		HashMap<String, String> data = IOdevice.load(path + id);
		User user = new User(data);
		return user;
	}

	public HashMap<String, String> getSaveObject() {
		HashMap<String, String> data = new HashMap<>();
		data.put("id", new Integer(id).toString());
		data.put("email", email);
		data.put("password", password);
		data.put("displayName", displayName);

		return data;
	}

	public static void save(User user) {
		HashMap<String, String> data = user.getSaveObject();
		String path = IOdevice.getUserPath();
		IOdevice.save(data, path + user.getId());
	}

	// public static User getById(int id) { }
}