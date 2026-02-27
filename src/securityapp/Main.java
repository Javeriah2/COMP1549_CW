package securityapp;

import securityapp.capability.Capability;
import securityapp.capability.Read;
import securityapp.model.AccessScope;
import securityapp.model.Resource;
import securityapp.model.Role;
import securityapp.model.User;
import securityapp.policy.SecurityProxy;

public class Main {
    public static void main(String[] args) {

        System.out.println("---INITIALIZE SECURITY SYS---");

        SecurityProxy proxy = new SecurityProxy();

        User internUser = new User("emp-101", Role.INTERN);
        User adminUser = new User("emp-999", Role.SYS_ADMIN);

        Resource publicSite = new Resource("Company Website", AccessScope.PUBLIC);
        Resource payrollDB = new Resource("Payroll Database", AccessScope.CONFIDENTIAL);

        System.out.println("=== RUNNING ACCESS TESTS ===");

        try {
            System.out.println("Test 1.....");
            Capability<Read> readToken = proxy.requestRead(internUser, publicSite);
            System.out.println("TEST 1 SUCCESS: User granted access to " + readToken.getResource().getName());
        } catch (SecurityException e) {
            System.out.println("TEST 1 FAILED: " + e.getMessage());
        }

        try {
            System.out.println("Attempting Test 2...");
            Capability<Read> readToken = proxy.requestRead(internUser, payrollDB);
            System.out.println("TEST 2 SUCCESS: User granted access to " + readToken.getResource().getName());
        } catch (SecurityException e) {
            System.out.println("TEST 2 EXPECTED REJECTION: " + e.getMessage());
        }

    }
}
