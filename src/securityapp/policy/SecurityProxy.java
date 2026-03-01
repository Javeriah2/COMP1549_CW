package securityapp.policy;


import securityapp.model.User;
import securityapp.model.Resource;
import securityapp.capability.Capability;
import securityapp.capability.Read;
import securityapp.capability.Write;

public class SecurityProxy {

	private final AccessPolicy policy = new ManagementAccessPolicy();
	
    public Capability<Read> requestRead(User user, Resource resource) {

        if (policy.canRead(user, resource)) {
            logAttempt(user, resource, "READ", "ALLOW");
            return new Capability<Read>(resource);
        } else {
            logAttempt(user, resource, "READ", "REFUSE");
            throw new SecurityException(
                    "Access Denied " + user.getRole() + " cannot read " + resource.getScope() + " resources");
        }
    }

    public Capability<Write> requestWrite(User user, Resource resource) {
        if (policy.canWrite(user, resource)) {
            logAttempt(user, resource, "WRITE", "ALLOW");
            return new Capability<Write>(resource);
        } else {
            logAttempt(user, resource, "WRITE", "REFUSE");
            throw new SecurityException(
                    "Access Denied " + user.getRole() + " cannot write to " + resource.getScope() + " resources");
        }
    }

    private void logAttempt(User user, Resource resource, String action, String result) {
        System.out.println(java.time.LocalDateTime.now() + ", " +
                user.getUniqueId() + ", " +
                user.getRole() + ", " +
                resource.getName() + ", " +
                action + ", " + result);
    }
}
