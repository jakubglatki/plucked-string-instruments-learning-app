/*


package app;

import app.model.Group.Group;
import app.model.Group.GroupRepository;
import app.model.Instrument.Instrument;
import app.model.Instrument.InstrumentRepository;
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

    @Autowired
    private InstrumentRepository instrumentRepository;

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

        //Instrument guitar = new Instrument("Guitar");


        ArrayList<Teacher> teacher = teacherRepository.findByFirstName("Jurgen");
        ArrayList<Student> students = studentRepository.findByLastName("Henderson");
        students.add(studentRepository.findByLastName("Keita").get(0));
        students.add(studentRepository.findByLastName("Milner").get(0));
*/
/*

        ArrayList<Student> students = studentRepository.findByLastName("Salah");
        students.add(studentRepository.findByLastName("Mane").get(0));
        students.add(studentRepository.findByLastName("Firmino").get(0));
        students.add(studentRepository.findByLastName("Jota").get(0));

        Student student1=studentRepository.findByLastName("Firmino").get(0);
        Grade grade=new Grade(teacher.get(0),5,"Piosenka La La La");
        ArrayList<Grade> grades=new ArrayList<>();
        grades.add(grade);
        student1.setGrades(grades);

        Group group = new Group("Ukuleliści", new Instrument("Ukulele"), teacher.get(0), students);

        String date = "1-12-2020; 18-30";
        String date1 = "8-12-2020; 18-30";
        ArrayList<String> dates = new ArrayList();
        dates.add(date);
        dates.add(date1);
        group.setClassesDates(dates);
        studentRepository.save(student1);
        try {
            groupRepository.save(group);
        }
        catch (Exception e){System.out.println(e.toString());}

        System.out.println(groupRepository.findByStudentsMailContaining("mo@salah.com").get(0).getName());*//*


        Instrument ukulele=new Instrument("Ukulele");
        Instrument guitar= new Instrument("Guitar");
        instrumentRepository.save(guitar);
        instrumentRepository.save(ukulele);
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