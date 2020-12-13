package app.view.LessonView;

import app.controller.LessonController;
import app.model.Group.Group;
import app.model.Group.GroupRepository;
import app.model.Instrument.Instrument;
import app.model.Lesson.Lesson;
import app.model.User.User;
import app.model.User.UserType;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;
import java.util.stream.Stream;

@CssImport("./styles/shared-styles.css")
public class TabsView extends VerticalLayout {

    private GroupRepository groupRepository;
    private Group group;
    private Lesson lesson;
    private int lastTabsLine;
    private Instrument instrument;
    private VerticalLayout tabsLayout;
    private MenuBar buttonsBar;
    private Button addLineButton;
    private Button removeLineButton;
    private HorizontalLayout buttonsLayout;
    private User user;

    public TabsView(GroupRepository groupRepository, Group group, Lesson lesson, User user){
        this.instrument=group.getInstrument();
        this.groupRepository=groupRepository;
        this.user=user;
        this.group=group;
        this.lesson=lesson;
        if (user.getUserType() == UserType.TEACHER)
            setButtonsBar();
        setTabsLayout();
        if (user.getUserType() == UserType.TEACHER) {
            setAddLineButton();
            setRemoveLineButton();
        }
    }


    private void setButtonsBar() {
        buttonsBar=new MenuBar();
        Stream.of("C","D","E","F","G","A","B")
                .forEach(buttonsBar::addItem);
        buttonsBar.setOpenOnHover(true);
        List<MenuItem> menuItems=buttonsBar.getItems();
        for(MenuItem menuItem: menuItems) {
            menuItem.getSubMenu().addItem(menuItem.getText() + "m");
            menuItem.getSubMenu().addItem(menuItem.getText() + "7");
        }
        add(buttonsBar);
    }

    private void setTabsLayout() {
        tabsLayout=new VerticalLayout();
        tabsLayout.addClassName("chord-grid");
        add(tabsLayout);
        setInstrumentsStrings();
    }

    private void setInstrumentsStrings() {
        lastTabsLine=lesson.getNbOfLines();
        for (int k = 0; k < lastTabsLine; k++) {
            addNewStringsLine(k);
        }
    }

    private void addNewStringsLine(int line){
        int nbOfStrings = instrument.getStrings().size();
        HorizontalLayout lineTabsLayout= new HorizontalLayout();
        lineTabsLayout.setPadding(false);
        lineTabsLayout.setSpacing(false);
        for (int i = 0; i < 30; i++) {
            VerticalLayout stringLayout = new VerticalLayout();
            stringLayout.setPadding(false);
            stringLayout.setSpacing(false);
            for (int j = 0; j < nbOfStrings; j++) {
                TextField textField = new TextField();
                textField.setReadOnly(true);
                textField.setValue(lesson.getInstrument().getStrings().get(j).getValue().get(i+(line*30)));
                if(user.getUserType()==UserType.TEACHER)
                    setTextFieldForTeacher(textField, j, i+(line*30));
                textField.addClassName("chord-text-field");
                stringLayout.add(textField);
            }
            lineTabsLayout.add(stringLayout);
        }
        tabsLayout.add(lineTabsLayout);
    }

    private void setTextFieldForTeacher(TextField textField, int stringNb, int placeNb) {
        textField.setReadOnly(false);
        textField.addValueChangeListener(e->{
           lesson.getInstrument().getStrings().get(stringNb).getValue().set(placeNb,e.getValue());
           groupRepository.save(group);
        });
    }


    private void setAddLineButton() {
        addLineButton=new Button(new Icon(VaadinIcon.PLUS));
        addLineButton.addClickListener(e->{
            for(int i=30*lastTabsLine;i<30*lastTabsLine+30;i++){
                for(int j=0;j<instrument.getStrings().size();j++)
                    lesson.getInstrument().getStrings().get(j).getValue().add(i,"-");
            }
            groupRepository.save(group);
            changeNbOfLinesInDB();
            addNewStringsLine(lastTabsLine);
            lastTabsLine++;
        });
        buttonsLayout=new HorizontalLayout(addLineButton);
        this.add(buttonsLayout);
    }

    private void setRemoveLineButton() {
        removeLineButton=new Button(new Icon(VaadinIcon.MINUS));
        removeLineButton.addClickListener(e->{
            if(this.lastTabsLine>1){
                tabsLayout.remove(tabsLayout.getComponentAt(lastTabsLine-1));
                lastTabsLine--;
                changeNbOfLinesInDB();
            }
        });
        buttonsLayout.add(removeLineButton);
    }

    private void changeNbOfLinesInDB(){
        int index= LessonController.getLessonIndex(group,lesson);
        lesson.setNbOfLines(lastTabsLine);
        group.getLessons().remove(index);
        group.getLessons().add(index,lesson);
        groupRepository.save(group);
    }
}
