package com.example.scienceConference.service;

import com.example.scienceConference.entities.User;
import com.example.scienceConference.exception.NoElementsFindException;
import com.example.scienceConference.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomService {
    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Map<Long, Long> findRoomsWithPresentations() throws Exception {
        Map<Long, Long> result = new HashMap<>();

        List<Object[]> list = roomRepository.findPresentationsRoom();
        if (list.isEmpty()){
            throw new NoElementsFindException("No found conference rooms");
        }

        for (Object[] temp : list){
            result.put((Long)temp[0], (Long)temp[1]);
        }

        return result;
    }
}
