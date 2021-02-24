package ru.kulikov.saula.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kulikov.saula.entity.User;
import ru.kulikov.saula.exception.ResourceNotFoundException;
import ru.kulikov.saula.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int theId) {
        Optional<User> result = userRepository.findById(theId);

        User theUser = null;

        if (result.isPresent()) {
            theUser = result.get();
        }
        else {
            // we didn't find the user
            throw new ResourceNotFoundException("Did not find user id - " + theId);
        }

        return theUser;
    }

    public void save(User theUser) {
        userRepository.save(theUser);
    }

    public void deleteById(int theId) {
        userRepository.deleteById(theId);
    }

    public User findByUsername(String name){ return userRepository.findByUsername(name); }

}
