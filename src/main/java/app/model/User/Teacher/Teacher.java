package app.model.User.Teacher;

import app.model.User.Grade.Grade;
import app.model.User.User;
import app.model.User.UserType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Users")
public class Teacher extends User {

    public Teacher(){}

    public Teacher(String firstName, String lastName, String mail, String password) {
        super(firstName, lastName, mail, password, UserType.TEACHER);
    }

    public Teacher(String mail, String password) {
        super(mail, password, UserType.TEACHER);
    }

}


