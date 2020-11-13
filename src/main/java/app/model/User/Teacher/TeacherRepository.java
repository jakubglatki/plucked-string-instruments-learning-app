package app.model.User.Teacher;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher,String> {

    public ArrayList<Teacher> findByFirstName(String firstName);
    public ArrayList<Teacher> findByLastName(String lastName);
}
