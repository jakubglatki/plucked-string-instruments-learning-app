package app.view.GroupsView;

import app.model.Group.Group;
import app.model.Group.GroupRepository;
import app.model.Instrument.InstrumentRepository;
import app.model.User.Student.StudentRepository;
import app.model.User.Teacher.Teacher;
import app.model.User.User;
import app.model.User.UserRepository;
import app.model.User.UserType;
import app.view.Layout.InternalLayout;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@Route(value="group", layout = InternalLayout.class)
@CssImport("./styles/shared-styles.css")
public class GroupView extends VerticalLayout {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private InstrumentRepository instrumentRepository;

    private User user;
    private ArrayList<Group> usersGroups;
    private Label infoLabel;
    private VerticalLayout allGroupsLayout;
    private HorizontalLayout twoGroupsLayout;
    private VerticalLayout groupLayout;
    private HorizontalLayout nameLayout;
    private HorizontalLayout teacherLayout;
    private Dialog addGroupDialog;
    private Dialog deleteGroupDialog;
    private Button addGroupButton;
    private Button deleteGroupButton;
    private HorizontalLayout buttonLayout;

    public GroupView(){
        infoLabel = new Label();
        add(infoLabel);
    }

    @Override
    public void onAttach(AttachEvent event){
        try {
            defineGroupsList();
            allGroupsLayout=new VerticalLayout();
            allGroupsLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            int i=0;
            for (Group group : usersGroups) {
                addGroupView(group, i);
                i++;
                if(i>1)
                    i=0;
            }
            if(usersGroups.size()%2==1)
                allGroupsLayout.add(twoGroupsLayout);
            this.add(allGroupsLayout);
            setTeachersButtons();
        }
        catch (Exception e){}
    }

    private  void setTeachersButtons(){
        if(user.getUserType()==UserType.TEACHER) {
            buttonLayout=new HorizontalLayout();
            addGroupButton=new Button("Dodaj grupę");
            addGroupDialog=new Dialog();
            setButton(addGroupButton, addGroupDialog);
            deleteGroupButton=new Button("Usuń grupy");
            deleteGroupDialog=new Dialog();
            setButton(deleteGroupButton,deleteGroupDialog);
            add(buttonLayout);
        }
    }

    private void defineGroupsList() {
        VaadinSession session=VaadinSession.getCurrent();
        try{
            user = userRepository.findByMail(session.getAttribute("user").toString());
            if(user.getUserType()==UserType.TEACHER) {
                usersGroups = groupRepository.findByTeacher(user);
            }
            else if(user.getUserType()==UserType.STUDENT) {
                usersGroups = groupRepository.findByStudentsMailContaining(user.getMail());
            }
        }
        catch (Exception e){
            infoLabel.setText("Zaloguj się, aby uzyskać dostęp do swoich grup");}
    }

    private void setButton(Button button, Dialog dialog) {
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addClickListener(event -> {
                    dialog.setWidth("1000px");
                    dialog.addDetachListener(dialogOpenedChangeEvent -> {
                        UI.getCurrent().getPage().reload();
                    });
                    if (button.getText().contains("Dodaj"))
                        setAddGroupDialog();
                    else
                        setDeleteGroupDialog();
                });
            buttonLayout.add(button);
    }



    private void setAddGroupDialog() {
        AddGroupView addAddGroupView = new AddGroupView((Teacher) user, addGroupDialog, studentRepository, groupRepository, instrumentRepository);
        addGroupDialog.add(addAddGroupView);
        addGroupDialog.open();
    }

    private void setDeleteGroupDialog() {
        DeleteGroupView deleteGroupView=new DeleteGroupView(deleteGroupDialog, (Teacher) user,groupRepository);
        deleteGroupDialog.add(deleteGroupView);
        deleteGroupDialog.open();
    }

    //Group layouts are grouped as two, and "i" says if it's first or second
    private void addGroupView(Group group, int i) {
        addNameLayout(group);
        addTeachingLayout(group);
        groupLayout=new VerticalLayout();
        groupLayout.add(nameLayout,teacherLayout);
        groupLayout.addClassName("group-grid");
        groupLayout.addClickListener(e->{
            VaadinSession.getCurrent().setAttribute("group", group);
            UI.getCurrent().navigate(SpecificGroupView.class);});

        if(i==0)
            makeNewTwoGroupsLayout(groupLayout);
        else
            addToTwoGroupsLayout(groupLayout);
    }

    private void makeNewTwoGroupsLayout(VerticalLayout groupLayout) {
        twoGroupsLayout=new HorizontalLayout();
        twoGroupsLayout.add(groupLayout);
    }

    private void addToTwoGroupsLayout(VerticalLayout groupLayout) {
        twoGroupsLayout.add(groupLayout);
        allGroupsLayout.add(twoGroupsLayout);
    }


    private void addNameLayout(Group group) {
        nameLayout=new HorizontalLayout();
        TextField name=new TextField("Nazwa");
        name.setValue(group.getName());
        TextField instrument=new TextField("Instrument");
        instrument.setValue(group.getInstrument().getName());
        name.setReadOnly(true);
        instrument.setReadOnly(true);
        nameLayout.add(name,instrument);
    }

    private void addTeachingLayout(Group group) {
        teacherLayout=new HorizontalLayout();
        TextField teacher=new TextField("Nauczyciel");
        teacher.setValue(group.getTeacher().getFirstName()+" "+group.getTeacher().getLastName());
        DateTimePicker datePicker= new DateTimePicker();
        if(group.getLessons()!=null)
           datePicker.setValue(group.getLessons().get(0).getClassDate());
        datePicker.setLabel("Najbliższa lekcja");
        teacher.setReadOnly(true);
        datePicker.setReadOnly(true);
        teacherLayout.add(teacher,datePicker);
    }
}
