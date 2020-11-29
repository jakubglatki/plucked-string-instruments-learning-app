package app.view.GroupsView;

import app.controller.LessonController;
import app.model.Group.Group;
import app.model.Group.GroupRepository;
import app.model.Lesson.Lesson;
import app.model.Lesson.LessonPresence;
import app.model.User.Student.Student;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.time.format.DateTimeFormatter;


public class StudentPresenceView extends VerticalLayout
{
    private Student student;
    private Group group;
    private GroupRepository groupRepository;
    private Grid<Lesson> presenceGrid;
    private Binder<Lesson> binder;
    private Checkbox presenceCheckBox;
    private TextArea topicField;
    private DateTimePicker classDatePicker;
    private Grid.Column<Lesson> presenceColumn;
    private Grid.Column<Lesson> topicColumn;
    private Grid.Column<Lesson> classDateColumn;
    private Grid.Column<Lesson> editorColumn;

    //for Students view
    public StudentPresenceView(Student student, Group group){
        this.student=student;
        this.group=group;
        binder=new Binder<>();
        addPresenceGrid(student, group);
    }

    //for teachers view, adds edit option
    public StudentPresenceView(Student student, Group group, GroupRepository groupRepository){
        this.student=student;
        this.group = group;
        this.groupRepository=groupRepository;
        binder=new Binder<>();
        addPresenceGrid(student, group);
    }


    public Grid<Lesson> getStudentPresenceGrid() {
        return presenceGrid;
    }

    private void addPresenceGrid(Student student, Group group) {
        presenceGrid=new Grid<>();
        if(group.getLessons()!=null)
            presenceGrid.setItems(group.getLessons());
        presenceColumn= presenceGrid.addColumn(new ComponentRenderer<>(lesson->{
            Checkbox checkbox= new Checkbox();
            checkbox.setValue(lesson.getStudentsLessonPresences(student).isPresent());
            if(this.groupRepository==null)
                checkbox.setReadOnly(true);
            else
                setCheckboxChangeEventListener(checkbox, lesson, student, group);
            return checkbox;
        }));
        presenceColumn.setHeader("Obecność");
        topicColumn=presenceGrid.addColumn(Lesson::getTopic).setHeader("Temat lekcji").setWidth("400px");
        setClassDateColumn();

        presenceGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);
        add(presenceGrid);
    }

    private void setCheckboxChangeEventListener(Checkbox checkbox, Lesson lesson, Student student, Group group) {
        checkbox.addClickListener(event->{
            LessonPresence lessonPresence=lesson.getStudentsLessonPresences(student);
            lessonPresence.setPresent(checkbox.getValue());
            LessonController lessonController=new LessonController();
            lesson.getLessonPresences().set(lessonController.getLessonPresenceIndex(lesson,lessonPresence),lessonPresence);
            group.getLessons().set(lessonController.getLessonIndex(group,lesson), lesson);
            groupRepository.save(group);
        });
    }

    private void setClassDateColumn() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        classDateColumn=presenceGrid.addColumn(Lesson->formatter.format(Lesson.getClassDate())).setHeader("Data");
    }

}
