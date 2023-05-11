/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Builds and updates the main view of the Sisu
 */
public class MainView {
    
    // All the available degrees from the University of Tampere
    private List<DegreeModule> degrees;

    // Curriculum Period Id that user selects
    private String selectedCurriculumPeriodId;

    // Selected Degree from the 'degrees'
    private DegreeModule selectedDegree;

    // Selected Module from the treeView
    private DegreeModule selectedModuleFromList;
    
    // TreeView data structure for hold the tree structure
    private TreeView<DegreeModule> treeView = new TreeView<>();
    // cell factory for the TreeView. Basically sets the names for the TreeView
    {
        treeView.setCellFactory(tv -> new TreeCell<DegreeModule>() {
            @Override
            protected void updateItem(DegreeModule item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
    }
    private VBox rightVBox;
    
    // Used for showing credits from courses
    private Label creditLabel;
    
    // Used for saving which courses are selected
    private List<String> selectedCourses;

    /**
     * Creates the root for the mainview.
     * @param student being passed around for one student to be used
     * @return the created root of the scene.
     */
    public BorderPane createMainViewContent(Student student) {

        // root BorderPane of the mainview
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));

        // HBox including the left TreeView and right CoursesView
        HBox centerHBox = new HBox(10); 
        centerHBox.getChildren().addAll(getTreeViewVBox(), getCoursesVBox(student));
        root.setCenter(centerHBox);
        
        // Add quitButton to the down right corner
        var quitButton = getQuitButton(student);
        BorderPane.setMargin(quitButton, new Insets(10, 10, 0, 10));
        root.setBottom(quitButton);
        BorderPane.setAlignment(quitButton, Pos.TOP_RIGHT);
        
        // Add creditLabel to the top right corner

        creditLabel = new Label("Credits: " + student.getCredits() + " op");
        BorderPane.setMargin(creditLabel, new Insets(0, 10, 10, 10));
        root.setTop(creditLabel);
        BorderPane.setAlignment(creditLabel, Pos.BOTTOM_RIGHT);
        
        selectedCourses = new ArrayList<>();
        return root;
    }
    
    /**
     * Creates the Vbox for the mainview
     * @return the created box
     */
    private VBox getTreeViewVBox() {

        VBox leftVBox = new VBox();
        leftVBox.setPrefWidth(380);

        // Create a ComboBox for selecting the curriculum period ID
        ComboBox<String> curriculumPeriodComboBox = new ComboBox<>();
        curriculumPeriodComboBox.setPromptText("Select a curriculum period ID");
        curriculumPeriodComboBox.getItems().addAll("uta-lvv-2020", "uta-lvv-2021", "uta-lvv-2022", "uta-lvv-2023");
        curriculumPeriodComboBox.getStyleClass().add("curriculumComboBox");
        changeComboBoxColour(curriculumPeriodComboBox);

        iAPI api = new API();

        // Create a ComboBox for selecting the degree
        ComboBox<String> degreeComboBox = new ComboBox<>();
        degreeComboBox.getStyleClass().add("degreeComboBox");
        degreeComboBox.setPromptText("Select a degree...");
        changeComboBoxColour(degreeComboBox);
       
        // Create a ProgressIndicator for the loading icon
        ProgressIndicator firstProgressIndicator = new ProgressIndicator();
        firstProgressIndicator.setVisible(false);

        Button backButton = new Button("<-");
        backButton.setMinWidth(40);
        HBox backButtonAndCourses= new HBox();
        backButtonAndCourses.setAlignment(Pos.CENTER_RIGHT); // Align content to the right
        backButtonAndCourses.setSpacing(10);
        backButtonAndCourses.getChildren().addAll(backButton, degreeComboBox);
        backButtonAndCourses.setVisible(false);

        // Create a StackPane to stack the ComboBoxes.
        StackPane firstStackPane = new StackPane();
        firstStackPane.getChildren().addAll(curriculumPeriodComboBox, firstProgressIndicator, backButtonAndCourses);

        // Add a listener to the valueProperty of the ComboBox. User selects curriculum period.
        curriculumPeriodComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            selectedCurriculumPeriodId = newValue;
        });
        
        curriculumPeriodComboBox.setOnAction(event -> {
            try {
                // Show the loading icon and hide the curriculumPeriodComboBox
                curriculumPeriodComboBox.setVisible(false);
                firstProgressIndicator.setVisible(true);
    
                // Run the task on a new thread to keep the UI responsive
                new Thread(() -> {
                    try {
                        degrees = convertToDegreeList(api.getAllDegrees(selectedCurriculumPeriodId));
    
                        // Show only the names of the degrees on the degreeComboBox
                        List<String> degreeNames = degrees.stream()
                                .map(DegreeModule::getName)
                                .collect(Collectors.toList());
    
                        // Update the UI on the JavaFX Application thread
                        Platform.runLater(() -> {
                            degreeComboBox.getItems().addAll(degreeNames);
                            backButtonAndCourses.setVisible(true); // Show backButtonAndCourses when degreeComboBox is filled with degrees
                            // Hide the loading icon
                            firstProgressIndicator.setVisible(false);
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        degreeComboBox.setOnAction(e -> {
            rightVBox.getChildren().clear();
            // User selects one degree from the degreeComboBox. Update the 'selectedDegree' variable
            selectedDegree = degrees.stream()
                .filter(p -> p.getName().equals(degreeComboBox.getSelectionModel().getSelectedItem()))
                .findFirst()
                .orElse(null);

            try {
                // Fill the TreeView when one degree is selected
                fillTreeView(treeView, selectedDegree, api);
            } catch(Exception except){
                except.printStackTrace();
            }  
        });

        backButton.setOnAction(event -> {

            // Set the visibility of the required elements
            curriculumPeriodComboBox.setVisible(true);
            backButtonAndCourses.setVisible(false);
    
            // Clear ComboBox and treeView
            degreeComboBox.getSelectionModel().clearSelection();
            degrees.clear();
            degreeComboBox.getItems().clear();
            degreeComboBox.setPromptText("Select a degree...");
            treeView.setRoot(null);
            selectedDegree = null;
            selectedModuleFromList = null;

            // Clear the coursesVBox
            rightVBox.getChildren().clear();
        });
        
        // Add all the elements to the left VBox
        leftVBox.getChildren().addAll(firstStackPane, treeView);     
        return leftVBox;
    }
    
    /**
     * Sets a colour to a box.
     * @param c is the box to be changed.
     */
    private void changeComboBoxColour(ComboBox<String> c){
        // Set the cell factory to use a custom ListCell that sets the text color
        c.setCellFactory(listView -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTextFill(Color.WHITE); // Set the text color
            }
        });

        // Set the button cell to use a custom ListCell that sets the text color
        c.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setTextFill(Color.WHITE); // Set the text color
            }
        });
    }

    /**
     * Creates scrollbars and shows them when needed. Also creates checkboxes for the visible courses. 
     * @return the scrolls made.
     */
    private ScrollPane getCoursesVBox(Student student) {
        
        rightVBox = new VBox();
        rightVBox.setPrefWidth(380);
        rightVBox.getStyleClass().add("courses-vbox");
        rightVBox.setMinWidth(500);

        // Wrap the VBox in a ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(rightVBox);
        scrollPane.setFitToWidth(true); // This will ensure that the content is resized when the width of the ScrollPane changes
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Show the horizontal scrollbar when needed
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Show the vertical scrollbar when needed

        iAPI api = new API();
        
        // Event listener for treeView on the left VBox
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue != null) {

                    // Update the selectedModuleFromList variable
                    selectedModuleFromList = newValue.getValue();
               
                    rightVBox.getChildren().clear();
    
                    // Get the data from the API to the selected module
                    JsonObject moduleJSON = api.getModuleById(selectedModuleFromList.getGroupId());
                    JsonElement elem = moduleJSON.get("rule");
    
                    // Find all the course ids
                    List<String> courseIds= new ArrayList<>();
                    findIds(elem, courseIds, "CourseUnitRule", "courseUnitGroupId"); 

                    // If there are courses
                    if(!courseIds.isEmpty()){
                        for (String id : courseIds) {
                            
                            JsonObject j = api.getCourseById(id);    
                            Course course = new Course(
                                j.get("name").getAsJsonObject().has("en") ? j.get("name").getAsJsonObject().get("en").getAsString() : j.get("name").getAsJsonObject().get("fi").getAsString(), 
                                j.get("id").getAsString(), 
                                j.get("groupId").getAsString(), 
                                j.get("credits").getAsJsonObject().get("min").getAsInt(), 
                                j.get("code").getAsString());

                            // Create checkbox for each course
                            CheckBox checkBox = new CheckBox(course.getName() + " " + course.getMinCredits() + "op");
                            // Sets the checkbox true if the course is found from the json already
                            for (Object c : student.getCourses()){
                                if(c.toString().equals(course.getName())|| selectedCourses.contains(course.getCourseCode())){
                                    checkBox.setSelected(true);
                                }
                            }
                            
                            checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                                if (isSelected) {
                                    TreeItem<DegreeModule> courseTreeItem = new TreeItem<>(course);
                                    newValue.getChildren().add(courseTreeItem);
                                    student.setCredits(student.getCredits() + course.getMinCredits());
                                    creditLabel.setText("Credits: " + student.getCredits() + " op");
                                    selectedCourses.add(course.getCourseCode());
                                    student.setCourse(course.toString());
                                } else {
                                    // Find and remove the TreeItem for the unselected course
                                    newValue.getChildren().removeIf(treeItem -> treeItem.getValue().equals(course));
                                    student.setCredits(student.getCredits() - course.getMinCredits());
                                    creditLabel.setText("Credits: " + student.getCredits() + " op");
                                    selectedCourses.remove(course.getCourseCode());
                                    student.removeCourse(course.toString());
                                }
                            });
                            rightVBox.getChildren().add(checkBox);    
                        }    
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
        return scrollPane;
    }
    
    /**
     * Creates quit button for the mainview.
     * @param student is passed for the JSON-file writing
     * @return the created button.
     */
    public Button getQuitButton(Student student) {
        Button button = new Button("Quit");
        button.setOnAction((ActionEvent event) -> {
            try {
               student.writeToFile(String.valueOf(student.getNumber()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.exit();
        });     
        return button;
    }
    
    /**
     * Converts the JsonObject data of all the degrees to a {@literal List<DegreeModule>}.
     * @param modulesJson is the JsonObject containing the data of the degrees.
     * @return a list containing the degreemodules.
     */
    private List<DegreeModule> convertToDegreeList(JsonObject modulesJson){
        List<DegreeModule> degreeList = new ArrayList<>();
        JsonArray searchResults = modulesJson.getAsJsonArray("searchResults");

        for (JsonElement element : searchResults) {
            JsonObject obj = element.getAsJsonObject();
            Module d = new Module(
                obj.get("name").getAsString(),
                obj.get("id").getAsString(),
                obj.get("groupId").getAsString(),
                obj.get("credits").getAsJsonObject().get("min").getAsInt(),
                obj.get("code").getAsString()
            );
            degreeList.add(d);
        }
        return degreeList;
    }

    // Find all the subIds for courses and modules
    private static void findIds(JsonElement jsonElement, List<String> Ids, String rule, String memberName) {
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
    
            if (jsonObject.get("type") != null && jsonObject.get("type").getAsString().equals(rule)) {
                String unitId = jsonObject.get(memberName).getAsString();
                Ids.add(unitId);
            }
    
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                findIds(entry.getValue(), Ids, rule, memberName);
            }
        } else if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
    
            for (JsonElement element : jsonArray) {
                findIds(element, Ids, rule, memberName);
            }
        }
    }
   
    /**
     * Fill the Tree structure using TreeView and TreeItems(nodes).
     * @param treeView is the treeview list of the DegreeModules.
     * @param root is a DegreeModule.
     * @param api is the API data.
     * @throws Exception if creating Tree Item wents wrong.
     */
    private void fillTreeView(TreeView<DegreeModule> treeView, DegreeModule root, iAPI api) throws Exception {
        TreeItem<DegreeModule> rootTreeItem = createTreeItem(root, api);
        treeView.setRoot(rootTreeItem);
    }

    /**
     * Create a TreeItem(node) to the tree.
     * @param module is a DegreeModule.
     * @param api is the API data.
     * @return TreeItem(node).
     */
    private TreeItem<DegreeModule> createTreeItem(DegreeModule module, iAPI api) {
        TreeItem<DegreeModule> treeItem = new TreeItem<>(module);
        if (module != null) {
            try{
                // Get the API data for the current module
                JsonObject moduleJSON = api.getModuleById(module.getGroupId());
                JsonElement elem = moduleJSON.get("rule");

                if(elem.getAsJsonObject() != null){

                    // Fill the submodule ids list
                    List<String> moduleIds = new ArrayList<>();
                    findIds(elem, moduleIds, "ModuleRule", "moduleGroupId"); 
                    
                    // If the module has submodules
                    if (!moduleIds.isEmpty()) {
                        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                        List<CompletableFuture<TreeItem<DegreeModule>>> futureTasks = new ArrayList<>();
        
                        for (String id : moduleIds) {
                            CompletableFuture<TreeItem<DegreeModule>> futureTask = CompletableFuture.supplyAsync(() -> {
                                try {
                                    JsonObject j = api.getModuleById(id);
                                    Module subModule = null;
        
                                    if (j.get("type").getAsString().equals("StudyModule")) {
                                        subModule = new Module(
                                                j.get("name").getAsJsonObject().has("en") ? j.get("name").getAsJsonObject().get("en").getAsString() : j.get("name").getAsJsonObject().get("fi").getAsString(),
                                                j.get("id").getAsString(),
                                                j.get("groupId").getAsString(),
                                                j.get("targetCredits").getAsJsonObject().get("min").getAsInt(),
                                                j.get("code").getAsString());
                                    } else if (j.get("type").getAsString().equals("GroupingModule")) {
                                        subModule = new Module(
                                                j.get("name").getAsJsonObject().has("en") ? j.get("name").getAsJsonObject().get("en").getAsString() : j.get("name").getAsJsonObject().get("fi").getAsString(),
                                                j.get("id").getAsString(),
                                                j.get("groupId").getAsString(),
                                                0,
                                                "null");
                                    }
                                    return createTreeItem(subModule, api);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return null;
                                }
                            }, executor);
                            futureTasks.add(futureTask);
                        }

                        // Wait for all tasks to complete
                        CompletableFuture.allOf(futureTasks.toArray(new CompletableFuture[0])).join();

                        // Add the results to the tree item
                        for (CompletableFuture<TreeItem<DegreeModule>> futureTask : futureTasks) {
                            treeItem.getChildren().add(futureTask.get());
                        }
                        executor.shutdown();
                    }     
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return treeItem;
    }
}
