package securityapp.policy;

import securityapp.model.User;
import securityapp.model.Resource;

public interface AccessPolicy {
	
	boolean canRead(User user, Resource resource);
	
	boolean canWrite(User user, Resource resource);
}