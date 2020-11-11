package app.view;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

@Route(value="", layout = InternalLayout.class)
public class MainView extends VerticalLayout  {

    public MainView() {
        add(new H4("Welcome "  + "!"));
    }


}
