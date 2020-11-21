package app.view.Layout;

import app.model.User.UserRepository;
import app.view.GroupsView.GroupView;
import app.view.Layout.MainLayout;
import app.view.LoginView;
import app.view.MainView;
import app.view.RegistrationView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

public class InternalLayout extends MainLayout {


    private  MenuBar menuBar;
    private static MenuItem welcome;
    private static MenuItem group;
    private static MenuItem login;
    private static MenuItem registration;
    private static MenuItem logout;


    public InternalLayout() {
        menuBar = new MenuBar();
        welcome=menuBar.addItem("Start");
        welcome.addClickListener(e->{
            UI.getCurrent().navigate(MainView.class);
        });

        group=menuBar.addItem("Grupy");
        group.addClickListener(e->{
            UI.getCurrent().navigate(GroupView.class);
        });

        login=menuBar.addItem("Logowanie");
        login.addClickListener(e->{
            UI.getCurrent().navigate(LoginView.class);});

        registration=menuBar.addItem("Rejestracja");
        registration.addClickListener(e->{
            UI.getCurrent().navigate(RegistrationView.class);});

        logout=menuBar.addItem("Wyloguj się");
        logout.setVisible(false);
        logout.addClickListener(e->{
            UI.getCurrent().navigate(MainView.class);
            VaadinSession.getCurrent().close();
        });

        menuBar.setHeight("100px");
        add(menuBar);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    public static void loggedIn(){
        UI.getCurrent().navigate(MainView.class);
        login.setVisible(false);
        registration.setVisible(false);
        logout.setVisible(true);
    }
}
