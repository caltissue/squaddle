package controllers;

import play.mvc.*;
import java.util.*;
import classes.*;
import java.io.*;

public class HomeController extends Controller {

    public Result index(Http.Request request) {
    	Optional<String> emailOpt = request.session().get("email");
    	String email = emailOpt.isPresent() ? emailOpt.get() : ""; 

    	Optional<String> message = request.session().get("logstate");
    	String msg = message.isPresent() ? message.get() : "";

    	if (email.length() > 0) {
    		ArrayList<Post> allPosts = IOdevice.getEveryPost();

    		return ok(views.html.index.render(email, msg, allPosts))
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
    		return redirect("/home").addingToSession(request, "email", email);
    	else 
    		return redirect("/login").addingToSession(request, "message", "bad login attempt");
    }

    public Result home(Http.Request request) {
    	return redirect("/");
    }

    public Result logout() {
    	return redirect("/").withNewSession();
    }

    public Result account(Http.Request request) {
    	Optional<String> emailOpt = request.session().get("email");
    	String email = emailOpt.isPresent() ? emailOpt.get() : "";
		// TODO: if no email, problem occured

    	User user = User.getByEmail(email);
    	String[] userInfo = user.getStringArray();
    	return ok(views.html.accountpage.render(userInfo));
    }

    public Result postpost(Http.Request request, String text) {
    	Optional<String> emailOpt = request.session().get("email");
    	String email = emailOpt.isPresent() ? emailOpt.get() : "";
    	User user = User.getByEmail(email);

    	Date date = new Date(System.currentTimeMillis());

    	Post post = new Post(text, date, user);
    	HashMap<String, String> saveObject = post.getSaveObject();
    	
    	boolean objectSaved = IOdevice.savePost(saveObject);

    	if(objectSaved)
			return redirect("/").addingToSession(request, "logstate", "Success");
		else
			return redirect("/").addingToSession(request, "logstate", "Sorry, something went wrong");
    }
}
