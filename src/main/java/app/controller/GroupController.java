package app.controller;

import app.model.Group.Group;
import app.model.User.Student.Student;

import java.util.ArrayList;

public class GroupController {
    public GroupController(){}

    public Group addStudentsToGroup(Group group, ArrayList<Student> students){
        ArrayList<Student> groupsStudents = new ArrayList<>();
        if(group.getStudents()!=null){
            groupsStudents=group.getStudents();
            for (Student student:students){
                groupsStudents.add(student);
            }
        }
        else groupsStudents=students;

        group.setStudents(groupsStudents);
        return group;
    }

    public Group deleteStudentsFromGroup(Group group, ArrayList<Student> students){
        ArrayList<Student> groupStudents= (ArrayList<Student>) group.getStudents().clone();
        for(int i=0;i<groupStudents.size();i++){
            for(Student student: students){
                if(groupStudents.get(i).getMail().contains(student.getMail()))
                    groupStudents.remove(student);
            }
        }
        group.setStudents(groupStudents);
        return group;
    }

    public Group editStudentInGroup(Group group, Student student){
        int index=0;
        int i=0;
        ArrayList<Student> students=group.getStudents();
        for(Student groupStudent: students){
            if(student.getMail()==groupStudent.getMail())
                index=i;
            i++;
        }
        students.set(index, student);
        group.setStudents(students);
        return group;
    }
}
