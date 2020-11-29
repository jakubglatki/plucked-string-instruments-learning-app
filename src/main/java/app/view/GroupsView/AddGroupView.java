package app.view.GroupsView;

import app.model.Group.Group;
import app.model.Group.GroupRepository;
import app.model.Instrument.Instrument;
import app.model.Instrument.InstrumentRepository;
import app.model.User.Grade.Grade;
import app.model.User.Student.Student;
import app.model.User.Student.StudentRepository;
import app.model.User.Teacher.Teacher;
import app.model.User.UserType;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;

import java.util.ArrayList;
import java.util.List;


public class AddGroupView extends VerticalLayout {

    private InstrumentRepository instrumentRepository;
    private StudentRepository studentRepository;
    private HorizontalLayout fieldsLayout;
    private TextField nameField;
    private ComboBox<Instrument> instrumentComboBox;
    private Grid<Student> studentsGrid;
    private GroupRepository groupRepository;
    private Binder<Group> binder;
    private Dialog dialog;
    private HorizontalLayout buttonsLayout;
    private Button addGroupButton;
    private Button cancelButton;
    private Teacher teacher;

    public AddGroupView(Teacher teacher, Dialog dialog, StudentRepository studentRepository, GroupRepository groupRepository, InstrumentRepository instrumentRepository){
        this.teacher=teacher;
        this.dialog=dialog;
        this.studentRepository=studentRepository;
        this.groupRepository=groupRepository;
        this.instrumentRepository=instrumentRepository;
        setFields();
        setGrid();
        setButtons();
    }

    private void setFields() {
        nameField=new TextField();
        nameField.setLabel("Nazwa grupy");
        setNameFieldBinder();

        instrumentComboBox=new ComboBox<>();
        instrumentComboBox.setLabel("Instrument");
        instrumentComboBox.setItemLabelGenerator(Instrument::getName);
        instrumentComboBox.setItems(instrumentRepository.findAll());
        instrumentComboBox.setAllowCustomValue(false);
        instrumentComboBox.setRequired(true);
        instrumentComboBox.setPlaceholder("Wybierz instrument");

        fieldsLayout=new HorizontalLayout(nameField,instrumentComboBox);
        add(fieldsLayout);
    }

    private void setNameFieldBinder() {
        binder=new Binder<>();
        binder.forField(nameField)
                .withValidator(new StringLengthValidator(
                        "Wybierz nazwę grupy", 1, null))
                .bind(Group::getName, Group::setName);
    }

    private void setGrid() {
        studentsGrid=new Grid<>();
        List<Student> students;
        students=studentRepository.findByUserType(UserType.STUDENT);
        studentsGrid.setItems(students);
        studentsGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        studentsGrid.addColumn(Student::getFirstName).setHeader("Imię");
        studentsGrid.addColumn(Student::getLastName).setHeader("Nazwisko");
        studentsGrid.addColumn(Student::getMail).setHeader("E-mail");

        studentsGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);
        add(studentsGrid);
    }

    private void setButtons() {
        addGroupButton=new Button("Dodaj");
        addGroupButton.addClickShortcut(Key.ENTER);
        addGroupButton.addClickListener(event -> {
            addNewGroup();
        });
        cancelButton=new Button("Anuluj");
        cancelButton.addClickListener(event -> {
           dialog.close();
        });
        buttonsLayout=new HorizontalLayout(addGroupButton,cancelButton);
        add(buttonsLayout);

    }

    private void addNewGroup() {
        if(!nameField.getValue().isEmpty()&&instrumentComboBox.getValue()!=null) {
            ArrayList<Student> students = new ArrayList<>();
            students.addAll(studentsGrid.getSelectedItems());
            Group group = new Group(nameField.getValue(), instrumentComboBox.getValue(), teacher, students);

            //so no grades from previous groups will move to another
            for (Student student: group.getStudents()
                 ) {
                student.setGrades(null);
            }
            groupRepository.save(group);
            dialog.close();
        }
    }

}
