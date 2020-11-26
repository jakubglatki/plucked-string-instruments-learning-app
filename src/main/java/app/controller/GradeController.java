package app.controller;

import app.model.Group.Group;
import app.model.User.Grade.Grade;
import app.model.User.Student.Student;

import java.util.ArrayList;
import java.util.Optional;

public class GradeController {

    public GradeController(){}

    public Student editStudentsGrade(Grade oldGrade, Grade newGrade, Group group, Student student){
        ArrayList<Grade> grades= student.getGrades();
        int index=0;
        int i=0;
        for(Grade grade: grades){
            if(grade==oldGrade)
                index=i;
            i++;
        }
        grades.set(index, newGrade);
        student.setGrades(grades);
        return student;
    }
}
