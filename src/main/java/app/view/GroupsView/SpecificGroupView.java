package app.view.GroupsView;

import app.controller.GroupController;
import app.model.Group.Group;
import app.model.Group.GroupRepository;
import app.model.Lesson.LessonRepository;
import app.model.User.Student.Student;
import app.model.User.Student.StudentRepository;
import app.model.User.Teacher.Teacher;
import app.model.User.User;
import app.model.User.UserRepository;
import app.model.User.UserType;
import app.view.Layout.InternalLayout;
import app.view.LessonView.AddLessonView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
    private TextField nameField;
    private TextField instrumentField;
    private TextField teacherField;
    private Grid<Student> studentGrid;
    private Button gradesButton;
    private Button studentsGradesButton;
    private Button addGradeButton;
    private Button addLessonButton;
    private Dialog addLessonDialog;
    private Button addStudentsButton;
    private Button removeStudentsButton;
    private AddGradeView addGradeView;
    private Dialog dialogGradesView;
    private Dialog dialogAddGrade;
    private Dialog dialogEditStudents;

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
            setStudentGrid();
            if(currentUser.getUserType()==UserType.STUDENT)
                setGradesButton();
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

    private void setStudentGrid(){
        studentGrid=new Grid<>();
        studentGrid.setItems(group.getStudents());
        studentGrid.addColumn(Student::getFirstName).setHeader("Imię").setResizable(true);
        studentGrid.addColumn(Student::getLastName).setHeader("Nazwisko").setResizable(true);
        if(currentUser.getUserType()==UserType.TEACHER) {
            setSeeStudentsGradesButton();
            setAddStudentsGradesButton();
        }
        studentGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);
        studentGrid.setHeightByRows(true);
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
        gradesButton.addClickListener(e->{
            showGradesClickListener((Student)currentUser);
        });
        add(gradesButton);
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
