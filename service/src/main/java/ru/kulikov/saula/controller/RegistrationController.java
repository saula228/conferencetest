package ru.kulikov.saula.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kulikov.saula.entity.User;
import ru.kulikov.saula.service.UserService;

import java.util.ArrayList;


@RestController
@EnableWebSecurity
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    //"Регистрация" пользователя и добавление его в базу данных(всем пользователям присваивается роль USER)
    @PostMapping("/") //http://localhost:8181/registration/
    public User addUser(@RequestBody User theUser) throws Exception {

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

}
