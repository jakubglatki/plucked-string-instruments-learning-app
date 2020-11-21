package app.view;

import app.view.Layout.InternalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value="", layout = InternalLayout.class)
public class MainView extends VerticalLayout  {

    public MainView() {
    }


}
