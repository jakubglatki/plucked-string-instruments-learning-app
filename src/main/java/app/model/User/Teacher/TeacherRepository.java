package app.model.User.Teacher;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher,String> {

    public List<Teacher> findByFirstName(String firstName);
    public List<Teacher> findByLastName(String lastName);
}
