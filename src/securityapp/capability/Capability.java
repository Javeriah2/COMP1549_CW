package securityapp.capability;

import securityapp.model.Resource;

public class Capability<T> {
    private final Resource resource;

    public Capability(Resource resource) {
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }

}