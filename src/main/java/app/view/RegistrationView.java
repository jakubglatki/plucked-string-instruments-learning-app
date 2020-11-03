package app.view;

import app.model.User.Student.Student;
import app.model.User.Teacher.Teacher;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("registration")
public class RegistrationView extends VerticalLayout {

    private VerticalLayout layout;
    private Binder<Teacher> teacherBinder;
    private Binder<Student> studentBinder;


    public RegistrationView()
    {
        setupBinders();
        setupLayout();
        addForms();
        addRadioButtons();
        addButtons();
        add(layout);
        setSizeFull();
        setHorizontalComponentAlignment(Alignment.CENTER, layout);
    }

    private void setupBinders()
    {
        teacherBinder=new Binder<>();
        studentBinder=new Binder<>();
    }

    private void setupLayout(){
        layout=new VerticalLayout();
        layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    private void addForms(){
        TextField firstName= new TextField();
        addTextFieldWithDescription(firstName, "First name");

        TextField lastName= new TextField();
        addTextFieldWithDescription(lastName, "Last name");

        TextField email= new TextField();
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
        PasswordField passwordField= new PasswordField();
        passwordField.setValueChangeMode(ValueChangeMode.EAGER);
        passwordField.setLabel("Password");
        layout.add(passwordField);
    }


    private void addRadioButtons() {
        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.setLabel("Type of user");
        radioGroup.setItems("Teacher", "Student");
        layout.add(radioGroup);
    }

    private void addButtons(){
        HorizontalLayout buttons = new HorizontalLayout();
        Button save= new Button("Save");
        Button reset =new Button("Reset");
        buttons.add(save,reset);
        layout.add(buttons);
    }
}
