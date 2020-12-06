package app.view.LessonView;

import app.LABEL_NAMES;
import app.controller.GradeController;
import app.model.Group.GroupRepository;
import app.model.Lesson.Lesson;
import app.model.Group.Group;
import app.model.User.Grade.Grade;
import app.model.User.Student.Student;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.*;


public class AddGradeToAllView extends VerticalLayout {

    private GroupRepository groupRepository;

    private Lesson lesson;
    private Group group;
    private Dialog dialog;
    private TextField descriptionField;
    private Grid<Student> studentGrid;
    private Button addButton;
    private Button cancelButton;
    private Collection<IntegerField> gradeFields;
    private HashMap<IntegerField, Student> map;
    private HorizontalLayout buttonLayout;
    private Binder<Grade> binder;

    public AddGradeToAllView(Group group, Lesson lesson,GroupRepository groupRepository, Dialog dialog){
        this.group=group;
        this.lesson=lesson;
        this.groupRepository=groupRepository;
        this.dialog=dialog;

        setDescriptionField();
        setStudentsGrid();
        setAddButton();
        setCloseButton();
        buttonLayout=new HorizontalLayout(addButton,cancelButton);
        add(buttonLayout);
    }

    private void setDescriptionField() {
        descriptionField=new TextField(LABEL_NAMES.DESCRIPTION);
        descriptionField.setRequired(true);
        descriptionField.setValue(lesson.getTopic());
        descriptionField.setWidth("250px");
        add(descriptionField);
    }

    private void setStudentsGrid() {
        studentGrid=new Grid<>();
        if(group.getStudents()!=null)
            studentGrid.setItems(group.getStudents());
        addGradeFieldColumn();
        studentGrid.addColumn(Student::getFirstName).setHeader(LABEL_NAMES.FIRST_NAME);
        studentGrid.addColumn(Student::getLastName).setHeader(LABEL_NAMES.LAST_NAME);

        add(studentGrid);
    }

    private void addGradeFieldColumn() {
        gradeFields = Collections
                .newSetFromMap(new WeakHashMap<>());
        map=new HashMap<IntegerField, Student>();

        studentGrid.addComponentColumn(student -> {
            IntegerField gradeField = new IntegerField();
            gradeField.setMin(1);
            gradeField.setMax(6);
            gradeField.setMaxWidth("2em");
            gradeFields.add(gradeField);
            map.put(gradeField, student);
            return gradeField;
        }).setHeader(LABEL_NAMES.GRADE);
    }


    private void setAddButton() {
        addButton=new Button(LABEL_NAMES.ADD);
        addButton.addClickShortcut(Key.ENTER);
        addButton.addClickListener(event -> {
            dialog.close();
            addGrade();
        });
    }

    private void addGrade() {
        for(IntegerField integerField: gradeFields) {
            if (integerField.getValue() != null) {
                GradeController.addGrade(new Grade(group.getTeacher(), integerField.getValue(), descriptionField.getValue()),
                        map.get(integerField));
            }
        }
        groupRepository.save(group);
    }

    private void setCloseButton() {
        cancelButton=new Button(LABEL_NAMES.CLOSE);
        cancelButton.addClickListener(event -> {
            dialog.close();
        });
    }
}
