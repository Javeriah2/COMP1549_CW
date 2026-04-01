package securityapp.command;

import securityapp.capability.Capability;
import securityapp.capability.Write;
import securityapp.model.User;
import securityapp.model.Resource;
import securityapp.policy.SecurityProxy;

public class WriteCommand implements AccessCommand {
    private final SecurityProxy proxy;
    private final User user;
    private final Resource resource;

    public WriteCommand(SecurityProxy proxy, User user, Resource resource) {
        this.proxy = proxy;
        this.user = user;
        this.resource = resource;
    }

    public Capability<Write> execute() {
        return proxy.requestWrite(user, resource);
    }

    public String getDescription() {
        return user.getRole() + " WRITE " + resource.getName();
    }
}