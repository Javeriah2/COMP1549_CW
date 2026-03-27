package securityapp.policy;

import securityapp.capability.Capability;
import securityapp.capability.Read;
import securityapp.capability.Write;
import securityapp.capability.Execute;
import securityapp.model.Resource;
import securityapp.model.User;

public class DirectResourceAccessor implements ResourceAccessor {

    public Capability<Read> requestRead(User user, Resource resource) {
        return new Capability<Read>(resource);
    }

    public Capability<Write> requestWrite(User user, Resource resource) {
        return new Capability<Write>(resource);
    }

    public Capability<Execute> requestExecute(User user, Resource resource) {
        return new Capability<Execute>(resource);
    }
}