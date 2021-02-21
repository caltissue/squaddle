package controllers;

import play.mvc.*;
import java.util.*;
import classes.*;
import java.io.*;

public class HomeController extends Controller {

    public Result index(Http.Request request) {
    	Optional<String> idO = request.session().get("user_id");
    	String idString = idO.isPresent() ? idO.get() : "";

    	Optional<String> message = request.session().get("logstate");
    	String msg = message.isPresent() ? message.get() : "";

    	if (idString != null && idString != "") {
    		ArrayList<Post> allPosts = IOdevice.getEveryPost();
    		User user = User.getById(Integer.parseInt(idString));

    		return ok(views.html.index.render(user.getDisplayName(), msg, allPosts))
    			.removingFromSession(request, "logstate");
    	}
    	else
    		return ok(views.html.login.render(""));
    }

    public Result login(Http.Request request) {
    	Optional<String> messageOpt = request.session().get("message");
    	String message = messageOpt.isPresent() ? messageOpt.get() : "";
    	return ok(views.html.login.render(message)).removingFromSession(request, "message");
    }

    public Result loggingin(Http.Request request, String email, String pw) {
    	User userAttempt = User.getByEmail(email);

    	if (userAttempt.getId() != 0 && pw.equals(userAttempt.getPassword())) 
    		return redirect("/").addingToSession(request, "user_id", new Integer(userAttempt.getId()).toString());
    	else 
    		return redirect("/login").addingToSession(request, "message", "bad login attempt");
    }

    public Result logout() {
    	return redirect("/").withNewSession();
    }

    public Result account(Http.Request request) {
    	Optional<String> idO = request.session().get("user_id"); // TODO: if no id, problem occured
    	String id = idO.isPresent() ? idO.get() : "";
    	User user = User.getById(Integer.parseInt(id));
    	User.save(user);
    	String[] userInfo = user.getStringArray();
    	return ok(views.html.accountpage.render(userInfo));
    }

    public Result postpost(Http.Request request, String text) {
    	Optional<String> idO = request.session().get("user_id"); 
    	String userId = idO.isPresent() ? idO.get() : "";
    	User user = User.getById(Integer.parseInt(userId));

    	Date date = new Date(System.currentTimeMillis());

    	int id = Post.getNewId();

    	Post post = new Post(id, text, date, user);
    	HashMap<String, String> saveObject = post.getSaveObject();
    	
    	boolean objectSaved = IOdevice.savePost(saveObject);

    	if(objectSaved)
			return redirect("/").addingToSession(request, "logstate", "Success");
		else
			return redirect("/").addingToSession(request, "logstate", "Sorry, something went wrong");
    }

    public Result deletepost(Http.Request request, int id) {
    	Post.deleteById(id);
    	return redirect("/");
    }
}
