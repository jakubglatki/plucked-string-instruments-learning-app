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
}
