package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.BuildingDto;
import com.emse.spring.faircorp.dto.RoomDto;
import com.emse.spring.faircorp.model.Building;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/buildings")
@Transactional
public class BuildingController {
    private final WindowDao windowDao;
    private final HeaterDao heaterDao;
    private final RoomDao roomDao;
    private final BuildingDao buildingDao;

    public BuildingController(WindowDao windowDao, HeaterDao heaterDao, RoomDao roomDao, BuildingDao buildingDao) {
        this.windowDao = windowDao;
        this.heaterDao = heaterDao;
        this.roomDao = roomDao;
        this.buildingDao = buildingDao;
    }

    @GetMapping
    public List<BuildingDto> findAll() {
        return buildingDao.findAll().stream().map(BuildingDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public BuildingDto findById(@PathVariable Long id) {
        return buildingDao.findById(id).map(BuildingDto::new).orElse(null);
    }

    @GetMapping(path = "/{id}/rooms")
    public List<RoomDto> findRoomsByBuilding(@PathVariable Long id) {
        return roomDao.findByBuilding(id).stream().map(RoomDto::new).collect(Collectors.toList());
    }

    @PostMapping(path = "/create")
    public BuildingDto create(@RequestBody BuildingDto buildingDto) {
        Building building = null;
        if (buildingDto.getId() == null) {
            building = buildingDao.save(new Building(buildingDto.getName(), buildingDto.getOutsideTemperature()));
        } else {
            building = buildingDao.getById(buildingDto.getId());
            building.setOutsideTemperature(buildingDto.getOutsideTemperature());
        }
        return new BuildingDto(building);
    }

    @DeleteMapping(path = "/{id}/delete")
    public void delete(@PathVariable Long id) {
        List<Long> idRooms = roomDao.findIdByBuilding(id);
        for (Long idRoom : idRooms) {
            windowDao.deleteByRoom(idRoom);
            heaterDao.deleteById(idRoom);
        }
        for (Long idRoom : idRooms) {
            roomDao.deleteById(idRoom);
        }
        buildingDao.deleteById(id);
    }
}
