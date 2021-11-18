package com.emse.spring.faircorp.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="BUILDING")
public class Building {



    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private Double outsideTemperature;

    @OneToMany(mappedBy = "building")
    private Set<Room> room;

    public Building(){
    }

    public Building(Double outsideTemperature){
        this.outsideTemperature = outsideTemperature;
    }
    public Long getId(){
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getOutsideTemperature() {
        return this.outsideTemperature;
    }

    public void setOutsideTemperature(Double outsideTemperature) {
        this.outsideTemperature = outsideTemperature;
    }

    public Set<Room> getRoom() {
        return room;
    }

    public void setRoom(Set<Room> room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
