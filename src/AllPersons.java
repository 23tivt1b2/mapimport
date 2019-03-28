import java.awt.geom.Point2D;
import java.util.ArrayList;

public class AllPersons {

    private static AllPersons instance;
    private ArrayList<Person> persons;

    private AllPersons() {
        this.persons = new ArrayList<>();
    }

    public ArrayList<Person> getAllPersons() {
        return persons;
    }

    public void addPerson(Person person) {
        this.persons.add(person);
    }

    public static AllPersons getInstance() {
        if(instance == null) {
            instance = new AllPersons();
        }

        return instance;
    }
}
