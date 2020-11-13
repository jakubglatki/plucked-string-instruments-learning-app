package app.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    public ArrayList<User> findByFirstName(String firstName);
    public ArrayList<User> findByLastName(String lastName);
    public ArrayList<User> findByUserType(UserType userType);
    public User findByMail(String mail);
}
