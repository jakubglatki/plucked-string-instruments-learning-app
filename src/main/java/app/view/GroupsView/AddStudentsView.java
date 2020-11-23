package app.view.GroupsView;

import app.controller.GroupController;
import app.model.Group.Group;
import app.model.Group.GroupRepository;
import app.model.User.Student.Student;
import app.model.User.Student.StudentRepository;
import app.model.User.UserType;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class AddStudentsView extends VerticalLayout {

    private StudentRepository studentRepository;
    private GroupRepository groupRepository;

    private GroupController groupController;
    private Grid<Student> studentsGrid;
    private Button addButton;
    private Button cancelButton;
    private HorizontalLayout buttonLayout;
    private VerticalLayout layout;
    private Group group;
    private Dialog dialog;
    private boolean isItNewGroup;

    public VerticalLayout getLayout() {
        return layout;
    }

    public AddStudentsView(Group group, Dialog dialog, GroupRepository groupRepository, StudentRepository studentRepository){
        this.group=group;
        this.dialog=dialog;
        this.groupRepository=groupRepository;
        this.studentRepository=studentRepository;
        this.groupController=new GroupController();
        this.buttonLayout=new HorizontalLayout();
        setGrid();
        setAddButton();
        setCloseButton();
        add(buttonLayout);
        layout=new VerticalLayout();
        layout.add(studentsGrid,buttonLayout);
    }

    private void setAddButton() {
        addButton=new Button("Dodaj");
        addButton.addClickShortcut(Key.ENTER);
        addButton.addClickListener(event -> {
            addStudents();
            dialog.close();
        });
        buttonLayout.add(addButton);
    }

    private void addStudents() {
        ArrayList<Student> students=new ArrayList<>();
        students.addAll(studentsGrid.getSelectedItems());
        group=groupController.addStudentsToGroup(group,students);
        groupRepository.save(group);
    }

    private void setCloseButton() {
        cancelButton=new Button("Anuluj");
        cancelButton.addClickListener(event -> {
            dialog.close();
        });
        buttonLayout.add(cancelButton);
    }


    private void setGrid() {
        studentsGrid=new Grid<>();
        ArrayList<Student> students= setStudentsList();
        studentsGrid.setItems(students);
        studentsGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        studentsGrid.addColumn(Student::getFirstName).setHeader("Imię");
        studentsGrid.addColumn(Student::getLastName).setHeader("Nazwisko");
        studentsGrid.addColumn(Student::getMail).setHeader("E-mail");

        studentsGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);
        add(studentsGrid);
    }

    private ArrayList<Student> setStudentsList() {
        ArrayList<Student> students;
        ArrayList<Student> groupStudents;
        ArrayList<Student> studentsToReturn;
        groupStudents=group.getStudents();
        students= studentRepository.findByUserType(UserType.STUDENT);
        studentsToReturn= (ArrayList<Student>) students.clone();
        for (int i=0;i<groupStudents.size();i++) {
            if(groupStudents!=null) {
                for(Student student:students){
                    if(groupStudents.get(i).getMail().contains(student.getMail())){
                        studentsToReturn.remove(student);
                    }
                }
            }
        }
        return studentsToReturn;
    }
}
