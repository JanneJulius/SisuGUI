/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

/**
 * A class for storing information of teacher.
 */
public class Teacher{
    private String teacherName;
    private int teacherNumber;

    /**
     * Default constructor for the teacher.
     */
    public Teacher()
    {}
    
    /**
     * Get the name of the teacher.
     * @return Name of the teacher.
     */
    public String getName(){
        return this.teacherName;
    }
    
    /**
     * Get the number of the teacher.
     * @return teacher number.
     */
    public int getNumber(){
        return this.teacherNumber;
    }
    
    /**
     * Sets a name for the teacher.
     * @param name is a new name for the teacher. 
     */
    public void setName(String name){
        this.teacherName = name;
    }
    
    /**
     * Sets a name for the teacher.
     * @param number is a new number for the teacher.
     */
    public void setNumber(int number){
        try {
            this.teacherNumber = number;       
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("TeacherID must be an integer.");
        }
    }
}

