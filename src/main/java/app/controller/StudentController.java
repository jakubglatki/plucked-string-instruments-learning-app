package app.controller;

import app.model.Group.Group;
import app.model.User.Grade.Grade;
import app.model.User.Student.Student;
import app.model.User.Student.StudentRepository;
import app.model.User.UserType;
import com.vaadin.flow.data.provider.DataProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class StudentController  {

    public StudentController(){}

    public ArrayList<Student> setStudentsListForAdding(Group group, StudentRepository studentRepository) {
        ArrayList<Student> students;
        ArrayList<Student> groupStudents;
        ArrayList<Student> studentsToReturn;
        groupStudents=group.getStudents();
        students= studentRepository.findByUserType(UserType.STUDENT);
        studentsToReturn= (ArrayList<Student>) students.clone();
        for (int i=0;i<groupStudents.size();i++) {
            if(groupStudents!=null) {
                for(Student student:students){
                    if(groupStudents.get(i).getMail().contains(student.getMail())){
                        studentsToReturn.remove(student);
                    }
                }
            }
        }
        return studentsToReturn;
    }


    public void addGrade(Grade grade, Student student){
        ArrayList<Grade> gradesList=new ArrayList<>();
        if(student.getGrades()!=null)
            gradesList=student.getGrades();

        gradesList.add(grade);

        student.setGrades(gradesList);
    }
}
