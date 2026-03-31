package securityapp.policy;

import securityapp.model.User;
import securityapp.model.Resource;
import securityapp.model.AccessScope;
import securityapp.capability.Capability;
import securityapp.capability.Execute;
import securityapp.capability.Read;
import securityapp.capability.Write;
import securityapp.model.Role;

public class SecurityProxy implements ResourceAccessor {

    private final ResourceAccessor realAccessor = new DirectResourceAccessor();

    public Capability<Read> requestRead(User user, Resource resource) {

        if (evaluateReadPolicy(user, resource)) {
            logAttempt(user, resource, "READ", "ALLOW");
            return realAccessor.requestRead(user, resource);
        } else {
            logAttempt(user, resource, "READ", "REFUSE");
            throw new SecurityException(
                    "Access Denied " + user.getRole() + " cannot read " + resource.getScope() + " resources");
        }
    }

    public Capability<Write> requestWrite(User user, Resource resource) {
        if (evaluateWritePolicy(user, resource)) {
            logAttempt(user, resource, "WRITE", "ALLOW");
            return realAccessor.requestWrite(user, resource);
        } else {
            logAttempt(user, resource, "WRITE", "REFUSE");
            throw new SecurityException(
                    "Access Denied " + user.getRole() + " cannot write to " + resource.getScope() + " resources");
        }
    }

    public Capability<Execute> requestExecute(User user, Resource resource) {
        if (evaluateExecutePolicy(user, resource)) {
            logAttempt(user, resource, "EXECUTE", "ALLOW");
            return realAccessor.requestExecute(user, resource);
        } else {
            logAttempt(user, resource, "EXECUTE", "REFUSE");
            throw new SecurityException(
                    "Access Denied: " + user.getRole() + " cannot execute " + resource.getScope() + " resources");
        }
    }

    private boolean evaluateReadPolicy(User user, Resource resource) {
        Role role = user.getRole();
        AccessScope scope = resource.getScope();

        return switch (scope) {
            case PUBLIC -> true;
            case INTERNAL -> role == Role.STUDENT || role == Role.STAFF || role == Role.ADMIN;
            case CONFIDENTIAL -> role == Role.STAFF || role == Role.ADMIN;
            default -> false;
        };
    }

    private boolean evaluateWritePolicy(User user, Resource resource) {
        Role role = user.getRole();
        AccessScope scope = resource.getScope();

        return switch (scope) {
            case PUBLIC -> role == Role.ADMIN;
            case INTERNAL -> role == Role.STAFF || role == Role.ADMIN;
            case CONFIDENTIAL -> role == Role.ADMIN;
            default -> false;
        };
    }

    private boolean evaluateExecutePolicy(User user, Resource resource) {
        Role role = user.getRole();
        AccessScope scope = resource.getScope();

        return switch (scope) {
            case PUBLIC -> role == Role.STUDENT || role == Role.STAFF || role == Role.ADMIN;
            case INTERNAL -> role == Role.STAFF || role == Role.ADMIN;
            case CONFIDENTIAL -> role == Role.ADMIN;
        };
    }

    private void logAttempt(User user, Resource resource, String action, String result) {
        System.out.println(java.time.LocalDateTime.now() + ", " +
                user.getUniqueId() + ", " +
                user.getRole() + ", " +
                resource.getName() + ", " +
                action + ", " + result);
    }
}
