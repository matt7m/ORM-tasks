package entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cinema_hall")
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinema_hall_id")
    private Long id;

    @Column(name = "seats_number")
    private Integer seatsNumber;

    @OneToMany(mappedBy = "cinemaHall", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Seat> seats;

    @OneToMany(mappedBy = "cinemaHall", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Seanse> seanses;
}
