package app.model.Lesson;

import app.model.Group.Group;
import app.model.User.Student.Student;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Lesson {
    private ArrayList<LessonPresence> lessonPresences;
    private String topic;
    private Date classDate;


    public Lesson(Group group, String topic, Date classDate){
        this.topic=topic;
        this.classDate=classDate;
        lessonPresences=new ArrayList<>();
        setLessonPresences(group);
    }

    private void setLessonPresences(Group group) {
        for (Student student : group.getStudents()) {
            LessonPresence lessonPresence=new LessonPresence(student);
            lessonPresences.add(lessonPresence);
        }
    }
    
    public ArrayList<LessonPresence> getLessonPresences() {
        return lessonPresences;
    }

    public void setLessonPresences(ArrayList<LessonPresence> lessonPresences) {
        this.lessonPresences = lessonPresences;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getClassDate() {
        return classDate;
    }

    public void setClassDate(Date classDate) {
        this.classDate = classDate;
    }

}
