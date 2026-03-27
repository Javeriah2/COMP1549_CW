package securityapp.policy;

import securityapp.capability.Capability;
import securityapp.capability.Read;
import securityapp.capability.Write;
import securityapp.capability.Execute;
import securityapp.model.Resource;
import securityapp.model.User;

public interface ResourceAccessor {
    Capability<Read> requestRead(User user, Resource resource);

    Capability<Write> requestWrite(User user, Resource resource);

    Capability<Execute> requestExecute(User user, Resource resource);
}