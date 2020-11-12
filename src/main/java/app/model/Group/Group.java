package app.model.Group;

import app.model.Instrument.Instrument;
import app.model.User.Student.Student;
import app.model.User.Teacher.Teacher;
import app.model.User.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Document(collection = "Groups")
public class Group {

    @Id
    private String Id;
    private String name;
    private Instrument instrument;
    private User teacher;
    private List<Student> students;
    private List<Date> classesDates;

    public Group(String name, Instrument instrument, User teacher, List<Student> students, List<Date> classesDates) {
        this.name = name;
        this.instrument = instrument;
        this.teacher = teacher;
        this.students = students;
        this.classesDates = classesDates;
    }

    public Group(String name, Instrument instrument, User teacher) {
        this.name = name;
        this.instrument = instrument;
        this.teacher = teacher;
    }

    public Group(String name, Instrument instrument, Teacher teacher, List<Student> students){
        this(name,instrument,teacher);
        this.students=students;
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Date> getClassesDates() {
        return classesDates;
    }

    public void setClassesDates(List<Date> classesDates) {
        this.classesDates = classesDates;
    }
}
