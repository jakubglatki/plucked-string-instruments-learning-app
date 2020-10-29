package app;

import app.model.User.Student.Student;
import app.model.User.Student.StudentRepository;
import app.model.User.User;
import app.model.User.UserRepository;
import app.model.User.UserType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DBSeeder implements CommandLineRunner {
    private app.model.User.UserRepository UserRepository;
    private StudentRepository studentRepository;

    public DBSeeder(UserRepository UserRepository, StudentRepository studentRepository) {
        this.UserRepository = UserRepository;
        this.studentRepository=studentRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        User elo = new User( "No", "elo", "xd@xd.com", "da", UserType.STUDENT);


        User noelo = new User( "No1", "elo1", "xd@xd.com1", "da1", UserType.STUDENT);

        User xd = new User( "Iks", "De", "xd@xd.com1", "da12", UserType.TEACHER);

        Student student=new Student("Pan","Student","student@student.com","studenthasło");

        // drop all Users
        this.UserRepository.deleteAll();

        //add our Users to the database
        List<User> Users = Arrays.asList(elo, noelo, xd);
        this.UserRepository.save(elo);
        this.UserRepository.save(noelo);
        this.UserRepository.save(xd);
        this.studentRepository.save(student);
    }
}