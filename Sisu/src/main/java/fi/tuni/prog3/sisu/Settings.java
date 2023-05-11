/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class creating a settings tab 
 */
public class Settings{

    private Teacher teacher = new Teacher();

    private boolean teacherMode = false;
    
    private MainView mainView = new MainView();
    
    Node createSettingsContent(Student student) {

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));

        // Create the title label
        Label titleLabel = new Label("Modify Personal Information");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        titleLabel.setPadding(new Insets(0, 0, 10, 0));

        // Add the title label to the top of the BorderPane
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        
        // Add ID for root for identifying it on styles.css
        root.setId("settings-content-root");

        //Adding button and teacher input to the BorderPane and aligning them
        var quitButton = mainView.getQuitButton(student);
        CheckBox teacherMode_checkBox = new CheckBox("Toggle Teacher input");
        teacherMode_checkBox.setSelected(teacherMode);
        HBox bottomHBox = new HBox(10);
        bottomHBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomHBox.setPadding(new Insets(10, 10, 0, 10));
        bottomHBox.getChildren().addAll(teacherMode_checkBox, quitButton);
        root.setBottom(bottomHBox);

        //Creating a VBox for the information fields.
        VBox InfoBox = new VBox();
        InfoBox.setSpacing(10);
        InfoBox.setPadding(new Insets(10, 10, 10, 10));

        //Adding labels and text fields for the teacher information fields.
        Label teacherNameLabel = new Label("Teacher Name:");
        TextField teacherNameField = new TextField();
        teacherNameField.setText(teacher.getName());

        Label teacherNumberLabel = new Label("WorkID:");
        NumericTextField teacherNumberField = new NumericTextField();
        teacherNumberField.setText(String.valueOf(teacher.getNumber()));

        //Adding labels and text fields for the student information fields.
        Label nameLabel = new Label("Student Name:");
        TextField nameField = new TextField();
        nameField.setText(student.getName());

        Label numberLabel = new Label("Student Number:");
        NumericTextField numberField = new NumericTextField();
        numberField.setText(String.valueOf(student.getNumber()));

        Label startYearLabel = new Label("Start Year:");
        NumericTextField startYearField = new NumericTextField();
        startYearField.setText(String.valueOf(student.getStartYear()));

        Label graduationYearLabel = new Label("Target Graduation Year:");
        NumericTextField graduationYearField = new NumericTextField();
        graduationYearField.setText(String.valueOf(student.getTargetGraduationYear()));

        if(teacherMode){
            InfoBox.getChildren().addAll(teacherNameLabel,teacherNameField,
                                        teacherNumberLabel, teacherNumberField);
        }
        else {
            InfoBox.getChildren().addAll(nameLabel, nameField, numberLabel,
                                        numberField, startYearLabel, startYearField, 
                                        graduationYearLabel, graduationYearField);
        }

        //Determines which mode is shown: teacher or student
        teacherMode_checkBox.setOnAction(event -> {
            if (teacherMode_checkBox.isSelected()) { //Teacher
                InfoBox.getChildren().removeAll(nameLabel, nameField, numberLabel,
                                            numberField, startYearLabel, startYearField, 
                                            graduationYearLabel, graduationYearField);
                InfoBox.getChildren().addAll(teacherNameLabel,teacherNameField,
                                            teacherNumberLabel, teacherNumberField);
            } 
            else { //Student
                InfoBox.getChildren().removeAll(teacherNameLabel,teacherNameField,
                                            teacherNumberLabel, teacherNumberField);
                InfoBox.getChildren().addAll(  nameLabel, nameField, numberLabel,
                                            numberField, startYearLabel, startYearField, 
                                            graduationYearLabel, graduationYearField);
            }
        });

        root.setCenter(InfoBox);    
        return root;
    }
    
    //Creates a new dialog where is possible to apply teacher or student information
    Dialog<ButtonType> getSettingsDialog(Student student) {
        Dialog<ButtonType> settingsDialog = new Dialog<>();
        settingsDialog.setTitle("Personal information");
        settingsDialog.setHeaderText("Please, enter your information");

        // Apply the styles to the SettingsDialog
        settingsDialog.getDialogPane().getStylesheets().add(getClass().getClassLoader().getResource("styles.css").toExternalForm()); 

        ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.APPLY);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        settingsDialog.getDialogPane().getButtonTypes().addAll(applyButtonType, cancelButtonType);
        
        Button applyButton = (Button) settingsDialog.getDialogPane().lookupButton(applyButtonType);
        applyButton.setDisable(true);
        Button cancelButton = (Button) settingsDialog.getDialogPane().lookupButton(cancelButtonType);

        // Create a BorderPane as a container for the GridPane
        BorderPane borderPane = new BorderPane();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        //grid.setPadding(new Insets(20, 150, 10, 10));
        grid.setAlignment(Pos.CENTER); // Center the GridPane

        // Name the student information fields
        TextField nameField = new TextField();
        NumericTextField numberField = new NumericTextField();
        NumericTextField startYearField = new NumericTextField();
        NumericTextField graduationYearField = new NumericTextField();
        Label nameLabel = new Label("Name:");
        Label numberLabel = new Label("Student Number:");
        Label StartYearLabel = new Label("Start Year:");
        Label TargetYearLabel = new Label("Target Graduation Year:");
        
        // Add all the labels and fields to the GridPane
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(numberLabel, 0, 1);
        grid.add(numberField, 1, 1);
        grid.add(StartYearLabel, 0, 2);
        grid.add(startYearField, 1, 2);
        grid.add(TargetYearLabel, 0, 3);
        grid.add(graduationYearField, 1, 3);

        CheckBox teacherMode_checkBox = new CheckBox("Toggle Teacher input");
                
        //Teacher fields, by option of checkbox
        TextField teacherNameField = new TextField();
        NumericTextField teacherNumberField = new NumericTextField();
        Label teacherNameLabel = new Label("Name:");
        Label teacherNumberLabel = new Label("Work ID:");
        
        grid.add(teacherMode_checkBox, 0, 4);

        // Removes the old nodes and sets new nodes if the teachermode is selected
        teacherMode_checkBox.setOnAction(event -> {
            if (teacherMode_checkBox.isSelected()) {
                grid.getChildren().removeAll(nameField, numberField,numberField, 
                                            startYearField,graduationYearField,
                                            nameLabel,numberLabel, TargetYearLabel, 
                                            StartYearLabel );
                grid.add(teacherNameLabel, 0, 0);
                grid.add(teacherNameField, 1, 0);
                grid.add(teacherNumberLabel, 0, 1);
                grid.add(teacherNumberField, 1, 1);

            } else {
                grid.getChildren().removeAll( teacherNameLabel,teacherNameField,
                                            teacherNumberLabel, teacherNumberField);
                grid.add(nameLabel, 0, 0);
                grid.add(nameField, 1, 0);
                grid.add(numberLabel, 0, 1);
                grid.add(numberField, 1, 1);
                grid.add(StartYearLabel, 0, 2);
                grid.add(startYearField, 1, 2);
                grid.add(TargetYearLabel, 0, 3);
                grid.add(graduationYearField, 1, 3);
            }
        });
        
        //Enabling the Apply button only when all fields have been filled.
        applyButton.setDisable(true);
        teacherNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            applyButton.setDisable(newValue.trim().isEmpty() || teacherNumberField.getText().trim().isEmpty()
                                    || teacherMode_checkBox.isSelected());
        });
        teacherNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            applyButton.setDisable(newValue.trim().isEmpty() || teacherNameField.getText().trim().isEmpty() 
                                    || !teacherMode_checkBox.isSelected());
        });


        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            applyButton.setDisable(newValue.trim().isEmpty() || numberField.getText().trim().isEmpty() 
                                    || startYearField.getText().trim().isEmpty() 
                                    || graduationYearField.getText().trim().isEmpty()
                                    || teacherMode_checkBox.isSelected());
        });
        numberField.textProperty().addListener((observable, oldValue, newValue) -> {
            applyButton.setDisable(newValue.trim().isEmpty() || nameField.getText().trim().isEmpty() 
                                    || startYearField.getText().trim().isEmpty() 
                                    || graduationYearField.getText().trim().isEmpty()
                                    || teacherMode_checkBox.isSelected());
        });
        startYearField.textProperty().addListener((observable, oldValue, newValue) -> {
            applyButton.setDisable(newValue.trim().isEmpty() || nameField.getText().trim().isEmpty() 
                                    || numberField.getText().trim().isEmpty() 
                                    || graduationYearField.getText().trim().isEmpty()
                                    || teacherMode_checkBox.isSelected());
        });
        graduationYearField.textProperty().addListener((observable, oldValue, newValue) -> {
            applyButton.setDisable(newValue.trim().isEmpty() || nameField.getText().trim().isEmpty() 
                                    || numberField.getText().trim().isEmpty() 
                                    || startYearField.getText().trim().isEmpty()
                                    || teacherMode_checkBox.isSelected());
        });
        
        
        applyButton.setOnAction((ActionEvent event) -> {
            if(teacherMode_checkBox.isSelected()){
                teacher.setName(teacherNameField.getText());
                teacher.setNumber(Integer.parseInt(teacherNumberField.getText()));
                teacherMode = true;
            }
            else{
                // Tries to read from the file, if fails, sets the new specifications for the student
                try{
                    student.readFromJson(numberField.getText());
                    }
                catch(IOException e){
                    System.out.println("Error reading file: " + e.getMessage());
                }
                student.setName(nameField.getText());
                student.setNumber(Integer.parseInt(numberField.getText()));
                student.setStartYear(Integer.parseInt(startYearField.getText()));
                student.setTargetGraduationYear(Integer.parseInt(graduationYearField.getText()));
            }

            settingsDialog.setResult(ButtonType.APPLY);

        });

        cancelButton.setOnAction((ActionEvent event) -> {
            settingsDialog.setResult(ButtonType.CANCEL);
            settingsDialog.close();
        });
        settingsDialog.setOnCloseRequest(event -> {
            settingsDialog.setResult(ButtonType.CANCEL);
            settingsDialog.close();
        });

        borderPane.setCenter(grid);
        settingsDialog.getDialogPane().setContent(borderPane);
          
        return settingsDialog;
    }
    
    /**
     * Class for a field containing only numbers.
     */
    public class NumericTextField extends TextField {
        /**
         * Constructor for the numeric textfield
         */
        public NumericTextField() {
            this.addEventFilter(KeyEvent.KEY_TYPED, event -> {
                if (!event.getCharacter().matches("\\d")) {
                    event.consume();
                }
            });
        }
    }    
}




