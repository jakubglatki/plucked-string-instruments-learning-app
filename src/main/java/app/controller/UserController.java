package app.controller;


import app.model.User.User;
import app.model.User.UserRepository;
import app.model.User.UserType;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Route("/users")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAll() {
        List<User> users = this.userRepository.findAll();
        return users;
    }

    @PostMapping
    public void insert(@RequestBody User user){
        this.userRepository.insert(user);
    }

    @PutMapping
    public void update(@RequestBody User user){
        this.userRepository.save(user);
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable("id")String id){
        Optional<User> user = this.userRepository.findById(id);
        return user;
    }

    @GetMapping("/firstName")
    public List<User> getUserByFirstName(@RequestParam String firstName) {
        List<User> user = this.userRepository.findByFirstName(firstName);
        return user;
    }


    @GetMapping("/lastName")
    public List<User> getUserByLastName(@RequestParam String lastName) {
        List<User> user = this.userRepository.findByLastName(lastName);
        return user;
    }

    @GetMapping("/userType")
    public List<User> getUserByUserType(@RequestParam UserType userType) {
        List<User> user = this.userRepository.findByUserType(userType);
        return user;
    }


    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
    }
}
