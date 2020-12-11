package app.view.LessonView;

import app.model.Group.GroupRepository;
import app.model.Instrument.Instrument;
import app.model.Group.Group;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TabsView extends VerticalLayout {

    private GroupRepository groupRepository;
    private Instrument instrument;
    private HorizontalLayout tabsLayout;

    public TabsView(GroupRepository groupRepository, Group group){
        this.instrument=group.getInstrument();
        this.groupRepository=groupRepository;
        setTabsLayout();
    }

    private void setTabsLayout() {
        tabsLayout=new HorizontalLayout();
        tabsLayout.addClassName("group-grid");
        tabsLayout.setWidth("98%");
        tabsLayout.setHeight("450px");
        add(tabsLayout);
    }
}
