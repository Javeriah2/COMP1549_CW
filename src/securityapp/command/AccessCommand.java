package securityapp.command;

import securityapp.capability.Capability;

public interface AccessCommand {
    Capability<?> execute();

    String getDescription();
}