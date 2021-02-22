package classes;

import java.util.*;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Post {
	private int id;
	private String text;
	private Date date;
	private User user;

	public Post(int id, String text, Date date, User user) {
		this.id = id;
		this.text = text;
		this.date = date;
		this.user = user;
	}

	public Post() {
		id = 0;
		text = "";
		date = null;
		user = null;
	}

	public int getId() { return id; }
	public String getText() { return text; }
	public Date getDate() { return date; }
	public User getUser() { return user; }

	public static void save(Post post) { // TODO: make boolean
		JsonNode postJson = Json.toJson(post);
		String path = IOdevice.getPostPath(); // TODO: poor separation of concerns every time I do this
		IOdevice.save(postJson, path + post.getId());
	}

	public static Post getById(int id) {
		String path = IOdevice.getPostPath();
		JsonNode postJson = IOdevice.load(path + id);
		Post post = Json.fromJson(postJson, Post.class);
		return post;
	}

	public static ArrayList<Post> getAll() {
		ArrayList<Post> posts = new ArrayList<>();
		String path = IOdevice.getPostFolderPath();
		ArrayList<JsonNode> nodes = IOdevice.getAllAsJsonNodes(path);
		for (JsonNode node : nodes) {
			Post p = Json.fromJson(node, Post.class);
			posts.add(p);
		}
		return posts;
	}

	public static void deleteById(int id) { // TODO: make boolean
		String path = IOdevice.getPostPath();
		IOdevice.deleteFile(path + id);
	}

	public static int getNewId() { // this method will cause a bug when multiple ppl post at once
		String path = IOdevice.getPostFolderPath();
		ArrayList<String> filenames = IOdevice.getEveryFilenameInFolder(path);
		int i = 0;
		for (String name : filenames) {
			String noExt = name.substring(0, name.indexOf("."));
			String intOnly = noExt.substring(name.indexOf("-") + 1);
			int postId = Integer.parseInt(intOnly);
			if (postId > i) i = postId;
		}
		return 1 + i;
	}
}