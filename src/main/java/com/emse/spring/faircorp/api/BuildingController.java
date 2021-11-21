package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.BuildingDto;
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

    @PostMapping(path = "/create")
    public BuildingDto create(@RequestBody BuildingDto dto) {
        Building building = null;
        if (dto.getId() == null) {
            building = buildingDao.save(new Building(dto.getOutsideTemperature()));
        } else {
            building = buildingDao.getById(dto.getId());
            building.setOutsideTemperature(dto.getOutsideTemperature());
        }
        return new BuildingDto(building);
    }

    @DeleteMapping(path = "/{id}/delete")
    public void delete(@PathVariable Long id){
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
