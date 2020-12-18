package app.model.Group;

import app.model.Instrument.Instrument;
import app.model.Lesson.Lesson;
import app.model.User.Student.Student;
import app.model.User.Teacher.Teacher;
import app.model.User.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;


@Document(collection = "Groups")
public class Group {

    @Id
    private String id;
    private String name;
    private Instrument instrument;
    private User teacher;
    private ArrayList<Student> students;
    private ArrayList<Lesson> lessons;

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Group(){}

    public Group(String name, Instrument instrument, User teacher) {
        this.name = name;
        this.instrument = instrument;
        this.teacher = teacher;
    }

    public Group(String name, Instrument instrument, Teacher teacher, ArrayList<Student> students){
        this(name,instrument,teacher);
        this.students=students;
        this.lessons= new ArrayList<>();
    }

    public Group(String name, Instrument instrument, User teacher, ArrayList<Student> students, ArrayList<Lesson> lessons) {
        this.name = name;
        this.instrument = instrument;
        this.teacher = teacher;
        this.students = students;
        this.lessons = lessons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
}
