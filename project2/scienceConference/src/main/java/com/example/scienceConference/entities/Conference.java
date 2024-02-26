package com.example.scienceConference.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="conference")
public class Conference {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "is_full")
    private Boolean isFull;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    //@OneToOne(mappedBy = "conference")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "presentation_id", referencedColumnName = "id")
    private Presentation presentation;
}
