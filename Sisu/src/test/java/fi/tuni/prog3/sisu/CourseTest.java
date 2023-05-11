package fi.tuni.prog3.sisu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *Testing for course class.
 */
public class CourseTest {
    
    /**
     * Test of getCourseCode method, of class Course.
     */
    @Test
    public void testGetCourseCode() {
        System.out.println("getCourseCode");
        Course instance = new Course("name", "id", "groupId", 0, "courseCode");
        String expResult = "courseCode";
        String result = instance.getCourseCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Course.
     */
    @Test
    public void testToString() {
        System.out.println("courseToString");
        Course instance = new Course("name", "id", "groupId", 0, "courseCode");
        String expResult = "name";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getName method, of class Course.
     */
    @Test
    public void testGetName() {
        System.out.println("courseGetName");
        Course instance = new Course("name", "id", "groupId", 0, "courseCode");
        String expResult = "name";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getId method, of class Course.
     */
    @Test
    public void testGetId() {
        System.out.println("courseGetId");
        DegreeModule instance = new Course("name", "id", "groupId", 0, "courseCode");
        String expResult = "id";
        String result = instance.getId();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getGroupId method, of class Course.
     */
    @Test
    public void testGetGroupId() {
        System.out.println("courseGetGroupId");
        DegreeModule instance = new Course("name", "id", "groupId", 0, "courseCode");
        String expResult = "groupId";
        String result = instance.getGroupId();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getMinCredits method, of class Course.
     */
    @Test
    public void testGetMinCredits() {
        System.out.println("courseMinCredits");
        DegreeModule instance = new Course("name", "id", "groupId", 0, "courseCode");
        int expResult = 0;
        int result = instance.getMinCredits();
        assertEquals(expResult, result);
    }
}
