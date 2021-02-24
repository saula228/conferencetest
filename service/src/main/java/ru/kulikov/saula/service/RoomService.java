package ru.kulikov.saula.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kulikov.saula.entity.Room;
import ru.kulikov.saula.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room findById(int theId) {
        Optional<Room> result = roomRepository.findById(theId);

        Room theRoom = null;

        if (result.isPresent()) {
            theRoom = result.get();
        }
        else {
            throw new RuntimeException("Did not find room id - " + theId);
        }

        return theRoom;
    }

}
