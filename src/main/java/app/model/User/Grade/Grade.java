package app.model.User.Grade;

import app.model.User.Student.Student;
import app.model.User.Student.StudentRepository;
import app.model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Grades")
public class Grade {

    @Id
    private String Id;
    private Student student;
    private User teacher;
    private int grade;
    private String gradeDescription;

    public Grade(){}

    public Grade(User teacher, int grade) {
        this.teacher = teacher;
        this.grade = grade;

       // addGradeToStudentsGradeList(student);
    }

    public Grade(User teacher, String gradeDescription) {
        this.teacher = teacher;
        this.gradeDescription = gradeDescription;


      //  addGradeToStudentsGradeList(student);
    }

    public Grade( User teacher, int grade, String gradeDescription) {
        this.teacher = teacher;
        this.grade = grade;
        this.gradeDescription = gradeDescription;

      //  addGradeToStudentsGradeList(student);
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getGradeDescription() {
        return gradeDescription;
    }

    public void setGradeDescription(String gradeDescription) {
        this.gradeDescription = gradeDescription;
    }

    private void addGradeToStudentsGradeList(Student student){
        ArrayList<Grade> gradeList = new ArrayList<>();
        gradeList.add(this);
        this.student.setGrades(gradeList);
    }
}
