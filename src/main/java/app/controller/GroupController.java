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
}
