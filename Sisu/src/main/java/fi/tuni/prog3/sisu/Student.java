/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.stream.JsonWriter;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;
/**
 * A class for storing information of student.
 */
public class Student {
    private String studentName;
    private int studentNumber;
    private int startYear;
    private int targetGraduationYear;
    private int credits;
    private ArrayList<String> courses = new ArrayList();
        
    /**
     * Default constructor for student class.
     */
    public Student(){   
        
    }
    
    /**
     * Get the name of the student.
     * @return Name of the student.
     */
    public String getName(){
        return this.studentName;
    }
    
    /**
     * Get the number of the student.
     * @return student number.
     */
    public int getNumber(){
        return studentNumber;
    }
    
    /**
     * Gets the start year of the student studies.
     * @return the start year.
     */
    public int getStartYear(){
        return this.startYear;
    }
    
    /**
     * Gets the target graduation year of the student.
     * @return the target year.
     */
    public int getTargetGraduationYear(){
        return this.targetGraduationYear;
    }
    
    /**
     * Gives the courses in case of iteration
     * @return Arraylist of courses the student has accomplished
     */
    public ArrayList getCourses(){
        return this.courses;
    }

    /**
     * Tracks gredits for ui 
     * @return student credits
     */
    public int getCredits(){
        return this.credits;
    }
    
    /**
     * Gives credits for ui
     * @param credits to be tracked
     */
    public void setCredits(int credits){
        this.credits = credits;
    }
    /**
     * Sets a name for the student.
     * @param name is a new name for the student. 
     */
    public void setName(String name){
        this.studentName = name;
    }
    
    /**
     * Sets a name for the student.
     * @param number is a new number for the student.
     */
    public void setNumber(int number){
        try {
            this.studentNumber = number;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Student number must be an integer.");
        }
    }
    
    /**
     * Sets a starting year for the student.
     * @param startingYear year to be set.
     */
    public void setStartYear(int startingYear){
        try {
            this.startYear= startingYear;        
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Starting year must be an integer.");
        }
    }
    
    /**
     * sets the course for the student to be written in json
     * for futher use
     * @param course to set in students accomplishments
     */
    public void setCourse(String course){
        try {
            this.courses.add(course); 
                  
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid type of course");
        }
    }
    
    /**
     * Removes the course from the students accomplishments
     * @param course to remove
     */
    public void removeCourse(String course){
        try {
        this.courses.remove(course);               
        } 
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Course not found");
        }
    }

    /**
     * Sets the target graduation year for the student.
     * @param graduationYear year to be set.
     */
    public void setTargetGraduationYear(int graduationYear){
        try {
            this.targetGraduationYear = graduationYear;       
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Graduation year must be an integer.");
        }
    }
   
    /**
     * Writes the JSON file from the object to the (sisu) folder.
     * @param fileName to be written in that file
     * @throws IOException in a case of an error
     */
    public void writeToFile(String fileName) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("studentName", studentName);
        jsonObject.addProperty("studentNumber", studentNumber);
        jsonObject.addProperty("startYear", startYear);
        jsonObject.addProperty("graduationYear", targetGraduationYear);
        jsonObject.addProperty("credits", credits);
        
        JsonArray courseArray = new JsonArray();
        for (String course : courses) {
            JsonObject courseJson = new JsonObject();
            courseJson.addProperty("courseName", course);
            courseArray.add(courseJson);
        }
        jsonObject.add("courses", courseArray);
        
         Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (JsonWriter writer = new JsonWriter(new FileWriter(fileName+".json", false))) {
            gson.toJson(jsonObject, writer);
            System.out.println("JSON written to file successfully.");
        } catch (JsonIOException e) {
            System.out.println("Error writing JSON to file: " + e.getMessage());
        }
    }
    
    /**
     * Reads the json file from the (sisu) folder.
     * @param fileName is the file to be read
     * @throws IOException in case of an exception
     */
    public void readFromJson(String fileName) throws IOException {
        try (Reader reader = new FileReader(fileName+".json")) {
            JsonParser parser = new JsonParser();
            JsonElement rootElement = parser.parse(reader);

            JsonObject studentObj = rootElement.getAsJsonObject();

            this.studentName=studentObj.get("studentName").getAsString();
            this.studentNumber=studentObj.get("studentNumber").getAsInt();
            this.startYear=studentObj.get("startYear").getAsInt();
            this.targetGraduationYear= studentObj.get("graduationYear").getAsInt();    
            this.credits= studentObj.get("credits").getAsInt();  


            JsonArray coursesArr = studentObj.getAsJsonArray("courses");
            for (JsonElement courseElement : coursesArr) {
                JsonObject courseObj = courseElement.getAsJsonObject();
                String courseName = courseObj.get("courseName").getAsString();
                this.courses.add(courseName);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}

