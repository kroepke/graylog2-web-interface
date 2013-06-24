package models;

import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import security.Roles;

import java.util.List;

public class User implements Subject {
	private static final Logger log = LoggerFactory.getLogger(User.class);

	private final String name;
	private final String email;

	private List<Roles.Role> roles = Lists.newArrayList(Roles.USER_ROLE);

	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public static User load(String userId) {
		User user = null;
		if ("admin".equals(userId)) {
			user = new User(userId, "admin@torch.sh");
			user.roles.add(Roles.ADMIN_ROLE);
		} else if ("lennart".equals(userId)) {
			user = new User(userId, "lennart@torch.sh");
		}
		log.info("Loaded user {} for name {}", user, userId);
		return user;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getFullName() {
		return "Lennart Koopmann";
	}

	@Override
	public List<? extends Role> getRoles() {
		return roles;
	}

	@Override
	public List<? extends Permission> getPermissions() {
		return null;
	}

	@Override
	public String getIdentifier() {
		return getName();
	}
}
