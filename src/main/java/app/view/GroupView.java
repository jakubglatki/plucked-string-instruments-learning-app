package app.view;

import app.model.Group.Group;
import app.model.Group.GroupRepository;
import app.model.User.Student.Student;
import app.model.User.User;
import app.model.User.UserRepository;
import app.model.User.UserType;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;

@Route(value="group", layout = InternalLayout.class)
@CssImport("./styles/shared-styles.css")
public class GroupView extends HorizontalLayout {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

    private User user;
    private ArrayList<Group> usersGroups;
    private Label infoLabel;
    private VerticalLayout groupLayout;
    private HorizontalLayout nameLayout;
    private HorizontalLayout teacherLayout;

    public GroupView(){
        infoLabel = new Label();
        add(infoLabel);
    }

    @Override
    public void onAttach(AttachEvent event){
        try {
            defineGroupsList();
            for (Group group : usersGroups) {
                addGroupView(group);
            }
        }
        catch (Exception e){}
    }



    private void defineGroupsList() {
        VaadinSession session=VaadinSession.getCurrent();
        try{
            user = userRepository.findByMail(session.getAttribute("user").toString());
            if(user.getUserType()==UserType.TEACHER) {
                usersGroups = groupRepository.findByTeacher(user);
                infoLabel.setText(usersGroups.get(0).getName());
            }
            else if(user.getUserType()==UserType.STUDENT) {
                usersGroups = groupRepository.findByStudentsContaining((Student) user);
               infoLabel.setText(usersGroups.get(0).getInstrument().toString());
            }
        }
        catch (Exception e){infoLabel.setText(e.toString());}
    }

    private void addGroupView(Group group) {
        addNameLayout(group);
        addTeachingLayout(group);
        groupLayout=new VerticalLayout();
        groupLayout.add(nameLayout,teacherLayout);
        groupLayout.addClassName("group-grid");
        add(groupLayout);
    }


    private void addNameLayout(Group group) {
        nameLayout=new HorizontalLayout();
        TextField name=new TextField("Name");
        name.setValue(group.getName());
        TextField instrument=new TextField("Instrument");
        instrument.setValue(group.getInstrument().getName());
        name.setReadOnly(true);
        instrument.setReadOnly(true);
        nameLayout.add(name,instrument);
    }

    private void addTeachingLayout(Group group) {
        teacherLayout=new HorizontalLayout();
        TextField teacher=new TextField("Teacher");
        teacher.setValue(group.getTeacher().getLastName());
        DatePicker datePicker=new DatePicker(LocalDate.now());//group.getClassesDates().get(0));
        datePicker.setLabel("Next class date");
        teacher.setReadOnly(true);
        datePicker.setReadOnly(true);
        teacherLayout.add(teacher,datePicker);
    }
}
