package junit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import model.Account;
import model.DBConnector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author edisonarango
 */
public class DBTest {
    
    private static String email = new Random().nextInt(10000000) + "@mail.com";
    
    public DBTest() {
    }
    
    //This is executed before all test methods of the class are executed.
    @BeforeClass
    public static void setUpClass() {
        DBConnector.getInstance().connect();
    }
    
    //This is after before all test methods of the class are executed.
    @AfterClass
    public static void tearDownClass() throws SQLException {
        Connection c = DBConnector.getInstance().getConnection();
        PreparedStatement pv = c.prepareStatement("delete from accounts where email = ?");
        pv.setString(1, email);
        pv.execute();
        DBConnector.getInstance().close();
    }
    
    //This is executed before each test (As many times as test methods)
    @Before
    public void setUp() {
    }
    
    //This is executed after each test
    @After
    public void tearDown() {
    }

    @Test
    public void databaseConnected() {
        assertNotNull(DBConnector.getInstance().getConnection());
    }
    
    @Test
    public void accountSaved(){
        Account a = new Account(email);
        try {
            a.save();
            //Test passed if not exception is throwed
            assertTrue(true);   
        } catch (SQLException ex) {
            assertTrue(false);
        }
    }
    
    @Test
    public void accountNotDuplicated(){
        Account a = new Account(email);
        try {
            a.save();
            assertTrue(false);   
        } catch (SQLException ex) {
            //Should throw an exception due to the same email can't be saved
            assertTrue(true);
        }
    }
}
