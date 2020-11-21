package app.model.User.Student;

import app.model.User.Grade.Grade;
import app.model.User.User;
import app.model.User.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Users")
public class Student extends User {

    private ArrayList<Grade> grades;

    public Student(){}

    public Student(String firstName, String lastName, String mail, String password, ArrayList<Grade> grades) {
        super(firstName, lastName, mail, password, UserType.STUDENT);
        this.grades = grades;
    }

    public Student(String mail, String password) {
        super(mail, password, UserType.STUDENT);
    }

    public Student(String firstName, String lastName, String mail, String password) {
        super(firstName, lastName, mail, password, UserType.STUDENT);
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<Grade> grades) {
        this.grades = grades;
    }

    public void addGrade(Grade grade){
        ArrayList<Grade> gradesList=new ArrayList<>();
        if(this.getGrades()!=null)
            gradesList=this.getGrades();

        gradesList.add(grade);

        setGrades(gradesList);
    }
}
