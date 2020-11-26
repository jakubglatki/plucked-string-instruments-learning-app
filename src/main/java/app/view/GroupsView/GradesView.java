package app.view.GroupsView;

import app.controller.GradeController;
import app.controller.GroupController;
import app.model.Group.Group;
import app.model.Group.GroupRepository;
import app.model.User.Grade.Grade;
import app.model.User.Grade.GradeRepository;
import app.model.User.Student.Student;
import app.model.User.Student.StudentRepository;
import app.model.User.Teacher.Teacher;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.WeakHashMap;


public class GradesView
{
    private Student student;
    private Teacher teacher;
    private Group group;
    private GroupRepository groupRepository;
    private StudentRepository studentRepository;
    private Grid<Grade> gradeGrid;
    private Binder<Grade> binder;
    private Editor<Grade> editor;
    private IntegerField gradeTextField;
    private TextArea descriptionTextField;
    private Grid.Column<Grade> gradeColumn;
    private Grid.Column<Grade> descriptionColumn;
    private Grid.Column<Grade> teacherColumn;
    private Grid.Column<Grade> editorColumn;
    private Collection<Button> editButtons;
    private Button saveButton;
    private Button cancelButton;
    private HorizontalLayout buttonLayout;
    private Grade editedGrade;

    //for Students view
    public GradesView(Student student){
        this.student=student;
        binder=new Binder<>();
        addGradesGrid(student);
    }

    //for teachers view, adds edit option
    public GradesView(Student student, Teacher teacher, Group group, GroupRepository groupRepository, StudentRepository studentRepository){
        this.student=student;
        this.teacher=teacher;
        this.group = group;
        this.groupRepository=groupRepository;
        this.studentRepository=studentRepository;
        binder=new Binder<>();
        addGradesGrid(student);
        setEditColumn();
    }


    public Grid<Grade> getGradeGrid() {
        return gradeGrid;
    }

    private void addGradesGrid(Student student) {
        gradeGrid=new Grid<>();
        if(student.getGrades()!=null)
            gradeGrid.setItems(student.getGrades());
        gradeColumn= gradeGrid.addColumn(Grade::getGrade).setHeader("Ocena");
        descriptionColumn=gradeGrid.addColumn(Grade::getGradeDescription).setHeader("Opis").setWidth("400px");
        teacherColumn=gradeGrid.addColumn(Grade->Grade.getTeacher().getFirstName()+" " +Grade.getTeacher().getLastName()).setHeader("Nauczyciel");
        gradeGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);
    }

    private void setEditColumn() {
        editor= gradeGrid.getEditor();
        editor.setBinder(binder);
        setGradeColumnBinder();
        addEditColumn();
    }


    private void setGradeColumnBinder() {
        gradeTextField=new IntegerField();
        descriptionTextField=new TextArea();
        descriptionTextField.setWidth(descriptionColumn.getWidth());
        binder.forField(gradeTextField)
                .withValidator(new IntegerRangeValidator(
                        "Podaj ocenę " +
                                "(1-6)",1,6))
                .bind(Grade::getGrade, Grade::setGrade);
        gradeColumn.setEditorComponent(gradeTextField);

        binder.forField(descriptionTextField)
                .withValidator(new StringLengthValidator(
                        "Podaj opis oceny", 1, null))
                .bind(Grade::getGradeDescription, Grade::setGradeDescription);
        descriptionColumn.setEditorComponent(descriptionTextField);
    }

    private void addEditColumn() {

        editButtons = Collections
                .newSetFromMap(new WeakHashMap<>());

      editorColumn = gradeGrid.addComponentColumn(grade -> {
            Button edit = new Button("Edytuj");
            edit.addClassName("edit");
            edit.addClickListener(e -> {
                editor.editItem(grade);
                editedGrade=grade;
                gradeTextField.focus();
            });
            edit.setEnabled(!editor.isOpen());
            editButtons.add(edit);
            return edit;
        });

      setEditorButtons();
    }

    private void setEditorButtons() {
        editor.addOpenListener(e -> editButtons.stream()
                .forEach(button -> button.setEnabled(!editor.isOpen())));
        editor.addCloseListener(e -> editButtons.stream()
                .forEach(button -> button.setEnabled(!editor.isOpen())));

        saveButton = new Button("Zapisz", e -> editor.save());
        saveButton.addClassName("save");

        cancelButton = new Button("Anuluj", e -> editor.cancel());
        buttonLayout=new HorizontalLayout(saveButton,cancelButton);
        editorColumn.setEditorComponent(buttonLayout);

        saveButton.addClickListener(editorSaveEvent -> {
            addEvent();
            editor.closeEditor();
        });
    }

    private void addEvent() {
        GradeController gradeController=new GradeController();
        student=gradeController.editStudentsGrade(editedGrade,
                new Grade(teacher, gradeTextField.getValue(), descriptionTextField.getValue()), group, student);
        GroupController groupController= new GroupController();
        group=groupController.editStudentInGroup(group,student);
        studentRepository.save(student);
        groupRepository.save(group);
    }

}
