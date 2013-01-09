
package riesware;

import java.util.Calendar;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import riesware.entities.App;
import riesware.entities.Permission;
import riesware.repositories.AppRepository;
import riesware.repositories.PermissionRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-config.xml")
public class ReadWriteDataTest {
    
    private static final Logger logger = LoggerFactory.getLogger(ReadWriteDataTest.class);
    
    @Autowired private AppRepository apps;
    @Autowired private PermissionRepository permissions;
    @Autowired private Application application;
    
    public ReadWriteDataTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void writeAppWithSomePermissions() {
        
        Permission p1 = application.getPermission("some test permission");        
        Permission p2 = application.getPermission("another test permission");        
        
        App app = new App();
        app.addPermission(p1);
        app.addPermission(p2);
        
        app.setAppId("some.id"); // app id must be set
        app.setName("Sample App");
        app.setDescription("Sample Description");
        
        apps.save(app);
    }
    
    @Test
    public void readAppById() {
        
        // find App by ID
        App a = apps.findOne("some.id");
        assertTrue(a != null);
        
        Collection<Permission> perms = a.getPermissions();
        assertTrue(perms.size() > 1);
        
        for (Permission p : perms) {
            logger.info("Permission " + p.getId() + ": " + p.getPermissionString());
        }
        
    }
    
    @Test
    public void checkIfPermissinExists() {
        
        String p1 = "existing permission string";        
        Permission perm1 = application.getPermission(p1);
        
        logger.info("Permission " + perm1.getId() + ": " + perm1.getPermissionString());
        
        String p2 = "unexisting permission string " + Calendar.getInstance().getTimeInMillis();
        Permission perm2 = application.getPermission(p2);
        
        logger.info("Permission " + perm1.getId() + ": " + perm1.getPermissionString());
        
        assertTrue(p1.equals(perm1.getPermissionString()));
        assertTrue(p2.equals(perm2.getPermissionString()));
    }
}
