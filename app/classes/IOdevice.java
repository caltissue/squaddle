package classes;

import java.util.*;
import java.io.*;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class IOdevice {

	private static final String filepath = "/Users/cal/Desktop/squad-online/squad-online/_db/";
	private static final String postPath = "_posts/post-";
	private static final String postFolderPath = "_posts/"; // TODO - this shit dumb as hell
	private static final String userPath = "_users/user-";
	private static final String userFolderPath = "_users/"; // TODO - this shit dumb as hell

	public static String getUserPath() { return userPath; }
	public static String getUserFolderPath() { return userFolderPath; }

	public static String getPostPath() { return postPath; }
	public static String getPostFolderPath() { return postFolderPath; }

	public static void save(JsonNode jsonNode, String filename) { // make boolean for feedback upon failure
		String fullFilename = filepath + filename;

		try {
			ObjectMapper om = new ObjectMapper();
			String json = om.writeValueAsString(jsonNode);
			FileWriter writer = new FileWriter(fullFilename + ".json");
			writer.write(json);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
	}

	public static JsonNode load(String filename) {
		String fullFilename = filepath + filename + ".json";
		String json = "";
		JsonNode node = Json.parse("{}");

		try {
			FileReader reader = new FileReader(fullFilename);
			int i;
			while ( (i=reader.read()) != -1 ) {
				json += String.valueOf((char)i);
			}
			reader.close();

			ObjectMapper om = new ObjectMapper();
			node = om.readTree(json);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return node;
	}

	public static ArrayList<JsonNode> getAllAsJsonNodes(String path) {
		ArrayList<JsonNode> nodes = new ArrayList<>();

		try {
			File dir = new File(filepath + path);
			File[] files = dir.listFiles();

			Arrays.sort(files);
			Collections.reverse(Arrays.asList(files));
			
			for (File f : files) {
				if (f.getName().contains(".json")) {
					String json = "";
					FileReader reader = new FileReader(filepath + path + f.getName());
					int i;
					while ( (i=reader.read()) != -1) {
						json += String.valueOf((char)i);
					}
					reader.close();

					ObjectMapper om = new ObjectMapper();
					JsonNode node = om.readTree(json);
					nodes.add(node);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception in getAllAsJsonNodes:\n" + e.getMessage());
		}

		return nodes;
	}

	public static ArrayList<String> getEveryFilenameInFolder(String path) {
		ArrayList<String> filenames = new ArrayList<>();
		try {
			File dir = new File(filepath + path); // should these 4 lines be a helper method?
			dir.createNewFile();
			File[] files = dir.listFiles();
			if (files == null) files = new File[] {};

			for (File f : files) {
				String filename = f.getName();
				if (filename.contains("post"))
					filenames.add(filename);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return filenames;
	}

	public static void deleteFile(String filename) {
		String file = filepath + filename + ".json";
		File f = new File(file);
		f.delete();
	}	

	public static JsonNode getNode(String fileDir, String key, String value) {
		try {
			File dir = new File(filepath + fileDir);
			dir.createNewFile();
			File[] files = dir.listFiles();
			if (files == null) files = new File[] {};

			for (File f : files) {
				if (f.getName().contains(".json")) {
					String json = "";
					FileReader reader = new FileReader(filepath + fileDir + f.getName());
					int i;
					while ( (i=reader.read()) != -1) {
						json += String.valueOf((char)i);
					}
					reader.close();

					ObjectMapper om = new ObjectMapper();
					JsonNode node = om.readTree(json);
					Map<String, Object> map = om.convertValue(node, new TypeReference<Map<String, Object>>(){});
					if (map.containsKey(key) && map.get(key) != null && map.get(key).equals(value)) 
							return node;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception in IOdevice.getNode()\n" + e.getMessage());
		}

		return null;
	}

/* TODO
	public static ArrayList<Post> getANumberOfPosts(int number) {
		// getEveryPost but for a given number
		return new ArrayList<Post>();
	}
*/
}