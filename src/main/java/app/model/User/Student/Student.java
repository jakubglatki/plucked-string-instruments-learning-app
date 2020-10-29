package app.model.User.Student;

import app.model.User.Grade.Grade;
import app.model.User.User;
import app.model.User.UserType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Users")
public class Student extends User {

    private List<Grade> grades;

    public Student(String firstName, String lastName, String mail, String password, List<Grade> grades) {
        super(firstName, lastName, mail, password, UserType.STUDENT);
        this.grades = grades;
    }

    public Student(String mail, String password) {
        super(mail, password, UserType.STUDENT);
    }

    public Student(String firstName, String lastName, String mail, String password) {
        super(firstName, lastName, mail, password, UserType.STUDENT);
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
}