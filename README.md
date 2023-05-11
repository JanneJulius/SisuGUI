# Maven and JavaFX Application for the University Course
 - Implements Graphical User Interface for finding Course and Degree data for the University of Tampere
 - Uses [Kori API](https://sis-tuni.funidata.fi/kori/swagger-ui/index.html?configUrl=/kori/v3/api-docs/swagger-config&filter=true) for data source

## Usage
1. Configure the running environment
   1. Install [JDK](https://www.oracle.com/java/technologies/downloads/)
   2. Configure some IDE, for example VSCode, and download at least the Extension "Extension Pack for Java"
   3. Head to the root of this repository with VSCode
   4. From the VSCode Explorer, select Maven>Sisu>Plugins>javafx>run

2. If everything went fine, the SettingsDialog should open. Input some user information

![Use cases](/pics/SettingsDialog.png)

3. Main View of the application opens

![Use cases](/pics/MainTab.png)

4. User can select the curriculum period and then select some degree. The left box of the main view is filled with the modules of that selected degree
  
![Use cases](/pics/TreeView.png)

5. When selecting some module from the left box, the right box is filled with the courses of that selected module. Courses are with the checkboxes and user can select courses as completed. The credits calculator on the top right corner is then updated.
6. Program writes a JSON for spesific user that includes the completed courses. Indetification is done by studentnumber. Hence, the application remembers the course achievements on the re-runs.

