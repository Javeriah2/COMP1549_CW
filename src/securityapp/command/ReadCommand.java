package securityapp.command;

import securityapp.capability.Capability;
import securityapp.capability.Read;
import securityapp.model.User;
import securityapp.model.Resource;
import securityapp.policy.SecurityProxy;

public class ReadCommand implements AccessCommand {
    private final SecurityProxy proxy;
    private final User user;
    private final Resource resource;

    public ReadCommand(SecurityProxy proxy, User user, Resource resource) {
        this.proxy = proxy;
        this.user = user;
        this.resource = resource;
    }

    public Capability<Read> execute() {
        return proxy.requestRead(user, resource);
    }

    public String getDescription() {
        return user.getRole() + " READ " + resource.getName();
    }
}