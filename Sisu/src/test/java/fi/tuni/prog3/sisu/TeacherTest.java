package fi.tuni.prog3.sisu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *Testing for teacher class.
 */
public class TeacherTest {

    /**
     * Test of getName method, of class Teacher.
     */
    @Test
    public void testGetName() {
        System.out.println("teacherGetName");
        Teacher instance = new Teacher();
        instance.setName("jake");
        String expResult = "jake";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNumber method, of class Teacher.
     */
    @Test
    public void testGetNumber() {
        System.out.println("teacherGetNumber");
        Teacher instance = new Teacher();
        instance.setNumber(3);
        int expResult = 3;
        int result = instance.getNumber();
        assertEquals(expResult, result);
    }
    
}
