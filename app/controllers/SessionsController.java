package controllers;

import be.objectify.deadbolt.java.actions.SubjectNotPresent;
import models.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import security.Authenticator;

import static play.data.Form.form;
import static security.AuthHandler.REDIRECT_TO_SLASH;

public class SessionsController extends Controller {
	private static final Logger log = LoggerFactory.getLogger(SessionsController.class);

	final static Form<LoginRequest> userForm = form(LoginRequest.class);

	@SubjectNotPresent(content = REDIRECT_TO_SLASH)
	public static Result index() {
		return ok(views.html.sessions.login.render(userForm));
	}
	
	public static Result create() {
		Form<LoginRequest> loginRequest = userForm.bindFromRequest();
		if (loginRequest.hasErrors()) {
			flash("error", "Please fill out all fields.");
			return redirect("/login");
		}
		
		LoginRequest r = loginRequest.get();
		Authenticator auth = new Authenticator();
		if (auth.authenticate(r.username, r.password)) {
			session("username", r.username);
			return redirect("/");
		} else {
			flash("error", "Wrong username or password.");
			return redirect("/login");
		}
	}

	public static Result destroy() {
		session().clear();
		return redirect("/login");
	}
	
}
