package app.view.LessonView;

import app.controller.GroupController;
import app.controller.LessonController;
import app.model.Group.Group;
import app.model.Group.GroupRepository;
import app.model.Instrument.Instrument;
import app.model.Lesson.Lesson;
import app.model.Lesson.LessonRepository;
import app.model.User.Student.Student;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.DateTimeRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AddLessonView extends VerticalLayout {
    private GroupRepository groupRepository;
    private LessonRepository lessonRepository;
    private TextArea topicField;
    private DateTimePicker lessonDatePicker;
    private Button addButton;
    private Button cancelButton;
    private Dialog dialog;
    private Instrument instrument;
    private Group group;
    private Binder<Lesson> binder;

    private HorizontalLayout fieldsLayout;
    private HorizontalLayout buttonsLayout;

    public AddLessonView(Group group, GroupRepository groupRepository, LessonRepository lessonRepository, Dialog dialog){
        this.group=group;
        this.dialog=dialog;
        this.groupRepository=groupRepository;
        this.lessonRepository=lessonRepository;
        this.instrument=group.getInstrument();
        setFields();
        setButtons();
    }

    private void setFields() {
        topicField=new TextArea("Temat");
        topicField.setValue("Temat");

        lessonDatePicker=new DateTimePicker();
        lessonDatePicker.setLabel("Data");

        setFieldsBinder();
        fieldsLayout=new HorizontalLayout(topicField, lessonDatePicker);
        add(fieldsLayout);
    }


    private void setFieldsBinder() {
        binder=new Binder<>();
        binder.forField(topicField)
                .withValidator(new StringLengthValidator(
                        "Wybierz temat lekcji", 1, null))
                .bind(Lesson::getTopic, Lesson::setTopic);

        binder.forField(lessonDatePicker)
                .withValidator(new DateTimeRangeValidator(
                        "Wybierz datę lekcji, nie wcześniejszą niż aktualna data", LocalDateTime.now(), null))
                .bind(Lesson::getClassDate, Lesson::setClassDate);
    }

    private void setButtons() {
        addButton=new Button("Dodaj");
        addButton.addClickShortcut(Key.ENTER);
        addButton.addClickListener(event -> {
            addNewLesson();
        });
        cancelButton=new Button("Anuluj");
        cancelButton.addClickListener(event -> {
            dialog.close();
        });
        buttonsLayout=new HorizontalLayout(addButton,cancelButton);
        add(buttonsLayout);

    }

    private void addNewLesson() { {
        if(!topicField.getValue().isEmpty()&&lessonDatePicker.getValue()!=null) {
            Lesson lesson=new Lesson(group, topicField.getValue(), lessonDatePicker.getValue());
            LessonController lessonController=new LessonController();
            group=lessonController.addLessonToGroup(group, lesson);
            groupRepository.save(group);
            dialog.close();
        }
    }
    }

}
