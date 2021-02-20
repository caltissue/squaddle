package classes;

import java.util.*;
import java.io.*;

public class IOdevice {

	private static String filepath = "/Users/cal/Desktop/squad-online/squad-online/";

	public static boolean savePost(HashMap<String, String> data) {
		String filename = filepath + "_posts/p-" + data.get("date") + ".ser";

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

	public static ArrayList<Post> getNumberOfPosts(int number) {
		// getEveryPost but for a given number
		return new ArrayList<Post>();
	}
}