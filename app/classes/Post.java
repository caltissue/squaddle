package classes;

import java.util.*;

public class Post {
	private int id;
	private String text;
	private Date date;
	private User user;

	public Post() {
		id = 0;
		text = "";
		date = null;
		user = null;
	}

	public Post(int id, String text, Date date, User user) {
		this.id = id;
		this.text = text;
		this.date = date;
		this.user = user;
	}

	public Post(HashMap<String, String> data) {
		this(Integer.parseInt(data.get("id")), data.get("text"), Long.parseLong(data.get("date")), data.get("user"));
	}

	public Post(int id, String text, long timeMillis, String userEmail) {
		this.id = id;
		this.text = text;
		this.date = new Date(timeMillis);
		this.user = user.getByEmail(userEmail);
	}

	public int getId() { return id; }
	public String getText() { return text; }
	public Date getDate() { return date; }
	public User getUser() { return user; }

	public HashMap<String, String> getSaveObject() {
		HashMap<String, String> data = new HashMap<>();
		data.put("id", new Integer(id).toString());
		data.put("text", text);
		data.put("date", new Long(date.getTime()).toString());
		data.put("user", user.getEmail());

		return data;
	}

	public static void deleteById(int id) {
		IOdevice.deletePostFile(id);
	}

	public static int getNewId() {
		ArrayList<String> filenames = IOdevice.getEveryPostFilename();
		if (filenames.size() == 0) return 1;

		Collections.sort(filenames);
		Collections.reverse(filenames);
		String latestFilename = filenames.get(0);
		int latestId = Integer.parseInt(latestFilename.substring("post-".length()));

		return latestId + 1;
	}
}