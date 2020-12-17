

package app;

import app.controller.ChordController;
import app.model.Group.Group;
import app.model.Group.GroupRepository;
import app.model.Instrument.Chord;
import app.model.Instrument.Instrument;
import app.model.Instrument.InstrumentRepository;
import app.model.Instrument.InstrumentString;
import app.model.Lesson.Lesson;
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
import java.util.*;

@Component
public class DBSeeder implements CommandLineRunner {
    private app.model.User.UserRepository UserRepository;
    private StudentRepository studentRepository;
    private GroupRepository groupRepository;

    @Autowired
    private TeacherRepository teacherRepository;
    private InstrumentRepository instrumentRepository;

    public DBSeeder(UserRepository UserRepository, StudentRepository studentRepository, GroupRepository groupRepository, InstrumentRepository instrumentRepository) {
        this.UserRepository = UserRepository;
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.instrumentRepository=instrumentRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
/*
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
        Instrument guitar=instrumentRepository.findByName("Gitara");
/*        Chord c=new Chord();
        c= ChordController.setGuitarChord("C", "0","1","0","2","3","-");
        Chord cm=new Chord();
        cm= ChordController.setGuitarChord("Cm", "3","4","5","5","-","-");
        Chord c7=new Chord();
        c7= ChordController.setGuitarChord("C7", "0","1","3","2","3","-");

        ArrayList<Chord> chords=new ArrayList<>(List.of(c,cm,c7));
        guitar.setChords(chords);
        try {
            Chord xd=instrumentRepository.findByChordsName("C");
            System.out.println(xd.toString());
        }
        catch (Exception e) {System.out.println(e);}*/

/*        Group group=groupRepository.findAll().get(0);
        Instrument instrument=group.getLessons().get(0).getInstrument();
        ArrayList<String> instrumetsClassName=new ArrayList<>();

        for(int i=119;i>59;i--){
            instrument.getStringLayoutClass().remove(i);
        }

*//*        instrument.setStringLayoutClass(instrumetsClassName);
        group.getLessons().get(0).setInstrument(instrument);


        instrument.setChords(guitar.getChords());
        group.setInstrument(instrument);*//*
        groupRepository.save(group);*/

    }
}

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

        System.out.println(groupRepository.findByStudentsMailContaining("mo@salah.com").get(0).getName());



        Instrument ukulele=new Instrument("Ukulele");
        Instrument guitar= new Instrument("Guitar");
        InstrumentString string1= new InstrumentString();
        InstrumentString string2= new InstrumentString();
        InstrumentString string3= new InstrumentString();
        InstrumentString string4= new InstrumentString();
        InstrumentString string5= new InstrumentString();
        InstrumentString string6= new InstrumentString();
        ArrayList<InstrumentString> stringss= new ArrayList<InstrumentString>(Arrays.asList(string1,string2,string3,string4));
        ukulele.setStrings(stringss);



        Group group=groupRepository.findAll().get(0);
        group.setInstrument(ukulele);
        groupRepository.save(group);
        stringss.add(string5);
        stringss.add(string6);
        guitar.setStrings(stringss);
        instrumentRepository.save(guitar);

        Instrument ukulele=instrumentRepository.findAll().get(0);
        group.setInstrument(ukulele);
        groupRepository.save(group);
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