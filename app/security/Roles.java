package security;

public class Roles {

	public static final String ADMIN = "admin";
	public static final Role ADMIN_ROLE = new Role(ADMIN);

	public static final String USER = "user";
	public static final Role USER_ROLE = new Role(USER);

	public static class Role implements be.objectify.deadbolt.core.models.Role {
		private final String name;

		public Role(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}
}
