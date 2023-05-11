package fi.tuni.prog3.sisu;
import java.net.*;
import java.io.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * A Class that implements an iAPI interface.
 */
public class API implements iAPI {

  // Base URL for SISU API requests
  private static final String BASE_URL = "https://sis-tuni.funidata.fi/kori/api/";

  /**
   * Returns all the degrees as a JsonObject from the Sisu API for Tampere University.
   * @param curriculumPeriodId curriculum period id that is selected by the user.
   * @return JsonObject.
   * @throws IOException if error in connencting to API endpoint.
   */
  @Override
  public JsonObject getAllDegrees(String curriculumPeriodId) throws IOException{
    
    // Construct the URL for the API endpoint
    String urlStr = 
      BASE_URL +
      "module-search?curriculumPeriodId=" + curriculumPeriodId +
      "&universityId=tuni-university-root-id&moduleType=DegreeProgramme&limit=1000";
    URL url = new URL(urlStr);
    
    // Open a connection to the API endpoint and read the response
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    StringBuilder response = new StringBuilder();
    String line;
    while ((line = rd.readLine()) != null) {
      response.append(line);
    }
    rd.close();
    
    // Parse the JSON response and return the JsonObject
    JsonObject JSON = JsonParser.parseString(response.toString()).getAsJsonObject();
    
    return JSON;
  }

  /**
   * Returns the exact module data as a JsonObject from the Sisu API.
   * @param Id Id of the module.
   * @return JsonObject.
   * @throws IOException if error in getting module from the API endpoint.
   */
  @Override
  public JsonObject getModuleById(String Id) throws IOException {

    String urlStr = 
    BASE_URL + "modules/" + 
      "by-group-id?groupId=" + Id + 
      "&universityId=tuni-university-root-id";
    URL url = new URL(urlStr);

    try {

      // Open a connection to the API endpoint and read the response
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");

      int responseCode = conn.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        // Successful GET request
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
          response.append(line);
        }
        rd.close();
        JsonArray jsonArray = JsonParser.parseString(response.toString()).getAsJsonArray();

        JsonObject JSON = null;
        if (!jsonArray.isJsonNull() && jsonArray.size() > 0) {
          JSON = jsonArray.get(0).getAsJsonObject();
        }

        if (JSON != null) {
          return JSON;
        } else {
          System.out.println("The response array is empty.");
        }
      } else {
        System.out.println("GET request failed. Response code: " + responseCode);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Returns the exact course data as a JsonObject from the Sisu API.
   * @param Id Id of the course.
   * @return JsonObject.
   * @throws IOException if error in finding the course from API endpoint.
   */
  @Override
  public JsonObject getCourseById(String Id) throws IOException {

    // Construct the URL for the API endpoint
    String urlStr = 
      BASE_URL + "course-units/" + 
      "by-group-id?groupId=" + Id + 
      "&universityId=tuni-university-root-id";
    URL url = new URL(urlStr);
  
    try {

      // Open a connection to the API endpoint and read the response
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");

      int responseCode = conn.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        // Successful GET request
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
          response.append(line);
        }
        rd.close();
        JsonArray jsonArray = JsonParser.parseString(response.toString()).getAsJsonArray();

        JsonObject JSON = null;
        if (!jsonArray.isJsonNull() && jsonArray.size() > 0) {
          JSON = jsonArray.get(0).getAsJsonObject();
        }

        if (JSON != null) {
          return JSON;
        } else {
          System.out.println("The response array is empty.");
        }
      } else {
        System.out.println("GET request failed. Response code: " + responseCode);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
