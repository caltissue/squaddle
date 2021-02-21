package classes;

import java.util.*;
import java.io.*;

public class IOdevice {

	private static final String filepath = "/Users/cal/Desktop/squad-online/squad-online/";
	private static final String pathToPosts = "_posts/post-";
	private static final String pathToUsers = "_accounts/user-";

	public static String getUserPath() { return pathToUsers; }

	public static boolean savePost(HashMap<String, String> data) { // TODO: this is now redundant
		String filename = filepath + pathToPosts + data.get("id");

		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(data);
			objOut.close();
			fileOut.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}

		return true;
	}

	public static void save(HashMap<String, String> data, String filename) { // TODO: make a boolean method
		String fullFilename = filepath + filename;

		try {
			FileOutputStream fileOut = new FileOutputStream(fullFilename);
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(data);
			objOut.close();
			fileOut.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			//return false;
		}
		//return true;
	}

	public static HashMap<String, String> load(String filename) {
		String fullFilename = filepath + filename;
		HashMap<String, String> data = new HashMap<>();

		try {
			FileInputStream fileIn = new FileInputStream(fullFilename);
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			data = (HashMap) objIn.readObject();
			objIn.close();
			fileIn.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return data;
	}


	public static Post getPost(String filename) {
		String fullname = filepath + "_posts/" + filename;
		Post p = new Post();

		try {
			FileInputStream fileIn = new FileInputStream(fullname);
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			HashMap<String, String> data = (HashMap) objIn.readObject();

			objIn.close();
			fileIn.close();

			p = new Post(data);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return p;
	}

	public static ArrayList<Post> getEveryPost() {
		ArrayList<Post> posts = new ArrayList<>();

		try {
			File dir = new File(filepath + "_posts");
			dir.createNewFile();
			File[] files = dir.listFiles();
			if (files == null) files = new File[] {};

			Arrays.sort(files);
			Collections.reverse(Arrays.asList(files));

			for (File f : files) {
				String filename = f.getName();
				Post p = getPost(filename);
				if (p.getText() == "" && p.getDate() == null && p.getUser() == null) {
					// TODO - discard with error log
				}
				else {
					posts.add(p);
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return posts;
	}

	public static ArrayList<String> getEveryPostFilename() {
		ArrayList<String> filenames = new ArrayList<>();

		try {
			File dir = new File(filepath + "_posts"); // should these 4 lines be a helper method?
			dir.createNewFile();
			File[] files = dir.listFiles();
			if (files == null) files = new File[] {};

			for (File f : files) {
				String filename = f.getName();
				filenames.add(filename);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return filenames;
	}

	public static void deletePostFile(int id) {
		String filename = filepath + pathToPosts + new Integer(id).toString();
		File f = new File(filename);
		f.delete();
	}

	public static ArrayList<Post> getANumberOfPosts(int number) {
		// getEveryPost but for a given number
		return new ArrayList<Post>();
	}
}