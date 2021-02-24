package ru.kulikov.saula.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kulikov.saula.entity.Presentation;
import ru.kulikov.saula.entity.Room;
import ru.kulikov.saula.entity.Schedule;
import ru.kulikov.saula.entity.User;
import ru.kulikov.saula.exception.BadRequestException;
import ru.kulikov.saula.service.PresentationService;
import ru.kulikov.saula.service.RoomService;
import ru.kulikov.saula.service.ScheduleService;
import ru.kulikov.saula.service.UserService;

import java.util.ArrayList;
import java.util.Set;

@RestController
@RequestMapping("/presenter")
public class PresenterRestController {

    @Autowired
    UserService userService;

    @Autowired
    PresentationService presentationService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private RoomService roomService;

    //Выдача имени залогиневшегося пользователя
    private String getCurrentUsername(){
        String rawUserInfo = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String ActiveUserName;

        ActiveUserName = rawUserInfo.split("\\;")[0];
        ActiveUserName = ActiveUserName.substring(ActiveUserName.lastIndexOf(":") + 1);
        ActiveUserName = ActiveUserName.trim();

        return ActiveUserName;
    }

    //Выдача презентаций пользователя
    @GetMapping("/") //http://localhost:8181/presenter/
    private Set<Presentation> getPresentations(){

        User tempUser = userService.findByUsername(getCurrentUsername());

        Set<Presentation> presentations = tempUser.getPresentations();

        return presentations;
    }

    //Добавление новой презентации
    @PostMapping("/presentation") //http://localhost:8181/presenter/presentation
    private void addNewPresentation(@RequestBody Presentation presentation){

        presentationService.save(presentation);

        User tempUser = userService.findByUsername(getCurrentUsername());

        tempUser.getPresentations().add(presentation);

        userService.save(tempUser);

    }

    //Добавление существующей презентации пользователю
    @PutMapping("/presentation/{presentationId}") //http://localhost:8181/presenter/presentation/
    private  void addExistingPresentation(@PathVariable int presentationId){

        Presentation presentation = presentationService.findById(presentationId);

        User tempUser = userService.findByUsername(getCurrentUsername());

        Set<Presentation> set = tempUser.getPresentations();

        set.add(presentation);

        tempUser.setPresentations(set);

        userService.save(tempUser);

    }

    //Удаление презентации у пользователя
    //Если этой презентации не осталось у других пользователей, удалить из списка презентаций
    @DeleteMapping("/{presentationId}") //http://localhost:8181/presenter/
    private void deletePresentation(@PathVariable int presentationId){

        Presentation presentation = presentationService.findById(presentationId);

        User tempUser = userService.findByUsername(getCurrentUsername());

        tempUser.getPresentations().remove(presentation);

        userService.save(tempUser);

        boolean check = false;

        ArrayList<User> users = (ArrayList<User>) userService.findAll();

        for(User user : users){
            Set<Presentation> presentations = (Set<Presentation>) user.getPresentations();
            for(Presentation presentation1 : presentations){
                if(presentation1.getId()==presentationId){
                    check = true;
                }
            }
        }
        //Одновременно с удалением презентации нужно удалять строку в расписании
        if(!check){

            Schedule tempSchedule = scheduleService.findByIdForPresentation(presentationId);
            if(tempSchedule!=null){
                scheduleService.deleteById(presentationId);
            }
            presentationService.deleteById(presentationId);
        }

    }

    //Добавление/изменение презентации в расписание
    @PostMapping("/schedule") //http://localhost:8181/presenter/schedule
    public Schedule addSchedule(@RequestBody Schedule theSchedule) {

        boolean check = false;
        User tempUser = userService.findByUsername(getCurrentUsername());
        Set<Presentation> presentations = (Set<Presentation>) tempUser.getPresentations();

        for(Presentation presentation : presentations){
            if(presentation.getId()==theSchedule.getPres_id())
                check = true;
        }

        if(!check)
            throw new RuntimeException("Presentation with id " + theSchedule.getPres_id() + "does not exist");

        int[] timeArray = new int[]{10, 12, 14, 16, 18, 20};
        ArrayList<Room> roomList = (ArrayList<Room>) roomService.findAll();
        ArrayList<Schedule> schedules = (ArrayList<Schedule>) scheduleService.findAll();

        boolean timeCheck = false;
        boolean RoomCheck = false;
        boolean ScheduleCollision = false;

        for (Room room : roomList) {
            if (room.getId() == theSchedule.getRoom_id())
                RoomCheck = true;
        }

        if (!RoomCheck)
            throw new BadRequestException("Incorrect room_id value - " + theSchedule.getRoom_id());

        for (int i : timeArray) {
            if (i == theSchedule.getTime())
                timeCheck = true;
        }

        if (!timeCheck)
            throw new BadRequestException("Incorrect time value - " + theSchedule.getRoom_id());

        for (Schedule schedule : schedules) {
            if ((schedule.getRoom_id() == theSchedule.getRoom_id()) && (schedule.getTime() == theSchedule.getTime()))
                ScheduleCollision = true;
        }

        if (ScheduleCollision)
            throw new BadRequestException("Presentation in this room and time already exists");

        scheduleService.save(theSchedule);

        return theSchedule;
    }



}
