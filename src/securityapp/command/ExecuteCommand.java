package securityapp.command;

import securityapp.capability.Capability;
import securityapp.capability.Execute;
import securityapp.model.User;
import securityapp.model.Resource;
import securityapp.policy.SecurityProxy;

public class ExecuteCommand implements AccessCommand {
    private final SecurityProxy proxy;
    private final User user;
    private final Resource resource;

    public ExecuteCommand(SecurityProxy proxy, User user, Resource resource) {
        this.proxy = proxy;
        this.user = user;
        this.resource = resource;
    }

    public Capability<Execute> execute() {
        return proxy.requestExecute(user, resource);
    }

    public String getDescription() {
        return user.getRole() + " EXECUTE " + resource.getName();
    }
}