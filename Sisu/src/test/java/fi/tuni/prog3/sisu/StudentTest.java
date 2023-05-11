package fi.tuni.prog3.sisu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *Testing for student class.
 */
public class StudentTest {

    /**
     * Test of getName method, of class Student.
     */
    @Test
    public void testGetName() {
        System.out.println("studentGetName");
        Student instance = new Student();
        instance.setName("jake");
        String expResult = "jake";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNumber method, of class Student.
     */
    @Test
    public void testGetNumber() {
        System.out.println("studentGetNumber");
        Student instance = new Student();
        instance.setNumber(3);
        int expResult = 3;
        int result = instance.getNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStartYear method, of class Student.
     */
    @Test
    public void testGetStartYear() {
        System.out.println("studentGetStartYear");
        Student instance = new Student();
        instance.setStartYear(2019);
        int expResult = 2019;
        int result = instance.getStartYear();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTargetGraduationYear method, of class Student.
     */
    @Test
    public void testGetTargetGraduationYear() {
        System.out.println("studentGetTargetGraduationYear");
        Student instance = new Student();
        instance.setTargetGraduationYear(2025);
        int expResult = 2025;
        int result = instance.getTargetGraduationYear();
        assertEquals(expResult, result);

    }
    
}
