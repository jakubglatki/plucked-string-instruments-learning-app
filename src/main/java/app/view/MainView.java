package app.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route("")
public class MainView extends VerticalLayout {

    public  MainView() {
        Button click_me = new Button("xd me");
        add(click_me);
    }
}
