package ru.kulikov.saula.sched;

public class ScheduleDTO {

    private String presentationName;

    private String roomName;

    private int time;

    public ScheduleDTO() {
    }

    public ScheduleDTO(String presentationName, String roomName, int time) {
        this.presentationName = presentationName;
        this.roomName = roomName;
        this.time = time;
    }

    public String getPresentationName() {
        return presentationName;
    }

    public void setPresentationName(String presentationName) {
        this.presentationName = presentationName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
