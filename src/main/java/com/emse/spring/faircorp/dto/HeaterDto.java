package com.emse.spring.faircorp.dto;


import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.Status;

public class HeaterDto {
    private Long id;
    private String name;
    private Double power;
    private Status heaterStatus;
    private String roomName;
    private Long roomId;

    public HeaterDto(){
    }

    public HeaterDto(Heater heater) {
        this.id = heater.getId();
        this.name = heater.getName();
        this.heaterStatus = heater.getStatus();
        this.roomName = heater.getRoom().getName();
        this.roomId = heater.getRoom().getId();
        this.power = heater.getPower();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Status getHeaterStatus() {
        return heaterStatus;
    }

    public void setHeaterStatus(Status heaterStatus) {
        this.heaterStatus = heaterStatus;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
