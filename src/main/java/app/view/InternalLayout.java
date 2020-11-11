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

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public static MenuItem getWelcome() {
        return welcome;
    }

    public static void setWelcome(MenuItem welcome) {
        InternalLayout.welcome = welcome;
    }

    public static MenuItem getGroup() {
        return group;
    }

    public static void setGroup(MenuItem group) {
        InternalLayout.group = group;
    }

    public static MenuItem getLogin() {
        return login;
    }

    public static void setLogin(MenuItem login) {
        InternalLayout.login = login;
    }

    public static MenuItem getRegistration() {
        return registration;
    }

    public static void setRegistration(MenuItem registration) {
        InternalLayout.registration = registration;
    }

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

        add(menuBar);
    }

    public static void loggedIn(){
        UI.getCurrent().navigate(MainView.class);
        login.setVisible(false);
        registration.setVisible(false);
    }
}
