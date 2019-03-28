package gui.pages.timetable.menus;

import data.Timetable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Load {
    public void start(gui.pages.timetable.Timetable timetable) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Timetable");
        File file = fileChooser.showOpenDialog(stage);
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream is = new ObjectInputStream(fis);
            Timetable readTimeTable = (Timetable) is.readObject();
            is.close();
            fis.close();
            timetable.update(readTimeTable);

        } catch(Exception ex) {
            System.out.println(ex);
        }
    }
}
