package controllers;

import play.mvc.*;
import java.util.*;
import classes.*;
import java.io.*;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;

public class HomeController extends Controller {

    public Result index(Http.Request request) {
    	Optional<String> idO = request.session().get("user_id");
    	String idString = idO.isPresent() ? idO.get() : "";

    	Optional<String> message = request.session().get("logstate");
    	String msg = message.isPresent() ? message.get() : "";

    	if (idString != null && idString != "") {
    		ArrayList<Post> allPosts = Post.getAll();
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

    	if (User.isValid(userAttempt, pw)) 
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
    	//User.save(user);
    	String[] userInfo = new String[] { user.getEmail(), user.getDisplayName() };
    	return ok(views.html.accountpage.render(userInfo));
    }

    public Result postpost(Http.Request request, String text) {
    	Optional<String> idO = request.session().get("user_id"); 
    	String userId = idO.isPresent() ? idO.get() : "";
    	User user = User.getById(Integer.parseInt(userId));

    	Date date = new Date(System.currentTimeMillis());

    	int id = Post.getNewId(); // TODO: poor concern sep. Controller shouldn't know about post id.

    	Post post = new Post(id, text, date, user);
    	Post.save(post);
    	return redirect("/").addingToSession(request, "logstate", "Success");
    	// TODO: error message (goes with Post.save() as a bool method)
    }

    public Result deletepost(Http.Request request, int id) {
    	Post.deleteById(id); // TODO: catch return boolean
    	return redirect("/");
    }
}
