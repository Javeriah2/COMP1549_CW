package securityapp;

import securityapp.model.*;
import securityapp.policy.*;
import securityapp.command.*;

public class Main {

    private static void runCommand(AccessRequestInvoker invoker, AccessCommand command) {
        try {
            invoker.invoke(command);
            System.out.println("ALLOWED  | " + command.getDescription());
        } catch (SecurityException e) {
            System.out.println("REFUSED  | " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        System.out.println("=== INITIALISING SECURITY SYSTEM ===\n");

        SecurityProxy proxy = new SecurityProxy();
        AccessRequestInvoker invoker = new AccessRequestInvoker();

        User guest = new User("usr-001", Role.GUEST);
        User student = new User("usr-002", Role.STUDENT);
        User admin = new User("usr-999", Role.ADMIN);

        Resource lectureMaterial = new Resource("Lecture Material", AccessScope.PUBLIC);
        Resource examPaper = new Resource("Exam Paper", AccessScope.CONFIDENTIAL);
        Resource printer = new Resource("Printer", AccessScope.INTERNAL);

        System.out.println("=== PART 1: DIFFERENT USERS, DIFFERENT RESOURCES ===\n");

        // Guest reads public resource (should ALLOW)
        runCommand(invoker, new ReadCommand(proxy, guest, lectureMaterial));

        // Student reads internal resource (should ALLOW)
        runCommand(invoker, new ReadCommand(proxy, student, printer));

        // Student reads confidential resource (should REFUSE)
        runCommand(invoker, new ReadCommand(proxy, student, examPaper));

        // Admin reads confidential resource (should ALLOW)
        runCommand(invoker, new ReadCommand(proxy, admin, examPaper));

        // Admin writes to confidential resource (should ALLOW)
        runCommand(invoker, new WriteCommand(proxy, admin, examPaper));

        // Guest writes to internal resource (should REFUSE)
        runCommand(invoker, new WriteCommand(proxy, guest, printer));

        // Student executes on internal resource (should REFUSE)
        runCommand(invoker, new ExecuteCommand(proxy, student, printer));

        // Guest executes on confidential resource (should REFUSE)
        runCommand(invoker, new ExecuteCommand(proxy, guest, examPaper));

        System.out.println("\n=== PART 2: DIFFERENT USERS, SAME RESOURCE ===\n");

        // Guest reads confidential resource (should REFUSE)
        runCommand(invoker, new ReadCommand(proxy, guest, examPaper));

        // Student reads confidential resource (should REFUSE)
        runCommand(invoker, new ReadCommand(proxy, student, examPaper));

        // Admin reads confidential resource (should ALLOW - only admin succeeds)
        runCommand(invoker, new ReadCommand(proxy, admin, examPaper));

        invoker.printHistory();
    }
}