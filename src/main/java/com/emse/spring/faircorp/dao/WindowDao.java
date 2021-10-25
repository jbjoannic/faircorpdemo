package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WindowDao extends JpaRepository<Window, Long> {

    @Query("select w from Window w")
    List<Window> findAll();

    @Query("select w from Window w where w.room.id = :id")
    List<Window> findByRoom(@Param("id") Long id);

    @Query("select w from Window w where w.room.id = :id and w.windowStatus= com.emse.spring.faircorp.model.WindowStatus.OPEN")
    List<Window> findRoomOpenWindows(@Param("id") Long id);

    @Modifying
    @Query("delete from Window w where w.room.id = :id")
    void deleteByRoom(@Param("id") Long id);
}
