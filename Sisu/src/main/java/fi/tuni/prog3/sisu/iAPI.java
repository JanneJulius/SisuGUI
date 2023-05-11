package fi.tuni.prog3.sisu;
import com.google.gson.JsonObject;

/**
 * Interface for extracting data from the Sisu API.
 */
public interface iAPI {

    /**
     * Returns all the degrees as a JsonObject from the Sisu API for Tampere University.
     * @param curriculumPeriodId curriculum period id that is selected by the user.
     * @return JsonObject.
     * @throws Exception if cannot get the degrees.
     */
    public JsonObject getAllDegrees(String curriculumPeriodId) throws Exception;

    /**
     * Returns the exact module data as a JsonObject from the Sisu API.
     * @param Id Id of the module.
     * @return JsonObject.
     * @throws Exception if moudule cannot be found.
     */
    public JsonObject getModuleById(String Id) throws Exception;

    /**
     * Returns the exact course data as a JsonObject from the Sisu API.
     * @param Id Id of the course.
     * @return JsonObject.
     * @throws Exception if course cannot be found.
     */
    public JsonObject getCourseById(String Id) throws Exception;
}
