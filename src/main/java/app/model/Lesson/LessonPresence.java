package app.model.Lesson;

import app.model.User.Student.Student;

public class LessonPresence {
    private Student student;
    private boolean isPresent;

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
