package com.example.scienceConference.repository;

import com.example.scienceConference.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("select r.id, count(c.presentation.id) as presentation_number " +
            "from Room r " +
            "left join Conference c on r.id = c.room.id " +
            "group by r.id")
    List<Object[]> findPresentationsRoom();
}
