package app.view.LessonView;

import app.LABEL_NAMES;
import app.model.Group.Group;
import app.model.Group.GroupRepository;
import app.model.Lesson.Lesson;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DeleteLessonView extends VerticalLayout {

    private GroupRepository groupRepository;

    private Grid<Lesson> lessonGrid;
    private Button removeButton;
    private Button cancelButton;
    private HorizontalLayout buttonLayout;
    private Dialog dialog;
    private Group group;


    public DeleteLessonView(Dialog dialog,  Group group, GroupRepository groupRepository) {
        this.dialog = dialog;
        this.group=group;
        this.groupRepository = groupRepository;
        this.buttonLayout = new HorizontalLayout();
        setGrid();
        setRemoveButton();
        setCloseButton();
        add(buttonLayout);
    }


    private void setRemoveButton() {
        removeButton = new Button("Usuń");
        removeButton.addClickShortcut(Key.ENTER);
        removeButton.addClickListener(event -> {
            removeGroup();
            dialog.close();
        });
        buttonLayout.add(removeButton);
    }

    private void removeGroup() {
        ArrayList<Lesson> lessons = new ArrayList<>();
        ArrayList<Lesson> groupLessons=new ArrayList<>();
        groupLessons.addAll(group.getLessons());
        lessons.addAll(lessonGrid.getSelectedItems());
        for (Lesson lesson : lessons
        ) {
            groupLessons.remove(lesson);
        }
        group.setLessons(groupLessons);
        groupRepository.save(group);
    }


    private void setCloseButton() {
        cancelButton = new Button("Anuluj");
        cancelButton.addClickListener(event -> {
            dialog.close();
        });
        buttonLayout.add(cancelButton);
    }


    private void setGrid() {
        lessonGrid = new Grid<>();
        lessonGrid.setItems(group.getLessons());
        lessonGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        lessonGrid.addColumn(Lesson::getTopic).setHeader(LABEL_NAMES.TOPIC);
        setClassDateColumn();
        lessonGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);
        add(lessonGrid);
    }


    private void setClassDateColumn() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        lessonGrid.addColumn(Lesson -> formatter.format(Lesson.getClassDate())).setHeader(LABEL_NAMES.DATE);
    }
}