package com.example.scienceConference.repository;

import com.example.scienceConference.entities.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Long> {
    @Query("select p.id, p.title from Presentation p")
    List<Object[]> findPresentation();
}
