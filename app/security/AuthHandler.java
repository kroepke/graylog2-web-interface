package security;

import be.objectify.deadbolt.core.models.Subject;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Http;
import play.mvc.Result;

import static play.mvc.Results.redirect;

public class AuthHandler implements DeadboltHandler {
	public static final Logger log = LoggerFactory.getLogger(AuthHandler.class);

	public static final String REDIRECT_TO_SLASH = "redirectToSlash";

	@Override
	public Result beforeAuthCheck(Http.Context context) {
		log.debug("beforeAuthCheck context {}", context);
		return null;
	}

	@Override
	public Subject getSubject(Http.Context context) {
		final String username = context.session().get("username");
		if (username != null && !username.isEmpty()) {
			log.debug("getSubject for username {}", username);
			return User.load(username);
		}
		return null;
	}

	@Override
	public Result onAuthFailure(Http.Context context, String content) {
		log.debug("Authentication failure, redirecting to Sessions#index. Content {}", content);
		if (content != null) {
			if (REDIRECT_TO_SLASH.equals(content)) {
				return redirect("/");
			}
		}
		return redirect(controllers.routes.SessionsController.index());
	}

	@Override
	public DynamicResourceHandler getDynamicResourceHandler(Http.Context context) {
		log.debug("getDynamicResourceHandler context {}", context);

		return null;
	}
}
