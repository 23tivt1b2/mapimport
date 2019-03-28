import data.Timetable;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Agenda {

    private Timetable agenda;

    public Agenda() {
        try {
            File file = new File("resources/ChiemTimetable.ttd");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream is = new ObjectInputStream(fis);
            Timetable readTimeTable = (Timetable) is.readObject();
            is.close();
            fis.close();

            this.agenda = readTimeTable;

        } catch(Exception ex) {
            System.out.println(ex);
        }
    }

    public Timetable getAgenda() {
        return this.agenda;
    }
}
