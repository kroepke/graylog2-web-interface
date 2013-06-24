package controllers;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import be.objectify.deadbolt.java.utils.PluginUtils;
import be.objectify.deadbolt.java.utils.RequestUtils;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Controller;

import static security.Roles.USER;

@SubjectPresent
@Restrict({@Group(USER)})
public class AuthenticatedController extends Controller {
	public static final Logger log = LoggerFactory.getLogger(AuthenticatedController.class);

	protected static User currentUser() {
		User user = null;
		try {
			// yuck.
			user = (User) RequestUtils.getSubject(PluginUtils.getDeadboltHandler(), ctx());
		} catch (Exception e) {
			log.error("Authorization system not initialized. Cannot continue.", e);
		}
		return user;
	}
}
