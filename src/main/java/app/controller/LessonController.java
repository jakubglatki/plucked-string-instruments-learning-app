package app.controller;

import app.model.Group.Group;
import app.model.Lesson.Lesson;

import java.util.ArrayList;
import java.util.Collections;

public class LessonController {

    public LessonController(){}

    //the lessons will all be sorted by their dates
    public Group addLessonToGroup(Group group, Lesson lesson){
        ArrayList<Lesson> groupsLesson = new ArrayList<>();
        if(group.getLessons()!=null)
            groupsLesson=group.getLessons();
        groupsLesson.add(lesson);
        Collections.sort(groupsLesson);
        group.setLessons(groupsLesson);
        return group;
    }
}
