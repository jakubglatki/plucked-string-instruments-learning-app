package app.model.User.Student;

import app.model.User.UserType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface StudentRepository extends MongoRepository<Student,String> {

    public ArrayList<Student> findByFirstName(String firstName);
    public ArrayList<Student> findByLastName(String lastName);
    public ArrayList<Student> findByUserType(UserType userType);
}
