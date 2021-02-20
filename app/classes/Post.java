package classes;

import java.util.Date;
import java.util.HashMap;

public class Post {
	private String text;
	private Date date;
	private User user;

	public Post() {
		text = "";
		date = null;
		user = null;
	}

	public Post(String text, Date date, User user) {
		this.text = text;
		this.date = date;
		this.user = user;
	}

	public Post(HashMap<String, String> data) {
		this(data.get("text"), Long.parseLong(data.get("date")), data.get("user"));
	}

	public Post(String text, long timeMillis, String userEmail) {
		this.text = text;
		this.date = new Date(timeMillis);
		this.user = user.getByEmail(userEmail);
	}

	public String getText() { return text; }
	public Date getDate() { return date; }
	public User getUser() { return user; }

	public HashMap<String, String> getSaveObject() {
		HashMap<String, String> data = new HashMap<>();
		data.put("text", text);
		data.put("date", new Long(date.getTime()).toString());
		data.put("user", user.getEmail());

		return data;
	}
}