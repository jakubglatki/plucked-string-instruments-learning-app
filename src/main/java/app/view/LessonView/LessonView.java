package app.view.LessonView;

import app.model.Lesson.Lesson;
import app.view.Layout.InternalLayout;
import app.view.MainView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "lesson", layout = InternalLayout.class)
@CssImport("./styles/shared-styles.css")
public class LessonView extends VerticalLayout {

    final static String TOPIC_LABEL="Temat";

    private HorizontalLayout tabsLayout;
    private TextField topicField;

    public LessonView(){
    }


    @Override
    public void onAttach(AttachEvent event){
        topicField=new TextField(TOPIC_LABEL);
        try {
            Lesson lesson= (Lesson)UI.getCurrent().getSession().getAttribute("lesson");
            topicField.setValue(lesson.getTopic());
            topicField.setReadOnly(true);
            topicField.setWidth("300px");
        }
        catch (Exception e){
            UI.getCurrent().navigate(MainView.class);
        }
        add(topicField);

        tabsLayout=new HorizontalLayout();
        tabsLayout.addClassName("group-grid");
        tabsLayout.setWidth("98%");
        tabsLayout.setHeight("450px");
        add(tabsLayout);
    }
}
