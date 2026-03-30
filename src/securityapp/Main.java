package securityapp;

import securityapp.capability.Capability;
import securityapp.capability.Read;
import securityapp.capability.Write;
import securityapp.capability.Execute;
import securityapp.model.AccessScope;
import securityapp.model.Resource;
import securityapp.model.Role;
import securityapp.model.User;
import securityapp.policy.SecurityProxy;

public class Main {

    private static void runReadTest(SecurityProxy proxy, User user, Resource resource) {
        try {
            Capability<Read> token = proxy.requestRead(user, resource);
            System.out.println("ALLOWED  | " + user.getRole() + " READ " + token.getResource().getName());
        } catch (SecurityException e) {
            System.out.println("REFUSED  | " + e.getMessage());
        }
    }

    private static void runWriteTest(SecurityProxy proxy, User user, Resource resource) {
        try {
            Capability<Write> token = proxy.requestWrite(user, resource);
            System.out.println("ALLOWED  | " + user.getRole() + " WRITE " + token.getResource().getName());
        } catch (SecurityException e) {
            System.out.println("REFUSED  | " + e.getMessage());
        }
    }

    private static void runExecuteTest(SecurityProxy proxy, User user, Resource resource) {
        try {
            Capability<Execute> token = proxy.requestExecute(user, resource);
            System.out.println("ALLOWED  | " + user.getRole() + " EXECUTE " + token.getResource().getName());
        } catch (SecurityException e) {
            System.out.println("REFUSED  | " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        System.out.println("INITIALIZE SECURITY SYS");

        SecurityProxy proxy = new SecurityProxy();

        User guest = new User("usr-111", Role.GUEST);
        User admin = new User("usr-999", Role.ADMIN);
        User student = new User("usr-333", Role.STUDENT);

        Resource lectureMaterial = new Resource("COMP-1549 Lecture 3 Slides", AccessScope.PUBLIC);
        Resource examPaper = new Resource("COMP-1549 Exam Questionnaire", AccessScope.CONFIDENTIAL);
        Resource printer = new Resource("Printer", AccessScope.INTERNAL);

        System.out.println("=== PART 1: DIFFERENT USERS, DIFFERENT RESOURCES ===\n");

        // Guest reads public resource (should ALLOW)
        runReadTest(proxy, guest, lectureMaterial);

        // Student reads internal resource (should ALLOW)
        runReadTest(proxy, student, printer);

        // Student reads confidential resource (should REFUSE)
        runReadTest(proxy, student, examPaper);

        // Admin reads confidential resource (should ALLOW)
        runReadTest(proxy, admin, examPaper);

        // Admin writes to confidential resource (should ALLOW)
        runWriteTest(proxy, admin, examPaper);

        // Guest writes to internal resource (should REFUSE)
        runWriteTest(proxy, guest, printer);

        // Student executes on printer (should REFUSE)
        runExecuteTest(proxy, student, printer);

        // Guest executes on confidential resource (should REFUSE)
        runExecuteTest(proxy, guest, examPaper);

        System.out.println("\n=== PART 2: DIFFERENT USERS, SAME RESOURCE ===\n");

        // All three users try to read the exam paper (only admin should succeed)
        runReadTest(proxy, guest, examPaper);
        runReadTest(proxy, student, examPaper);
        runReadTest(proxy, admin, examPaper);
    }

}