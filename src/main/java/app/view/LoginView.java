package app.view;

import app.model.User.User;
import app.model.User.UserRepository;
import app.view.Layout.InternalLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value="login", layout = InternalLayout.class)
public class LoginView extends VerticalLayout {

    private VerticalLayout layout;
    private TextField email;
    private PasswordField password;
    private Button login;
    private Label infoLabel;
    private Binder<User> binder;

    @Autowired
    private UserRepository userRepository;

    public LoginView(){
        setupLayout();
        addFields();
        addBinders();
        addButton();
        addLabel();

        add(layout);
    }

    private void setupLayout() {
        layout=new VerticalLayout();
        layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }


    private void addFields() {
        email=new TextField();
        email.setValueChangeMode(ValueChangeMode.EAGER);
        email.setLabel("E-mail");

        password=new PasswordField();
        password.setValueChangeMode(ValueChangeMode.EAGER);
        password.setLabel("Hasło");

        layout.add(email,password);
    }

    private void addBinders() {
        binder=new Binder<User>();

        binder.forField(email)
                .withValidator(new EmailValidator("Nieprawidłowy adres e-mail"))
                .bind(User::getMail, User::setMail);

        binder.forField(password)
                .withValidator(new StringLengthValidator(
                        "Podaj swoje hasło", 5, null))
                .bind(User::getPassword, User::setPassword);
    }

    private void addButton() {
        login=new Button("Zaloguj się");
        setLoginButtonListener();
        layout.add(login);
    }

    private void addLabel() {
        infoLabel=new Label();
        layout.add(infoLabel);
    }
    private void setLoginButtonListener(){
        login.addClickListener(event->{
            binder.validate();
            if(binder.isValid()) {
                loginUser();
            }
        });
        login.addClickShortcut(Key.ENTER);
    }

    private void loginUser() {
        User user = userRepository.findByMail(email.getValue());
        if(user != null) {
            if(user.getPassword().equals(password.getValue())){
                infoLabel.setText("Logowanie się powiodło!");
                VaadinSession session=VaadinSession.getCurrent();
                session.setAttribute("user", email.getValue());
                InternalLayout.loggedIn();
            }
            else infoLabel.setText("Twój e-mail albo hasło są nieprawidłowe. Spróbuj ponownie");
        }
        else infoLabel.setText("Użytkownik z takim adresem e-mail nie istnieje. Spróbuj ponownie");
    }
}
