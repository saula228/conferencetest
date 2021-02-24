package ru.kulikov.saula.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kulikov.saula.entity.Schedule;


public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
}
