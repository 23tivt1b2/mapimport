package data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer {

    private Timetable timeTable;

    public Serializer() {
        this.timeTable = null;
    }

    public Serializer(Timetable timeTable) {
        this.timeTable = timeTable;
    }

    /**
     * Serialized the object using objectIO, the file is saved in the data directory.
     * Make sure timeTable is up to date, using the method setTimeTable.
     *
     * @param name  the name of the file, under what name it will be saved.
     * @throws Exception if file doesn't exists or if timeTalbe in this object is null.
    */
    public void serializeTimeTable(String name) {
        try {
            if(this.timeTable == null) {
                throw new Exception("TimeTable not filled");
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data/" + name + ".bin"));
            objectOutputStream.writeObject(this.timeTable);
            objectOutputStream.close();
        } catch(Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * Deserialzes the object using objectIO, the TimeTable is returnd after reading the object.
     *
     * @param name  the name of the file, under what name it will be saved.
     * @return TimeTable object that is saved in the directory data with the given name. returns null if the file is not found.
     * @throws Exception if file doesn't exists.
     */
    public Timetable deserializeTimeTable(String name) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("data/" + name + ".bin"));
            Timetable readTimeTable = (Timetable) objectInputStream.readObject();
            objectInputStream.close();

            return readTimeTable;
        } catch(Exception ex) {
            System.out.println(ex);
        }

        return null;
    }

    public Timetable getTimeTable() {
        return  timeTable;
    }

    public void setTimeTable(Timetable timeTable) {
        this.timeTable = timeTable;
    }
}