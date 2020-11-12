package app.view;

import app.model.User.User;
import app.model.User.UserRepository;
import app.model.User.UserType;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class InternalLayout extends MainLayout {


    private  MenuBar menuBar;
    private static MenuItem welcome;
    private static MenuItem group;
    private static MenuItem login;
    private static MenuItem registration;
    private static MenuItem logout;

    @Autowired
    private static UserRepository userRepository;

    public InternalLayout() {
        menuBar = new MenuBar();
        welcome=menuBar.addItem("Welcome");
        welcome.addClickListener(e->{
            UI.getCurrent().navigate(MainView.class);
        });

        group=menuBar.addItem("Group");
        group.addClickListener(e->{
            UI.getCurrent().navigate(GroupView.class);
        });

        login=menuBar.addItem("Login");
        login.addClickListener(e->{
            UI.getCurrent().navigate(LoginView.class);});

        registration=menuBar.addItem("Registration");
        registration.addClickListener(e->{
            UI.getCurrent().navigate(RegistrationView.class);});

        logout=menuBar.addItem("Logout");
        logout.setVisible(false);
        logout.addClickListener(e->{
            VaadinSession.getCurrent().close();
            UI.getCurrent().navigate(MainView.class);
        });

        add(menuBar);
    }

    public static void loggedIn(){
        UI.getCurrent().navigate(MainView.class);
        login.setVisible(false);
        registration.setVisible(false);
        logout.setVisible(true);
    }
}
