package app.model.Group;

import app.model.User.Student.Student;
import app.model.User.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface GroupRepository extends MongoRepository<Group,String> {
    public ArrayList<Group> findByTeacher(User user);
    public ArrayList<Group> findByStudentsContaining(Student student);
}
