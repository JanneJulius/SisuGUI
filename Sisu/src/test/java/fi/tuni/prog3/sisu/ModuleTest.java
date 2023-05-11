package fi.tuni.prog3.sisu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *Testing for module class.
 */
public class ModuleTest {
    
    /**
     * Test of getModuleCode method, of class Module.
     */
    @Test
    public void testGetModuleCode() {
        System.out.println("getModuleCode");
        Module instance = new Module("name", "id", "groupId", 0, "moduleCode");
        String expResult = "moduleCode";
        String result = instance.getModuleCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Module.
     */
    @Test
    public void testToString() {
        System.out.println("moduleToString");
        Module instance = new Module("moduleName", "id", "groupId", 0, "moduleCode");
        String expResult = "moduleName";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
