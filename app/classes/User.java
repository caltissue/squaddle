package classes;

import java.util.ArrayList;

public class User {
	private int id;
	private String email;
	private String password;

	public User(int id, String email, String password) {
		this.id = id;
		this.email = email;
		this.password = password;
	}

	public int getId() { return id; }
	public String getEmail() { return email; }
	public String getPassword() { return password; }

	public String[] getStringArray() { 
		return new String[] {Integer.toString(id), email, password}; 
	}

	public static User getByEmail(String email) {
		ArrayList<String[]> users = new ArrayList<>();
		users.add(new String[] {"1", "caltissue@gmail.com", "x"});

		User u = new User(0, "", "");

		for (String[] user : users) {
			if (user[1].equals(email)) 
				u = new User(Integer.parseInt(user[0]), user[1], user[2]);
		}

		return u;
	}

	// public static User getById(int id) { }
}