/*

package app;

import app.model.Group.Group;
import app.model.Group.GroupRepository;
import app.model.Instrument.Instrument;
import app.model.User.Grade.Grade;
import app.model.User.Student.Student;
import app.model.User.Student.StudentRepository;
import app.model.User.Teacher.Teacher;
import app.model.User.Teacher.TeacherRepository;
import app.model.User.User;
import app.model.User.UserRepository;
import app.model.User.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

@Component
public class DBSeeder implements CommandLineRunner {
    private app.model.User.UserRepository UserRepository;
    private StudentRepository studentRepository;
    private GroupRepository groupRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public DBSeeder(UserRepository UserRepository, StudentRepository studentRepository, GroupRepository groupRepository) {
        this.UserRepository = UserRepository;
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        User elo = new User("No", "elo", "xd@xd.com", "da", UserType.STUDENT);


        User noelo = new User("No1", "elo1", "xd@xd.com1", "da1", UserType.STUDENT);

        User xd = new User("Iks", "De", "xd@xd.com1", "da12", UserType.TEACHER);

        Student student = new Student("Pan", "Student", "student@student.com", "studenthasło");

        Instrument guitar = new Instrument("Guitar");


        ArrayList<Teacher> teacher = teacherRepository.findByFirstName("Jurgen");

        ArrayList<Student> students = studentRepository.findByLastName("Henderson");
        students.add(studentRepository.findByLastName("Keita").get(0));
        students.add(studentRepository.findByLastName("Milner").get(0));

        Group group = new Group("GuitarM", guitar, teacher.get(0), students);

        String date = "1-12-2020; 18-30";
        String date1 = "8-12-2020; 18-30";
        ArrayList<String> dates = new ArrayList();
        dates.add(date);
        dates.add(date1);
        group.setClassesDates(dates);
        Student student1=studentRepository.findByLastName("Firmino").get(0);

        Grade grade=new Grade(student1, teacher.get(0), 6);
        Grade grade1=new Grade(student1,teacher.get(0), 5);

    }
}

*/




/*        // drop all Users
        this.UserRepository.deleteAll();
        this.studentRepository.deleteAll();
        this.groupRepository.deleteAll();

        //add our Users to the database
        ArrayList<User> Users = Arrays.asArrayList(elo, noelo, xd);
        this.UserRepository.save(elo);
        this.UserRepository.save(noelo);
        this.UserRepository.save(xd);
        this.studentRepository.save(student);
        this.groupRepository.save(group);*//*

    }
}
*/