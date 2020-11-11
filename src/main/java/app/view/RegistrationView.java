package app.view;

import app.model.User.Student.Student;
import app.model.User.Student.StudentRepository;
import app.model.User.Teacher.Teacher;
import app.model.User.Teacher.TeacherRepository;
import app.model.User.User;
import app.model.User.UserRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Route(value="registration", layout = InternalLayout.class)
public class RegistrationView extends VerticalLayout {

    private VerticalLayout layout;
    private RadioButtonGroup<String> radioGroup;
    @Autowired
    private TeacherRepository teacherBinder;
    @Autowired
    private StudentRepository studentBinder;
    @Autowired
    private UserRepository userRepository;
    private Binder<User> binder;
    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private PasswordField password;
    private Label infoLabel;

    public RegistrationView()
    {
        binder=new Binder<User>();
        setupLayout();
        addForms();
        addBinders();
        addRadioButtons();
        addButtons();
        addInfoLabel();
        add(layout);
        setSizeFull();
        setHorizontalComponentAlignment(Alignment.CENTER, layout);
    }


    private void setupLayout(){
        layout=new VerticalLayout();
        layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    private void addForms(){
        firstName= new TextField();
        addTextFieldWithDescription(firstName, "First name");
        firstName.setRequiredIndicatorVisible(true);


        lastName= new TextField();
        addTextFieldWithDescription(lastName, "Last name");
        lastName.setRequiredIndicatorVisible(true);

        email= new TextField();
        addTextFieldWithDescription(email, "E-mail");

        addPasswordForm();
    }

    private void addTextFieldWithDescription(TextField textField, String name)
    {
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setLabel(name);
        layout.add(textField);
    }

    private void addPasswordForm()
    {
        password= new PasswordField();
        password.setValueChangeMode(ValueChangeMode.EAGER);
        password.setLabel("Password");
        password.setRequiredIndicatorVisible(true);
        layout.add(password);
    }


    private void addRadioButtons() {
        radioGroup = new RadioButtonGroup<>();
        radioGroup.setLabel("Type of user");
        radioGroup.setItems("Teacher", "Student");
        layout.add(radioGroup);
    }

    private void addButtons(){
        HorizontalLayout buttons = new HorizontalLayout();
        Button save= new Button("Register");
        Button reset =new Button("Clear");
        buttons.add(save,reset);
        setSaveButtonListener(save);
        setResetButtonListener(reset);
        layout.add(buttons);
    }

    private void addInfoLabel(){
        infoLabel=new Label();
        layout.add(infoLabel);
    }

    private void addBinders(){
        binder.forField(firstName)
                .withValidator(new StringLengthValidator(
                        "Please add the first name", 1, null))
                .bind(User::getFirstName, User::setFirstName);

        binder.forField(lastName)
                .withValidator(new StringLengthValidator(
                        "Please add the last name", 1, null))
                .bind(User::getLastName, User::setLastName);

        binder.forField(email)
                .withValidator(new EmailValidator("Incorrect email address"))
                .bind(User::getMail, User::setMail);

        binder.forField(password)
                .withValidator(new StringLengthValidator(
                        "Please add the password of minimum length of 5", 5, null))
                .bind(User::getPassword, User::setPassword);
    }

    private void setSaveButtonListener(Button save) {
        save.addClickListener(event->{
            infoLabel.setText("");
            if(binder.isValid()) {
                if(checkIfEmailExists()) {
                    infoLabel.setText("Registration was successful!");
                    addUserData();
                }
                else infoLabel.setText("User with that e-mail address already exists");
            }
            else setErrorMessage();
        });
        save.addClickShortcut(Key.ENTER);
    }

    private boolean checkIfEmailExists(){

        User user = userRepository.findByMail(email.getValue());
        if(user==null)
            return true;
        else
            return false;
    }

    private void addUserData(){
        if (this.radioGroup.getValue() != null) {
            infoLabel.setText("");
            if (this.radioGroup.getValue().equals("Teacher")) {
                teacherBinder.save(new Teacher(firstName.getValue(), lastName.getValue(), email.getValue(), password.getValue()));
            } else if (this.radioGroup.getValue().equals("Student")) {
                studentBinder.save(new Student(firstName.getValue(), lastName.getValue(), email.getValue(), password.getValue()));
            }
        }
        else  infoLabel.setText("Please choose type of your account (Teacher or Student)");
    }

    private void setErrorMessage(){
        BinderValidationStatus<User> validate = binder.validate();
        String errorText = validate.getFieldValidationStatuses()
                .stream().filter(BindingValidationStatus::isError)
                .map(BindingValidationStatus::getMessage)
                .map(Optional::get).distinct()
                .collect(Collectors.joining(", "));
        infoLabel.setText("There are errors: " + errorText);
    }

    private void setResetButtonListener(Button reset) {
        reset.addClickListener(event->{
            infoLabel.setText("");
            firstName.clear();
            lastName.clear();
            email.clear();
            password.clear();
            radioGroup.clear();
        });
    }


}
