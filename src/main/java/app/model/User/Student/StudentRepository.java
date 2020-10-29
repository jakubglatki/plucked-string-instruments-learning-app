package app.model.User.Student;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends MongoRepository<Student,String> {

    public List<Student> findByFirstName(String firstName);
    public List<Student> findByLastName(String lastName);
}