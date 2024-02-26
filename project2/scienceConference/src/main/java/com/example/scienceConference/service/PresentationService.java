package com.example.scienceConference.service;

import com.example.scienceConference.entities.Presentation;
import com.example.scienceConference.entities.User;
import com.example.scienceConference.exception.NoElementsFindException;
import com.example.scienceConference.repository.PresentationRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PresentationService {

    private PresentationRepository presentationRepository;

    public PresentationService(PresentationRepository presentationRepository) {
        this.presentationRepository = presentationRepository;
    }

    public Map<Long, String> findPresentations() throws Exception {
        Map<Long, String> titles = new HashMap<>();

        List<Object[]> list = presentationRepository.findPresentation();
        if (list.isEmpty()){
            throw new NoElementsFindException("No found presentations");
        }

        for (Object[] temp : list){
            titles.put((Long)temp[0], (String)temp[1]);
        }

        return titles;
    }

}
