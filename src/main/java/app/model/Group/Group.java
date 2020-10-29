package app.model.Group;

import app.model.Instrument.Instrument;
import app.model.User.Student.Student;
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
}
