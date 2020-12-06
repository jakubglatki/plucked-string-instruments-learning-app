package app.model.User.Grade;

import app.model.User.Student.Student;
import app.model.User.User;

public class Grade {

    private User teacher;
    private int grade;
    private String gradeDescription;

    public Grade(){}

    public Grade(User teacher, int grade) {
        this.teacher = teacher;
        this.grade = grade;
    }

    public Grade(User teacher, String gradeDescription) {
        this.teacher = teacher;
        this.gradeDescription = gradeDescription;
    }

    public Grade( User teacher, int grade, String gradeDescription) {
        this.teacher = teacher;
        this.grade = grade;
        this.gradeDescription = gradeDescription;
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

}
