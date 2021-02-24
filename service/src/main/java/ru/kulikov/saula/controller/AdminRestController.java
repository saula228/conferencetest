package ru.kulikov.saula.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kulikov.saula.entity.Presentation;
import ru.kulikov.saula.entity.User;
import ru.kulikov.saula.exception.ResourceNotFoundException;
import ru.kulikov.saula.service.PresentationService;
import ru.kulikov.saula.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private PresentationService presentationService;

    //Список всех зарегестриванных пользователей
    @GetMapping("/users") //http://localhost:8181/admin/users
    public List<User> findAll() {
        return userService.findAll();
    }

    //Добавление нового пользователя
    @PostMapping("/users") //http://localhost:8181/admin/users
    public User addUser(@RequestBody User theUser) {

        ArrayList<User> users = (ArrayList<User>) userService.findAll();

        for(User user : users){
            if(user.getUsername().equals(theUser.getUsername()))
                throw new RuntimeException("User with name: " + user.getUsername() + " already exists");
        }

        theUser.setId(0);

        theUser.setRole("ROLE_USER");

        String s = theUser.getPassword();

        theUser.setPassword(bCryptPasswordEncoder.encode(s));

        userService.save(theUser);

        return theUser;
    }

    //Удаление пользователя
    @DeleteMapping("/users/{userId}") //http://localhost:8181/admin/users/
    public String deleteUser(@PathVariable int userId) {

        User tempUser = userService.findById(userId);

        if (tempUser == null) {
            throw new RuntimeException("User id not found - " + userId);
        }

        userService.deleteById(userId);

        return "Deleted user id - " + userId;
    }

    //Повышение юзера до "Докладчика"
    @PutMapping("/user/{userId}") //http://localhost:8181/admin/user/
    public User updateUser(@PathVariable int userId) {

        User tempUser = userService.findById(userId);

        if (tempUser == null) {
            throw new ResourceNotFoundException("User id not found - " + userId);
        }

        tempUser.setRole("ROLE_PRESENTER");

        userService.save(tempUser);

        return tempUser;
    }

    //Список всех существующих презентаций
    @GetMapping("/presentations") //http://localhost:8181/admin/presentations
    public List<Presentation> findAllPresentations() {
        return presentationService.findAll();
    }
}
