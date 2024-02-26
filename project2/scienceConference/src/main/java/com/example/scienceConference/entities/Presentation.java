package com.example.scienceConference.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="presentation")
public class Presentation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User user;

//    @OneToOne
//    @JoinColumn(name = "conference_id", referencedColumnName = "id")
    @OneToOne(mappedBy = "presentation")
    @JsonIgnore
    private Conference conference;
}

