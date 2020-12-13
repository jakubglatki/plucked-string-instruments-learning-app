package app.model.Lesson;

import app.controller.LessonController;
import app.model.Group.Group;
import app.model.Instrument.Instrument;
import app.model.User.Student.Student;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;

@Document(collection = "Lessons")
public class Lesson implements Comparable<Lesson>{

    @Id
    private String id;
    private ArrayList<LessonPresence> lessonPresences;
    private String topic;
    private LocalDateTime classDate;
    private boolean isActive;
    private Instrument instrument;

    private int nbOfLines=2;

    public Lesson(){}

    public Lesson(Group group, String topic, LocalDateTime classDate){
        this.topic=topic;
        this.classDate=classDate;
        this.isActive=false;
        this.instrument=group.getInstrument();
        lessonPresences=new ArrayList<>();
        setLessonPresences(group);
        this.instrument= LessonController.setLessonsInstrumentFields(this);
    }

    private void setLessonPresences(Group group) {
        for (Student student : group.getStudents()) {
            LessonPresence lessonPresence=new LessonPresence(student);
            lessonPresences.add(lessonPresence);
        }
    }

    public LessonPresence getStudentsLessonPresences(Student student)
    {
        for(LessonPresence lessonPresence: this.getLessonPresences())
            if(student.getMail().contains(lessonPresence.getStudent().getMail())){
                return lessonPresence;
            }
        return lessonPresences.get(0);
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

    public LocalDateTime getClassDate() {
        return classDate;
    }

    public void setClassDate(LocalDateTime classDate) {
        this.classDate = classDate;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }


    public int getNbOfLines() {
        return nbOfLines;
    }

    public void setNbOfLines(int nbOfLines) {
        this.nbOfLines = nbOfLines;
    }


    @Override
    public int compareTo(Lesson o) {
        if (getClassDate() == null || o.getClassDate() == null)
            return 0;
        return getClassDate().compareTo(o.getClassDate());
    }
}
