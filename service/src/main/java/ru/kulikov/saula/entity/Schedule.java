package ru.kulikov.saula.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_schedule")
public class Schedule {

    @Column(name = "time")
    private int time;

    @Column(name = "room_id")
    private int room_id;

    @Id
    @Column(name = "pres_id")
    private int pres_id;

    public Schedule() {
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getPres_id() {
        return pres_id;
    }

    public void setPres_id(int pres_id) {
        this.pres_id = pres_id;
    }

}
