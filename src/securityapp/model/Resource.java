package securityapp.model;

public class Resource {
    private final String name;
    private final AccessScope scope;

    public Resource(String name, AccessScope scope) {
        this.name = name;
        this.scope = scope;
    }

    public String getName() {
        return name;
    }

    public AccessScope getScope() {
        return scope;
    }

}