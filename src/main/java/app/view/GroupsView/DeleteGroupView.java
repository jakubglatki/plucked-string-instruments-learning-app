package app.view.GroupsView;

import app.controller.GroupController;
import app.controller.StudentController;
import app.model.Group.Group;
import app.model.Group.GroupRepository;
import app.model.User.Student.Student;
import app.model.User.Student.StudentRepository;
import app.model.User.Teacher.Teacher;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;

public class DeleteGroupView extends VerticalLayout {

    private GroupRepository groupRepository;

    private GroupController groupController;
    private Grid<Group> groupGrid;
    private Button removeButton;
    private Button cancelButton;
    private HorizontalLayout buttonLayout;
    private Dialog dialog;
    private Teacher teacher;


    public DeleteGroupView(Dialog dialog,Teacher teacher, GroupRepository groupRepository) {
        this.dialog = dialog;
        this.teacher=teacher;
        this.groupRepository = groupRepository;
        this.groupController = new GroupController();
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
        ArrayList<Group> groups = new ArrayList<>();
        groups.addAll(groupGrid.getSelectedItems());
        for (Group group : groups
        ) {
            groupRepository.delete(group);
        }
    }


    private void setCloseButton() {
        cancelButton = new Button("Anuluj");
        cancelButton.addClickListener(event -> {
            dialog.close();
        });
        buttonLayout.add(cancelButton);
    }


    private void setGrid() {
        groupGrid = new Grid<>();
        ArrayList<Group> groups;
        groups = (ArrayList<Group>) groupRepository.findByTeacher(teacher);
        groupGrid.setItems(groups);
        groupGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        groupGrid.addColumn(Group::getName).setHeader("Nazwa");
        groupGrid.addColumn(Group -> Group.getInstrument().getName()).setHeader("Instrument");
        groupGrid.addColumn(Group -> Group.getTeacher().getFirstName() + " " + Group.getTeacher().getLastName()).setHeader("Nauczyciel");

        groupGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);
        add(groupGrid);
    }
}