package fi.tuni.prog3.sisu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;


/**
 * JavaFX Sisu
 */
public class Sisu extends Application {

    // Tab data
    private TabPane tabPane;
    private Tab settingsTab;
    private Tab mainViewTab;

    private MainView mainView = new MainView();
    
    private Settings settings = new Settings();
    
    private Student student = new Student();

    /**
     * Main function 
     */
    public static void main(String[] args) {
        launch();
    }

    //Shows settings dialog and moves forward to main window
    @Override
    public void start(Stage primaryStage) {

        Dialog<ButtonType> settingsDialog = settings.getSettingsDialog(student);
        settingsDialog.showAndWait();
        
        // User canceled the settings dialog, exit the application
        if (settingsDialog.getResult() == ButtonType.CANCEL) {
            Platform.exit();
            return;
        }

        tabPane = new TabPane();

        mainViewTab = new Tab("Main View");
        mainViewTab.setContent(mainView.createMainViewContent(student));
        tabPane.getTabs().add(mainViewTab);

        settingsTab = new Tab("Settings");
        settingsTab.setContent(settings.createSettingsContent(student));
        tabPane.getTabs().add(settingsTab);

        // Disable the close button for the tabs
        settingsTab.setClosable(false);
        mainViewTab.setClosable(false);
        
        // Set the tab pane as the root node of the scene
        Scene scene = new Scene(tabPane, 800, 500);

        // Load the CSS file
        scene.getStylesheets().add(getClass().getClassLoader().getResource("styles.css").toExternalForm());
        
        // Render the primary scene
        primaryStage.setScene(scene);
        primaryStage.setTitle("SisuGUI");
        primaryStage.show();
    }    
}