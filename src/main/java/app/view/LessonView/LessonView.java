package app.view.LessonView;

import app.LABEL_NAMES;
import app.controller.LessonController;
import app.model.Group.GroupRepository;
import app.model.Lesson.Lesson;
import app.model.Lesson.LessonPresence;
import app.model.User.Student.Student;
import app.model.User.User;
import app.model.Group.Group;
import app.model.User.UserRepository;
import app.model.User.UserType;
import app.view.Layout.InternalLayout;
import app.view.MainView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;

@Route(value = "lesson", layout = InternalLayout.class)
@CssImport("./styles/shared-styles.css")
public class LessonView extends VerticalLayout {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;

    private Lesson lesson;
    private User user;
    private Group group;
    private HorizontalLayout tabsLayout;
    private TextField topicField;
    private HorizontalLayout fieldLayout;
    private Button changeLessonStatusButton;

    public LessonView(){
    }


    @Override
    public void onAttach(AttachEvent event){
        topicField=new TextField(LABEL_NAMES.TOPIC);
        try {
            user = userRepository.findByMail(VaadinSession.getCurrent().getAttribute("user").toString());
            lesson= (Lesson)UI.getCurrent().getSession().getAttribute("lesson");
            group=(Group)UI.getCurrent().getSession().getAttribute("group");
            setTopicField();
            if(user.getUserType()==UserType.STUDENT && lesson.getIsActive()==true)
                setStudentAsPresent();
            add(fieldLayout);
        }
        catch (Exception e){
            UI.getCurrent().navigate(MainView.class);
        }
        setTabsLayout();
    }

    private void setStudentAsPresent() {
        LessonPresence lessonPresence=lesson.getStudentsLessonPresences((Student)user);
        lessonPresence.setPresent(true);
        LessonController lessonController=new LessonController();
        lesson.getLessonPresences().set(lessonController.getLessonPresenceIndex(lesson,lessonPresence),lessonPresence);
        group.getLessons().set(lessonController.getLessonIndex(group,lesson), lesson);
        groupRepository.save(group);
    }

    private void setTopicField(){
        topicField.setValue(lesson.getTopic());
        topicField.setReadOnly(true);
        topicField.setWidth("300px");
        fieldLayout=new HorizontalLayout(topicField);
        if(user.getUserType()== UserType.TEACHER)
            setStartEndLessonButtons();
    }

    private void setStartEndLessonButtons() {
        changeLessonStatusButton=new Button();
        if(lesson.getIsActive()==true)
            changeLessonStatusButton.setText(LABEL_NAMES.END_LESSON);
        else
            changeLessonStatusButton.setText(LABEL_NAMES.START_LESSON);
        
        changeLessonStatusButton.addClickListener(e->{
           if(changeLessonStatusButton.getText().equals(LABEL_NAMES.START_LESSON))
               setChangeLessonStatus(true, LABEL_NAMES.END_LESSON);
           else
               setChangeLessonStatus(false, LABEL_NAMES.START_LESSON);
        });
        fieldLayout.add(changeLessonStatusButton);
    }

    private void setChangeLessonStatus(boolean active, String buttonText) {
        lesson.setActive(active);
        groupRepository.save(group);
        changeLessonStatusButton.setText(buttonText);
    }

    private void setTabsLayout() {
        tabsLayout=new HorizontalLayout();
        tabsLayout.addClassName("group-grid");
        tabsLayout.setWidth("98%");
        tabsLayout.setHeight("450px");
        add(tabsLayout);
    }
}
