package com.example.scienceConference.controller;

import com.example.scienceConference.dto.UserRoleDto;
import com.example.scienceConference.entities.Reservation;
import com.example.scienceConference.entities.User;
import com.example.scienceConference.service.PresentationService;
import com.example.scienceConference.service.ReservationService;
import com.example.scienceConference.service.RoomService;
import com.example.scienceConference.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AdminController {

    private UserService userService;

    private PresentationService presentationService;

    private RoomService roomService;

    private ReservationService reservationService;

    public AdminController(UserService userService, PresentationService presentationService, RoomService roomService, ReservationService reservationService) {
        this.userService = userService;
        this.presentationService = presentationService;
        this.roomService = roomService;
        this.reservationService = reservationService;
    }

    @GetMapping(path="/users", produces = "application/json")
    public ResponseEntity<List<User>> getUsers() throws Exception {
        List<User> users = userService.findAll();
        return new ResponseEntity<List<User>>(users, new HttpHeaders(), HttpStatus.OK);
    }

    //-> 1
    @GetMapping("/members")
    public ResponseEntity<List<User>> getConferenceMembers(@RequestParam(defaultValue = "0") Integer pageNo,
                                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                                           @RequestParam(defaultValue = "id") String sortBy) throws Exception {
        List<User> users = userService.findConferenceMembers(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<User>>(users, new HttpHeaders(), HttpStatus.OK);
    }

    //-> 2
    @GetMapping("/membersWithRole")
    public ResponseEntity<List<UserRoleDto>> getMembersWithRoles() throws Exception {
        List<UserRoleDto> users = userService.findMembersWithRoles();
        return new ResponseEntity<List<UserRoleDto>>(users, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/membersByRole/role")
    public ResponseEntity<List<UserRoleDto>> getMembersByRoles(@RequestParam(defaultValue = "all") String role) throws Exception {
        List<UserRoleDto> users = userService.findMembersByRoles(role);
        return new ResponseEntity<List<UserRoleDto>>(users, new HttpHeaders(), HttpStatus.OK);
    }

    //-> 3
    @GetMapping("/membersByCountry/{country}")
    public ResponseEntity<List<User>> getMembersWithCountry(@PathVariable(required = false) String country) throws Exception {
        List<User> users = new ArrayList<>();

        if (country == null || country.equals("all"))
            users = userService.findAll();
        else
            users = userService.findMembersByCountry(country);

        return new ResponseEntity<List<User>>(users, new HttpHeaders(), HttpStatus.OK);
    }

    //-> 4
    @GetMapping("/presentations")
    public ResponseEntity<Map<Long, String>> getPresentationTitles() throws Exception {
        Map<Long, String> presentations = presentationService.findPresentations();
        return new ResponseEntity<Map<Long, String>>(presentations, new HttpHeaders(), HttpStatus.OK);
    }

    //-> 5
    @GetMapping("/memberWithMostPresentations")
    public ResponseEntity<User> getUserWithMostPresentation() throws Exception {
        User user = userService.findMemberWithMostPresentations();
        return new ResponseEntity<User>(user, new HttpHeaders(), HttpStatus.OK);
    }

    //-> 6
    @GetMapping("/presentationsEachRoom")
    public ResponseEntity<Map<Long, Long>> getRoomsWithPresentations() throws Exception {
        Map<Long, Long> roomsMap = roomService.findRoomsWithPresentations();
        return new ResponseEntity<Map<Long, Long>>(roomsMap, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@Valid @RequestBody User user){
        User savedUser = userService.createUser(user);
        if (savedUser == null){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Error with saved user!");
        }
        return ResponseEntity.ok().body("User saved successfully!");
    }

    @PostMapping("/createReservation")
    public ResponseEntity<String> createReservation(@Valid @RequestBody Reservation reservation){
        Reservation savedReservation = reservationService.createReservation(reservation);
        if (savedReservation == null){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Cannot add reservation!");
        }
        return ResponseEntity.ok().body("Reservation add successfully!");
    }
}
