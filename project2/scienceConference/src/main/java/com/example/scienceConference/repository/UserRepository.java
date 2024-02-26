package com.example.scienceConference.repository;

import com.example.scienceConference.entities.Role;
import com.example.scienceConference.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByEmail(String email);

    @Query("select u from User u inner join Reservation r on r.user.id = u.id")
    Page<User> findConferenceMembers(Pageable pageable);

    @Query("select u from User u inner join Role r on u.role.id = r.id order by r.type desc")
    List<User> findConferenceMembersWithRole();

    @Query("select u from User u inner join Role r on u.role.id = r.id where r.type = :role")
    List<User> findConferenceMembersByRole(@Param("role") String role);

    @Query("select u from User u where u.country = :country")
    List<User> findMembersByCountry(@Param("country") String country);

    //@Query("select u, count(p) from User u join Presentation p on u.id = p.user.id group by u order by 2 desc limit 1")
    //List<User> findMemberWithMostPresentations();
    User findFirstByOrderByPresentationsDesc();
}
