package securityapp.policy;

import securityapp.model.Resource;
import securityapp.model.User;
import securityapp.model.AccessScope;
import securityapp.model.Role;

public class ManagementAccessPolicy implements AccessPolicy{

	@Override
	public boolean canRead(User user, Resource resource) {
		// TODO Auto-generated method stub
		if (resource.getScope() == AccessScope.PUBLIC) {
			return true;
		}
		else if (resource.getScope() == AccessScope.INTERNAL) {
			switch (user.getRole()) {
			case DEVELOPER:
			case MANAGER:
			case SYS_ADMIN:
				return true;
			default:
				return false;
			}
		}
		else if (resource.getScope() == AccessScope.CONFIDENTIAL) {
			switch (user.getRole()) {
			case MANAGER:
			case SYS_ADMIN:
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean canWrite(User user, Resource resource) {
		// TODO Auto-generated method stub
		if (resource.getScope() == AccessScope.PUBLIC) {
			switch (user.getRole()) {
			case INTERN:
			case SYS_ADMIN:
				return true;
			default:
				return false;
			}
		
		}
		else if (resource.getScope() == AccessScope.INTERNAL) {
			switch (user.getRole()) {
			case DEVELOPER:
			case MANAGER:
			case SYS_ADMIN:
				return true;
			default:
				return false;
			}
		}
		else if (resource.getScope() == AccessScope.CONFIDENTIAL) {
			switch (user.getRole()) {
			case MANAGER:
			case SYS_ADMIN:
				return true;
			default:
				return false;
			}
		}
		
		return false;
	}

}
