package app.model.Lesson;

import app.model.User.Student.Student;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lessonPresence")
public class LessonPresence {
    @Id
    private String id;
    private Student student;
    private boolean isPresent;

    public LessonPresence(){}

    public LessonPresence(Student student) {
        this.student = student;
        isPresent=false;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }
}
