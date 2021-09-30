/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import africanmobilephones.MainAfricanNumbers;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author marco
 */
public class AfricanMobileTest {
    MainAfricanNumbers main ;
    public AfricanMobileTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        main = new MainAfricanNumbers("C:\\South_African_Mobile_Numbers.csv");
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    @DisplayName("Test if Number is Good Shaped")
    public void testNumberGoodShaped() {
        
        Pattern pattern = Pattern.compile("^\\d{11}$");

        assertEquals("27123456789 Number Good Shaped!", this.main.isNumberGoodShaped(pattern, "27123456789"), "Test Passed");
    }
    
    @Test
    @DisplayName("Test if Number is Good Shaped")
    public void testNumberBadShaped() {
        
        Pattern pattern = Pattern.compile("^\\d{11}$");

        assertEquals("77123456789 Number Not Correctly Formed!", this.main.isNumberGoodShaped(pattern, "77123456789"), "Test Passed");
    }
    
    @Test
    @DisplayName("Test if Number Has Been Modified")
    public void testNumberGoodIfModified() {
        
        Pattern pattern = Pattern.compile("^\\d{11}$");

        assertEquals("27123456789333 Number good if truncated --> 27123456789", this.main.isNumberGoodShaped(pattern, "27123456789333"), "Test Passed");
    }    
    
}
