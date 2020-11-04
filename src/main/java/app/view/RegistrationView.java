package app.view;

import app.model.User.Student.Student;
import app.model.User.Student.StudentRepository;
import app.model.User.Teacher.Teacher;
import app.model.User.Teacher.TeacherRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("registration")
public class RegistrationView extends VerticalLayout {

    private VerticalLayout layout;
    private RadioButtonGroup<String> radioGroup;
    @Autowired
    private TeacherRepository teacherBinder;
    @Autowired
    private StudentRepository studentBinder;
    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private PasswordField password;

    public RegistrationView()
    {
        setupLayout();
        addForms();
        addRadioButtons();
        addButtons();
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

        lastName= new TextField();
        addTextFieldWithDescription(lastName, "Last name");

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
        Button save= new Button("Save");
        Button reset =new Button("Clear");
        buttons.add(save,reset);
        setSaveButtonListener(save);
        setResetButtonListener(reset);
        layout.add(buttons);
    }


    private void setSaveButtonListener(Button save) {
        EmailValidator emailValidator=new EmailValidator("Your e-mail address is incorrect.");
        save.addClickListener(event->{
           // if(emailValidator.)
            if(this.radioGroup.getValue().equals("Teacher")){
                teacherBinder.save(new Teacher(firstName.getValue(), lastName.getValue(), email.getValue(), password.getValue()));
            }
            else if(this.radioGroup.getValue().equals("Student")) {
                studentBinder.save(new Student(firstName.getValue(), lastName.getValue(), email.getValue(), password.getValue()));
            }
            else return;
        });
    }

    private void setResetButtonListener(Button reset) {
        reset.addClickListener(event-> {
            firstName.clear();
            lastName.clear();
            email.clear();
            password.clear();
            radioGroup.clear();
        });
    }
}
