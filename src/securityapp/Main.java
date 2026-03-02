package securityapp;

import securityapp.model.ResourceRegistry;
import securityapp.capability.Capability;
import securityapp.capability.Read;
import securityapp.capability.Write;
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
        User developerUser = new User("emp-202", Role.DEVELOPER);
        User managerUser = new User("emp-303", Role.MANAGER);


        
        System.out.println("=== RUNNING ACCESS TESTS ===");

        try {
            System.out.println("Test 1.....");
            Capability<Read> readToken = proxy.requestRead(internUser, ResourceRegistry.COMPANY_WEBSITE);
            System.out.println("TEST 1 SUCCESS: User granted access to " + readToken.getResource().getName());
        } catch (SecurityException e) {
            System.out.println("TEST 1 FAILED: " + e.getMessage());
        }

        try {
            System.out.println("Attempting Test 2...");
            Capability<Read> readToken = proxy.requestRead(internUser, ResourceRegistry.PAYROLL_DB);
            System.out.println("TEST 2 SUCCESS: User granted access to " + readToken.getResource().getName());
        } catch (SecurityException e) {
            System.out.println("TEST 2 EXPECTED REJECTION: " + e.getMessage());
        }
        try {
        	System.out.println("Test 3");
        	Capability<Write> writeToken = proxy.requestWrite(managerUser, ResourceRegistry.HR_RECORDS);
        	System.out.println("TEST 3 SUCCESS: User granted access to" + writeToken.getResource().getName());
        }  catch (SecurityException e) {
            System.out.println("TEST 3 EXPECTED REJECTION: " + e.getMessage());
        }
        try {
        	System.out.println("Test 4");
        	Capability<Write> writeToken = proxy.requestWrite(internUser, ResourceRegistry.HR_RECORDS);
        	System.out.println("TEST 4 SUCCESS: User granted access to" + writeToken.getResource().getName());
        }  catch (SecurityException e) {
            System.out.println("TEST 4 EXPECTED REJECTION: " + e.getMessage());
        }


    }
    
}
