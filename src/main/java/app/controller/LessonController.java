package app.controller;

import app.model.Group.Group;
import app.model.Lesson.Lesson;
import app.model.Lesson.LessonPresence;
import app.model.User.Student.Student;

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

    public static int getLessonIndex(Group group, Lesson lesson){

        ArrayList<Lesson> lessons= new ArrayList<>();
        lessons=group.getLessons();
        int index = 0;
        int i=0;
        for(Lesson groupLessons : lessons){
            if(groupLessons.getTopic().equals(lesson.getTopic())) {
                index = i;
                break;
            }
            i++;
        }
        return index;
    }

    public int getLessonPresenceIndex(Lesson lesson, LessonPresence lessonPresence){

        ArrayList<LessonPresence> lessonPresences= new ArrayList<>();
        lessonPresences=lesson.getLessonPresences();
        int index = 0;
        int i=0;
        for(LessonPresence presences : lessonPresences){
            if(lessonPresence.getStudent().getMail().equals(presences.getStudent().getMail())) {
                index = i;
                break;
            }
            i++;
        }
        return index;
    }
}
