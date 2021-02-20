package controllers;

import play.mvc.*;
import java.util.Optional;
import java.util.Date;
import java.util.HashMap;
import classes.*;

public class HomeController extends Controller {

    public Result index(Http.Request request) {
    	Optional<String> emailOpt = request.session().get("email");
    	String email = emailOpt.isPresent() ? emailOpt.get() : ""; 

    	if (email.length() > 0) 
    		// get all posts & pass as context
    		return ok(views.html.index.render(email,""));
    	else
    		return ok(views.html.login.render(""));
    }

    public Result login(Http.Request request) {
    	Optional<String> messageOpt = request.session().get("message");
    	String message = messageOpt.isPresent() ? messageOpt.get() : "";
    	return ok(views.html.login.render(message));
    }

    public Result loggingin(Http.Request request, String email, String pw) {
    	User userAttempt = User.getByEmail(email);

    	if (userAttempt.getId() != 0 && pw.equals(userAttempt.getPassword())) 
    		return redirect("/home").addingToSession(request, "email", email);
    	else 
    		return redirect("/login").addingToSession(request, "message", "bad login attempt");
    }

    public Result home(Http.Request request) {
    	Optional<String> email = request.session().get("email");
    	String nameString = email.isPresent() ? email.get() : ""; 
    	return ok(views.html.index.render(nameString, "logout"));
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
    	// serialize it to file

    	return redirect("/");
    }
}
