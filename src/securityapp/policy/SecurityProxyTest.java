package securityapp.policy;

// Import JUnit assertion and testing libraries
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import securityapp.model.User;
import securityapp.model.Role;
import securityapp.model.Resource;
import securityapp.model.AccessScope;
import securityapp.capability.Capability;
import securityapp.capability.Read;
import securityapp.capability.Write;

public class SecurityProxyTest {

    private SecurityProxy proxy;
    private User guest;
    private User admin;
    private Resource lectureMaterial;
    private Resource examPaper;

    @BeforeEach
    public void setUp() {
        proxy = new SecurityProxy();
        guest = new User("usr-001", Role.GUEST);
        admin = new User("usr-999", Role.ADMIN);

        lectureMaterial = new Resource("Lecture Material", AccessScope.PUBLIC);
        examPaper = new Resource("Exam Paper", AccessScope.CONFIDENTIAL);
    }

    // TEST 1: Guest CAN read a public resource
    @Test
    public void testGuestCanReadPublicResource() {
        Capability<Read> token = proxy.requestRead(guest, lectureMaterial);
        assertNotNull(token, "Guest should be granted a Read token for a public resource.");
        assertEquals(lectureMaterial, token.getResource());
    }

    // TEST 2: Guest CANNOT read a confidential resource
    @Test
    public void testGuestCannotReadConfidentialResource() {
        Exception exception = assertThrows(SecurityException.class, () -> {
            proxy.requestRead(guest, examPaper);
        });
        assertTrue(exception.getMessage().contains("Access Denied"));
    }

    // TEST 3: Admin CAN write to a confidential resource
    @Test
    public void testAdminCanWriteConfidentialResource() {
        Capability<Write> token = proxy.requestWrite(admin, examPaper);
        assertNotNull(token, "Admin should be granted a Write token for a confidential resource.");
        assertEquals(examPaper, token.getResource());
    }

    // TEST 4: Guest CANNOT execute on a confidential resource
    @Test
    public void testGuestCannotExecuteConfidentialResource() {
        Exception exception = assertThrows(SecurityException.class, () -> {
            proxy.requestExecute(guest, examPaper);
        });
        assertTrue(exception.getMessage().contains("Access Denied"));
    }
}