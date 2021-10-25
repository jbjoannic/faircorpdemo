package com.emse.spring.faircorp.api;


import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.HeaterDto;
import com.emse.spring.faircorp.dto.RoomDto;
import com.emse.spring.faircorp.dto.WindowDto;
import com.emse.spring.faircorp.model.*;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/rooms")
@Transactional
public class RoomController {
    private final WindowDao windowDao;
    private final HeaterDao heaterDao;
    private final RoomDao roomDao;
    private final BuildingDao buildingDao;

    public RoomController(WindowDao windowDao, HeaterDao heaterDao, RoomDao roomDao, BuildingDao buildingDao) {
        this.windowDao = windowDao;
        this.heaterDao = heaterDao;
        this.roomDao = roomDao;
        this.buildingDao = buildingDao;
    }

    @GetMapping
    public List<RoomDto> findAll() {
        return roomDao.findAll().stream().map(RoomDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public RoomDto findById(@PathVariable Long id) {
        return roomDao.findById(id).map(RoomDto::new).orElse(null);
    }



    @PostMapping
    public RoomDto create(@RequestBody RoomDto dto){
        Building building = buildingDao.getById(dto.getBuildingId());
        Room room = null;

        if (dto.getId() == null) {
            room = roomDao.save(new Room(dto.getFloor(), dto.getName(), building));
        } else {
            room = roomDao.getById(dto.getId());
            room.setCurrentTemperature(dto.getCurrentTemperature());
        }
        return new RoomDto(room);
    }

    @DeleteMapping(path = "/{id}/delete")
    public void delete(@PathVariable Long id) {
        windowDao.deleteByRoom(id);
        heaterDao.deleteByRoom(id);
        roomDao.deleteById(id);
    }

    @PutMapping(path = "/{id}/switchWindow")
    public List<WindowDto> switchWindowStatus(@PathVariable Long id) {
        List<Window> windows = windowDao.findByRoom(id);
        for (Window window : windows) {
            window.setWindowStatus(window.getWindowStatus() == WindowStatus.OPEN ? WindowStatus.CLOSED: WindowStatus.OPEN);
        }
        return windows.stream().map(WindowDto::new).collect(Collectors.toList());
    }

    @PutMapping(path = "/{id}/switchHeater")
    public List<HeaterDto> switchHeaterStatus(@PathVariable Long id) {
        List<Heater> heaters = heaterDao.findByRoom(id);
        for (Heater heater : heaters) {
            heater.setStatus(heater.getStatus() == Status.ON ? Status.OFF: Status.ON);
        }
        return heaters.stream().map(HeaterDto::new).collect(Collectors.toList());
    }
}
