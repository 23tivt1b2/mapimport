package gui.pages.timetable.menus;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Save {

    public void start(data.Timetable timetable) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Timetable");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Timetable data(*.ttd)", ".ttd"));
        fileChooser.setInitialFileName(timetable.getName());
        File file = fileChooser.showSaveDialog(stage);
        try {
            if(!file.getName().contains(".")) {
                file = new File(file.getAbsolutePath());
            }
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(timetable);

        } catch(Exception ex) {
            System.out.println(ex);
        }
    }
}
