package app.view;

import app.model.User.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;

import java.util.List;

@Route("elo")
public class EloView extends VerticalLayout {
    public EloView(){


        Button elo = new Button("elo");
        elo.addClickListener(e->{
            UI.getCurrent().navigate("");
        });
        add(elo);
    }
}
