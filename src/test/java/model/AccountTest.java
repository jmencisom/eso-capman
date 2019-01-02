/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author javier
 */
public class AccountTest {
    
    public AccountTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("* AccountTest: @BeforeClass method");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("* AccountTest: @AfterClass method");
    }
    
    @Before
    public void setUp() {
        System.out.println("* AccountTest: @Before method");
    }
    
    @After
    public void tearDown() {
        System.out.println("* AccountTest: @After method");
    }

//    /**
//     * Test of getId method, of class Account.
//     */
//    @Test
//    public void testGetId() {
//        System.out.println("getId");
//        Account instance = null;
//        int expResult = 0;
//        int result = instance.getId();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setId method, of class Account.
//     */
//    @Test
//    public void testSetId() {
//        System.out.println("setId");
//        int id = 0;
//        Account instance = null;
//        instance.setId(id);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getEmail method, of class Account.
//     */
//    @Test
//    public void testGetEmail() {
//        System.out.println("getEmail");
//        Account instance = null;
//        String expResult = "";
//        String result = instance.getEmail();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setEmail method, of class Account.
//     */
//    @Test
//    public void testSetEmail() {
//        System.out.println("setEmail");
//        String email = "";
//        Account instance = null;
//        instance.setEmail(email);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of save method, of class Account.
//     */
//    @Test
//    public void testSave() throws Exception {
//        System.out.println("save");
//        Account instance = null;
//        instance.save();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of delete method, of class Account.
//     */
//    @Test
//    public void testDelete() throws Exception {
//        System.out.println("delete");
//        Account instance = null;
//        instance.delete();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of find method, of class Account.
//     */
//    @Test
//    public void testFind_int() {
//        System.out.println("find");
//        int id = 0;
//        Account expResult = null;
//        Account result = Account.find(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of find method, of class Account.
//     */
//    @Test
//    public void testFind_String() {
//        System.out.println("find");
//        String email = "";
//        Account expResult = null;
//        Account result = Account.find(email);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findAll method, of class Account.
//     */
//    @Test
//    public void testFindAll() {
//        System.out.println("findAll");
//        ArrayList<Account> expResult = null;
//        ArrayList<Account> result = Account.findAll();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of toString method, of class Account.
//     */
//    @Test
//    public void testToString() {
//        System.out.println("toString");
//        Account instance = null;
//        String expResult = "";
//        String result = instance.toString();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
