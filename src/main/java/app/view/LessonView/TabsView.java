package app.view.LessonView;

import app.controller.LessonController;
import app.model.Group.Group;
import app.model.Group.GroupRepository;
import app.model.Instrument.Chord;
import app.model.Instrument.Instrument;
import app.model.Instrument.InstrumentRepository;
import app.model.Lesson.Lesson;
import app.model.User.User;
import app.model.User.UserType;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@CssImport("./styles/shared-styles.css")
public class TabsView extends VerticalLayout {

    private GroupRepository groupRepository;
    protected Group group;
    protected Lesson lesson;
    private int lastTabsLine;
    private Instrument instrument;
    private VerticalLayout tabsLayout;
    private MenuBar buttonsBar;
    private Button addLineButton;
    private Button removeLineButton;
    private HorizontalLayout buttonsLayout;
    private User user;
    protected ArrayList<VerticalLayout> stringLayouts;

    public TabsView(GroupRepository groupRepository,  Group group, Lesson lesson, User user){
        this.instrument=group.getInstrument();
        this.groupRepository=groupRepository;
        this.user=user;
        this.group=group;
        this.lesson=lesson;
        stringLayouts=new ArrayList<>();
        if (user.getUserType() == UserType.TEACHER) {
            buttonsBar=new MenuBar();
            buttonsBar.setOpenOnHover(true);
            Stream.of("C","D","E","F","G","A","B")
                    .forEach(this::setButtonsBar);
            add(buttonsBar);
        }
        setTabsLayout();
        if (user.getUserType() == UserType.TEACHER) {
            setAddLineButton();
            setRemoveLineButton();
        }
    }


    private void setButtonsBar(String chordName) {
        Chord chord=group.getInstrument().getChords().stream().filter(ch -> ch.getName().equals(chordName)).findFirst().get();
        MenuItem menuItem= buttonsBar.addItem(chord.getName());
        SubMenu subMenuItem=menuItem.getSubMenu();
        Chord chordM=group.getInstrument().getChords().stream().filter(ch -> ch.getName().equals(chordName+"m")).findFirst().get();
        MenuItem itemM= subMenuItem.addItem(chordM.getName());
        Chord chord7=group.getInstrument().getChords().stream().filter(ch -> ch.getName().equals(chordName+"7")).findFirst().get();
        MenuItem item7= subMenuItem.addItem(chord7.getName());
        addMenuItemsClickListeners(menuItem, chord);
        addMenuItemsClickListeners(itemM, chordM);
        addMenuItemsClickListeners(item7, chord7);
    }

    private void addMenuItemsClickListeners(MenuItem menuItem, Chord chord) {
        menuItem.addClickListener(menuItemClickEvent -> {
            for(VerticalLayout stringLayout:stringLayouts){
                if(stringLayout.getClassName().equals("chosen-string-layout")){
                    for(int i=0;i<instrument.getStrings().size();i++) {
                        TextField textField= (TextField)stringLayout.getComponentAt(i+1);
                        textField.setValue(chord.getStrings().get(i).getValue().get(0));
                    }
                    uncheckStringLayout(stringLayout);
                }
            }
        });
    }

    private void uncheckStringLayout(VerticalLayout stringLayout) {
        stringLayout.setClassName("not-chosen-string-layout");
        int index= stringLayouts.indexOf(stringLayout);
        lesson.getInstrument().getStringLayoutClass().set(index,"not-chosen-string-layout");
        groupRepository.save(group);
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
            VerticalLayout stringLayout= new VerticalLayout();
            setStringLayout(stringLayout, i, line);
            setIcons(i, line, stringLayout);
            for (int j = 0; j < nbOfStrings; j++) {
                setTextFields(i,j, line, stringLayout);
            }
            lineTabsLayout.add(stringLayout);
        }
        tabsLayout.add(lineTabsLayout);
    }

    private void setStringLayout(VerticalLayout stringLayout, int i, int line) {
        stringLayout.setPadding(false);
        stringLayout.setSpacing(false);
        stringLayout.setId(String.valueOf(i+(line*30)));
        stringLayout.setClassName(lesson.getInstrument().getStringLayoutClass().get(i+(line*30)));
        stringLayouts.add(stringLayout);
    }

    private void setIcons(int i, int line, VerticalLayout stringLayout) {
        if(user.getUserType()==UserType.TEACHER) {
            Icon icon = new Icon(VaadinIcon.CARET_DOWN);
            icon.setClassName("icon");
            stringLayout.add(icon);
            stringLayout.setHorizontalComponentAlignment(Alignment.CENTER, icon);
            icon.addClickListener(e -> setIconClickListener(i, line, stringLayout));
        }
    }

    private void setIconClickListener( int i, int line, VerticalLayout stringLayout) {
        if (!stringLayout.getClassName().equals("chosen-string-layout"))
            setStringLayoutClassName("chosen-string-layout", stringLayout, i, line);
        else
            setStringLayoutClassName("not-chosen-string-layout", stringLayout, i, line);
    }

    private void setStringLayoutClassName(String s, VerticalLayout stringLayout, int i, int line) {
        stringLayout.setClassName(s);
        lesson.getInstrument().getStringLayoutClass().set((i+(line*30)), s);
        groupRepository.save(group);
    }

    private void setTextFields(int i, int j, int line, VerticalLayout stringLayout) {
        TextField textField = new TextField();
        textField.setReadOnly(true);
        textField.setValue(lesson.getInstrument().getStrings().get(j).getValue().get(i+(line*30)));
        if(user.getUserType()==UserType.TEACHER)
            setTextFieldForTeacher(textField, j, i+(line*30));
        textField.addClassName("chord-text-field");
        stringLayout.add(textField);
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
                lesson.getInstrument().getStringLayoutClass().add("not-chosen-string-layout");
                for(int j=0;j<instrument.getStrings().size();j++) {
                    lesson.getInstrument().getStrings().get(j).getValue().add(i, "-");
                }
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
                    for(int i=30*(lastTabsLine)-1;i>30*(lastTabsLine)-31;i--) {
                        lesson.getInstrument().getStringLayoutClass().remove(i);
                        for (int j = 0; j < instrument.getStrings().size(); j++) {
                            lesson.getInstrument().getStrings().get(j).getValue().remove(i);
                            stringLayouts.remove(i);
                        }
                    }
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
