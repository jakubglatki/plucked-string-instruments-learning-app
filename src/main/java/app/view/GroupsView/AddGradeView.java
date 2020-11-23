package app.view.GroupsView;

import app.model.Group.Group;
import app.model.Group.GroupRepository;
import app.model.User.Grade.Grade;
import app.model.User.Student.Student;
import app.model.User.Student.StudentRepository;
import app.model.User.Teacher.Teacher;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import org.springframework.beans.factory.annotation.Autowired;


public class AddGradeView extends VerticalLayout {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private StudentRepository studentRepository;

    private Student student;
    private Teacher teacher;
    private Group group;
    private IntegerField gradeField;
    private TextField descriptionField;
    private Button addButton;
    private Button closeButton;
    private HorizontalLayout buttonLayout;
    private HorizontalLayout fieldsLayout;
    private Dialog dialog;
    private Binder<Grade> binder;

    public AddGradeView(Student student, Teacher teacher, Group group, StudentRepository studentRepository, GroupRepository groupRepository, Dialog dialog){
        this.student=student;
        this.teacher=teacher;
        this.group=group;
        this.studentRepository=studentRepository;
        this.groupRepository=groupRepository;
        this.dialog=dialog;
        binder=new Binder<>();

        setFields();
        setAddButton();
        setCloseButton();
        setBinders();
        setLayout();

    }

    private void setBinders() {
        binder.forField(gradeField)
                .withValidator(new IntegerRangeValidator(
                        "Dodaj ocenę " +
                                "(1-6)",1,6))
                .bind(Grade::getGrade, Grade::setGrade);

        binder.forField(descriptionField)
                .withValidator(new StringLengthValidator(
                        "Dodaj opis oceny", 1, null))
                .bind(Grade::getGradeDescription, Grade::setGradeDescription);
    }

    private void setLayout() {
        fieldsLayout=new HorizontalLayout(gradeField,descriptionField);
        buttonLayout=new HorizontalLayout(addButton,closeButton);
        add(fieldsLayout, buttonLayout);
    }


    private void setAddButton() {
        addButton=new Button("Dodaj");
        addButton.addClickShortcut(Key.ENTER);
            addButton.addClickListener(event -> {
                dialog.close();
                addGrade();
            });
    }

    private void setCloseButton() {
        closeButton=new Button("Wyjdź");
        closeButton.addClickListener(event -> {
            dialog.close();
        });
    }
    private void addGrade() {
        if(!this.gradeField.getValue().equals(null) && !this.descriptionField.getValue().isBlank()) {
            student.addGrade(new Grade(teacher, gradeField.getValue(), descriptionField.getValue()));
            groupRepository.save(group);
            studentRepository.save(student);
        }
    }


    private void setFields() {
        gradeField=new IntegerField("Ocena");
        gradeField.setMin(1);
        gradeField.setMax(6);
        gradeField.setMaxWidth("3em");

        descriptionField=new TextField("Opis");
        descriptionField.setWidth("50em");
    }

}
