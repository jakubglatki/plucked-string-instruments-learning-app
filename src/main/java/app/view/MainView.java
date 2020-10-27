package app.view;

import app.model.User.User;
import app.model.User.UserRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    private UserRepository userRepository;
    public  MainView(UserRepository userRepository) {
        this.userRepository=userRepository;
        Button click_me = new Button();
        List<User> users=userRepository.findByFirstName("Iks");
        click_me.setText(users.get(0).getLastName());
        click_me.addClickListener(e->{
            UI.getCurrent().navigate("elo");
        });
        add(click_me);
    }
}
