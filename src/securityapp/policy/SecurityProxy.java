package securityapp.policy;

import java.security.PublicKey;
import securityapp.model.User;
import securityapp.model.Resource;
import securityapp.model.AccessScope;
import securityapp.capability.Capability;
import securityapp.capability.Read;
import securityapp.capability.Write;

public class SecurityProxy {

    public Capability<Read> requestRead(User user, Resource resource) {

        if (evaluateReadPolicy(user, resource)) {
            logAttempt(user, resource, "READ", "ALLOW");
            return new Capability<Read>(resource);
        } else {
            logAttempt(user, resource, "READ", "REFUSE");
            throw new SecurityException(
                    "Access Denied " + user.getRole() + " cannot read " + resource.getScope() + " resources");
        }
    }

    public Capability<Write> requestWrite(User user, Resource resource) {
        if (evaluateWritePolicy(user, resource)) {
            logAttempt(user, resource, "WRITE", "ALLOW");
            return new Capability<Write>(resource);
        } else {
            logAttempt(user, resource, "WRITE", "REFUSE");
            throw new SecurityException(
                    "Access Denied " + user.getRole() + " cannot write to " + resource.getScope() + " resources");
        }
    }

    private boolean evaluateReadPolicy(User user, Resource resource) {
        if (resource.getScope() == AccessScope.PUBLIC) {
            return true;
        } else {
            return false;
        }
    }

    private boolean evaluateWritePolicy(User user, Resource resource) {
        return false;
    }

    private void logAttempt(User user, Resource resource, String action, String result) {
        System.out.println(java.time.LocalDateTime.now() + ", " +
                user.getUniqueId() + ", " +
                user.getRole() + ", " +
                resource.getName() + ", " +
                action + ", " + result);
    }
}
