package securityapp.command;

import securityapp.capability.Capability;
import java.util.ArrayList;
import java.util.List;

public class AccessRequestInvoker {
    private final List<String> commandHistory = new ArrayList<>();

    public Capability<?> invoke(AccessCommand command) {
        commandHistory.add(command.getDescription());
        return command.execute();
    }

    public void printHistory() {
        System.out.println("\n=== COMMAND HISTORY ===");
        for (String entry : commandHistory) {
            System.out.println(entry);
        }
    }
}