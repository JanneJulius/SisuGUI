package fi.tuni.prog3.sisu;

/**
 * A class for storing information on Courses.
 */
public class Course extends DegreeModule {
  private String courseCode;
  
  /**
 * A constructor for initializing the member variables.
 * @param name name of the Course.
 * @param id id of the Course.
 * @param groupId group id of the Course.
 * @param minCredits minimum credits of the Course.
 * @param courseCode code of the Course.
 */
  public Course(String name, String id, String groupId, int minCredits, String courseCode) {
    super(name, id, groupId, minCredits);
    this.courseCode = courseCode;
  }
  
  /**
   * Returns the code of the Course.
   * @return code of the Course.
   */
  public String getCourseCode() {
    return this.courseCode;
  }

  /**
   * Returns a string representation of the Course.
   * @return name of the Course.
   */
  @Override
  public String toString() {
    return getName();
  }

}




