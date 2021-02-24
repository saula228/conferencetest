package ru.kulikov.saula.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kulikov.saula.entity.Schedule;
import ru.kulikov.saula.exception.ResourceNotFoundException;
import ru.kulikov.saula.repository.ScheduleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public Schedule findById(int theId) {
        Optional<Schedule> result = scheduleRepository.findById(theId);

        Schedule theSchedule = null;

        if (result.isPresent()) {
            theSchedule = result.get();
        }
        else {
            throw new ResourceNotFoundException("Did not find schedule id - " + theId);
        }

        return theSchedule;
    }

    public Schedule findByIdForPresentation(int theId) {
        Optional<Schedule> result = scheduleRepository.findById(theId);

        Schedule theSchedule = null;

        if (result.isPresent()) {
            theSchedule = result.get();
        }

        return theSchedule;
    }

    public void save(Schedule theSchedule) {
        scheduleRepository.save(theSchedule);
    }

    public void deleteById(int theId) {
        scheduleRepository.deleteById(theId);
    }
}
