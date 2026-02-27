package securityapp.model;

public class User {
    private final String uniqueId;
    private final Role role;

    public User(String uniqueId, Role role) {
        this.uniqueId = uniqueId;
        this.role = role;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public Role getRole() {
        return role;
    }
}
