package com.example.scienceConference.service;

import com.example.scienceConference.entities.Conference;
import com.example.scienceConference.entities.Reservation;
import com.example.scienceConference.entities.User;
import com.example.scienceConference.repository.ConferenceRepository;
import com.example.scienceConference.repository.ReservationRepository;
import com.example.scienceConference.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;

    private ConferenceRepository conferenceRepository;

    public ReservationService(ReservationRepository reservationRepository, ConferenceRepository conferenceRepository) {
        this.reservationRepository = reservationRepository;
        this.conferenceRepository = conferenceRepository;
    }

    public Reservation createReservation(Reservation reservation){
        List<Conference> lists = conferenceRepository.findAll();

        Conference conference = reservation.getConference();
//        Long temp = conference.getId();
//        if (lists.get(temp.intValue()).getIsFull()){
//            return null;
//        }

        return reservationRepository.save(reservation);
    }

}
