package app.view.LessonView;

import app.model.Group.GroupRepository;
import app.model.Instrument.Instrument;
import app.model.Group.Group;
import app.model.User.User;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;
import java.util.stream.Stream;

@CssImport("./styles/shared-styles.css")
public class TabsView extends VerticalLayout {

    private GroupRepository groupRepository;
    private Instrument instrument;
    private VerticalLayout tabsLayout;
    private MenuBar buttonsBar;
    private User user;

    public TabsView(GroupRepository groupRepository, Group group, User user){
        this.instrument=group.getInstrument();
        this.groupRepository=groupRepository;
        this.user=user;
        setButtonsBar();
        setTabsLayout();
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
        int nbOfStrings = instrument.getStrings().size();
        String layoutWidth=tabsLayout.getWidth();
        for (int k = 0; k < 2; k++) {
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
                    textField.setValue("-");
                    textField.addClassName("chord-text-field");
                    stringLayout.add(textField);
                }
                lineTabsLayout.add(stringLayout);
            }
            tabsLayout.add(lineTabsLayout);
        }
    }
}
