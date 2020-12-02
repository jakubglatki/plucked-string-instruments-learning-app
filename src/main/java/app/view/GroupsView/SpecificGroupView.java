package app.view.GroupsView;

import app.controller.GroupController;
import app.model.Group.Group;
import app.model.Group.GroupRepository;
import app.model.Instrument.Instrument;
import app.model.Lesson.Lesson;
import app.model.Lesson.LessonRepository;
import app.model.User.Student.Student;
import app.model.User.Student.StudentRepository;
import app.model.User.Teacher.Teacher;
import app.model.User.User;
import app.model.User.UserRepository;
import app.model.User.UserType;
import app.view.Layout.InternalLayout;
import app.view.LessonView.AddLessonView;
import app.view.LessonView.LessonView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Collections;
import java.util.WeakHashMap;

@Route(value="specificGroup", layout = InternalLayout.class)
@CssImport("./styles/shared-styles.css")
public class SpecificGroupView extends VerticalLayout {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    private Group group;
    private User currentUser;
    private Label infoLabel;
    private HorizontalLayout infoLayout;
    private HorizontalLayout buttonLayout;
    private HorizontalLayout studentsButtonsLayout;
    private TextField nameField;
    private TextField instrumentField;
    private TextField teacherField;
    private Grid<Student> studentGrid;
    private Button gradesButton;
    private Button studentsGradesButton;
    private Button studentPresenceButton;
    private Button addGradeButton;
    private Button addLessonButton;
    private Dialog addLessonDialog;
    private Button addStudentsButton;
    private Button removeStudentsButton;
    private AddGradeView addGradeView;
    private Dialog dialogPresence;
    private Dialog dialogGradesView;
    private Dialog dialogAddGrade;
    private Dialog dialogEditStudents;
    private HorizontalLayout seeLessonLayout;
    private ComboBox<Lesson> lessonTopicComboBox;
    private DateTimePicker lessonDatePicker;

    public SpecificGroupView()
    {
        infoLabel=new Label();
        add(infoLabel);
    }

    @Override
    public void onAttach(AttachEvent event){
        try {
            currentUser = userRepository.findByMail(VaadinSession.getCurrent().getAttribute("user").toString());
            group= (Group) VaadinSession.getCurrent().getAttribute("group");
            setTextFields();
            setLessonLayout();
            setStudentGrid();
            if(currentUser.getUserType()==UserType.STUDENT) {
                studentsButtonsLayout = new HorizontalLayout();
                setGradesButton();
                setPresenceButton();
                add(studentsButtonsLayout);
            }
            if(currentUser.getUserType()==UserType.TEACHER) {
                buttonLayout=new HorizontalLayout();
                setAddStudentsButton();
                setRemoveStudentsButton();
                setAddLessonButton();
                add(buttonLayout);
            }
        }
        catch (Exception e){infoLabel.setText("Zaloguj się, aby uzyskać dostęp do swoich grup");}
    }



    private void setTextFields() {
        nameField=new TextField("Nazwa");
        nameField.setValue(group.getName());
        nameField.setReadOnly(true);
        instrumentField=new TextField("Instrument");
        instrumentField.setValue(group.getInstrument().getName());
        instrumentField.setReadOnly(true);
        teacherField=new TextField("Nauczyciel");
        teacherField.setValue(group.getTeacher().getFirstName()+" "+group.getTeacher().getLastName());
        teacherField.setReadOnly(true);
        infoLayout=new HorizontalLayout(nameField,instrumentField,teacherField);
        add(infoLayout);
        setHorizontalComponentAlignment(Alignment.START, infoLayout);
    }

    private void setLessonLayout() {
        lessonTopicComboBox=new ComboBox<>();
        lessonTopicComboBox.setLabel("Temat lekcji");
        lessonTopicComboBox.setItems(group.getLessons());
        lessonTopicComboBox.setItemLabelGenerator(Lesson::getTopic);
        lessonTopicComboBox.setAllowCustomValue(false);

        lessonDatePicker=new DateTimePicker();
        lessonDatePicker.setLabel("Data");
        lessonDatePicker.setReadOnly(true);



        seeLessonLayout=new HorizontalLayout(lessonTopicComboBox, lessonDatePicker);
        setLessonLayoutClickListener();
        seeLessonLayout.addClassName("group-grid");
        infoLayout.add(seeLessonLayout);
    }

    private void setLessonLayoutClickListener(){
        lessonTopicComboBox.addValueChangeListener(comboBoxLessonComponentValueChangeEvent ->
        {lessonDatePicker.setValue(lessonTopicComboBox.getValue().getClassDate());
            if(lessonDatePicker.getValue()!=null){
                seeLessonLayout.addClickListener(e-> {
                    VaadinSession.getCurrent().setAttribute("lesson", lessonTopicComboBox.getValue());
                    UI.getCurrent().navigate(LessonView.class);
                });
            }});
    }

    private void setStudentGrid(){
        studentGrid=new Grid<>();
        studentGrid.setItems(group.getStudents());
        studentGrid.addColumn(Student::getFirstName).setHeader("Imię").setResizable(true);
        studentGrid.addColumn(Student::getLastName).setHeader("Nazwisko").setResizable(true);
        if(currentUser.getUserType()==UserType.TEACHER) {
            setSeeStudentsGradesButton();
            setAddStudentsGradesButton();
            setSeeStudentsPresenceButton();
        }
        studentGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);
        add(studentGrid);
    }


    private void setSeeStudentsGradesButton() {
        Collection<Button> seeGradesButtons= Collections.newSetFromMap(new WeakHashMap<>());
        studentGrid.addComponentColumn(student->{
            studentsGradesButton=new Button("Pokaż oceny");
            studentsGradesButton.addClickListener(e->{
                showGradesClickListener(student);
            });
            seeGradesButtons.add(studentsGradesButton);
            return studentsGradesButton;
        });
    }

    private void setSeeStudentsPresenceButton() {
        Collection<Button> seePresenceButtons= Collections.newSetFromMap(new WeakHashMap<>());
        studentGrid.addComponentColumn(student->{
            studentPresenceButton=new Button("Pokaż obecność");
            studentPresenceButton.addClickListener(e->{
                showPresenceClickListener(student);
            });
            seePresenceButtons.add(studentPresenceButton);
            return studentPresenceButton;
        });
    }

    private void setAddStudentsGradesButton() {
        Collection<Button> addGradesButtons= Collections.newSetFromMap(new WeakHashMap<>());
        studentGrid.addComponentColumn(student->{
            setAddGradeButton(student);
            addGradesButtons.add(addGradeButton);
            return addGradeButton;
        });
    }

    private void setGradesButton(){
        gradesButton=new Button("Pokaż moje oceny");
        gradesButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        gradesButton.addClickListener(e->{
            showGradesClickListener((Student)currentUser);
        });
        studentsButtonsLayout.add(gradesButton);
    }

    private void setPresenceButton(){
        studentPresenceButton= new Button("Pokaż moją obecność");
        studentPresenceButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        studentPresenceButton.addClickListener(event -> {
            showPresenceClickListener((Student)currentUser);
        });
        studentsButtonsLayout.add(studentPresenceButton);
    }


    private void showGradesClickListener(Student student) {
        dialogGradesView=new Dialog();
        GradesView gradesView;
        if(currentUser.getUserType()==UserType.TEACHER)
            gradesView=new GradesView(student, (Teacher) currentUser, group, groupRepository, studentRepository);
        else
            gradesView=new GradesView(student,group);
        dialogGradesView.setWidth("1100px");
        dialogGradesView.add(gradesView.getGradeGrid());
        if(currentUser.getUserType()==UserType.TEACHER) {
            setAddGradeButton(student);
            dialogGradesView.add(addGradeButton);
        }
        dialogGradesView.open();
    }


    private void showPresenceClickListener(Student student) {
        dialogPresence=new Dialog();
        StudentPresenceView studentPresenceView;
        if(currentUser.getUserType()==UserType.TEACHER)
            studentPresenceView=new StudentPresenceView(student, group, groupRepository);
        else
            studentPresenceView=new StudentPresenceView(student,group);
        dialogPresence.setWidth("1100px");
        dialogPresence.add(studentPresenceView);
        dialogPresence.open();

    }

    private void setAddGradeButton(Student student) {
        addGradeButton=new Button("Dodaj ocenę");
        addGradeButton.addClickListener(event -> {
            if(dialogGradesView!=null && dialogGradesView.isOpened())
                dialogGradesView.close();
            dialogAddGrade= new Dialog();
            addGradeView=new AddGradeView(student,(Teacher)currentUser, group, studentRepository, groupRepository, dialogAddGrade);
            dialogAddGrade.add(addGradeView);
            dialogAddGrade.open();
        });
    }

    private void setAddStudentsButton(){
        setEditStudentsDialog(true);
        addStudentsButton=new Button("Dodaj uczniów");
        addStudentsButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addStudentsButton.addClickListener(e->{
            EditStudentsView editStudentsView =new EditStudentsView(group, dialogEditStudents, groupRepository, studentRepository, false, true);
            dialogEditStudents.removeAll();
            dialogEditStudents.add(editStudentsView.getLayout());
            dialogEditStudents.open();
        });
        buttonLayout.add(addStudentsButton);
    }

    private void setAddLessonButton() {
        addLessonButton=new Button("Dodaj lekcję");
        addLessonButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addLessonDialog=new Dialog();
        addLessonDialog.addOpenedChangeListener(dialogOpenedChangeEvent -> {lessonTopicComboBox.getDataProvider().refreshAll();});
        addLessonButton.addClickListener(event -> {
            addLessonDialog.removeAll();
            AddLessonView addLessonView=new AddLessonView(group, groupRepository,lessonRepository, addLessonDialog);
            addLessonDialog.add(addLessonView);
            addLessonDialog.open();
        });
        buttonLayout.add(addLessonButton);
        buttonLayout.setVerticalComponentAlignment(Alignment.END, addLessonButton);
    }
    private void setEditStudentsDialog(Boolean isItAdd) {
        dialogEditStudents=new Dialog();
        dialogEditStudents.setWidth("1100px");
        dialogEditStudents.addOpenedChangeListener(dialogOpenedChangeEvent -> {
            if(isItAdd)
                studentGrid.getDataProvider().refreshAll();
            else if(!isItAdd)
            {
                this.remove(studentGrid, buttonLayout);
                setStudentGrid();
                this.add(buttonLayout);
            }
        });
    }

    private void setRemoveStudentsButton(){
        setEditStudentsDialog(false);
        removeStudentsButton=new Button("Usuń uczniów");
        removeStudentsButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        removeStudentsButton.addClickListener(event -> {
            EditStudentsView editStudentsView= new EditStudentsView(group,dialogEditStudents, groupRepository, studentRepository, false, false);
            dialogEditStudents.removeAll();
            dialogEditStudents.add(editStudentsView.getLayout());
            dialogEditStudents.open();
        });
        buttonLayout.add(removeStudentsButton);
    }
}
