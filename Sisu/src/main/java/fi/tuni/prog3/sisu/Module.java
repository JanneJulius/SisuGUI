package fi.tuni.prog3.sisu;

/**
 * A Class for storing information on Modules.
 */
public class Module extends DegreeModule {
  private String moduleCode;
 
  /**
 * A constructor for initializing the member variables.
 * @param name name of the Module.
 * @param id id of the Module.
 * @param groupId group id of the Module.
 * @param minCredits minimum credits of the Module.
 * @param moduleCode code of the Module.
 */
  public Module(String name, String id, String groupId, int minCredits, String moduleCode) {
    super(name, id, groupId, minCredits);
    this.moduleCode = moduleCode;
  }

  /**
   * Returns the code of the Module.
   * @return code of the Module.
   */
  public String getModuleCode() {
    return this.moduleCode;
  }

  /**
   * Returns a string representation of the Module.
   * @return name of the Module.
   */
  @Override
  public String toString() {
    return getName();
  }
  
}
