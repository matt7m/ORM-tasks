package com.example.scienceConference.service;

import com.example.scienceConference.dto.UserRoleDto;
import com.example.scienceConference.entities.Role;
import com.example.scienceConference.entities.User;
import com.example.scienceConference.exception.NoElementsFindException;
import com.example.scienceConference.repository.RoleRepository;
import com.example.scienceConference.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User createUser(User user){
        if (userRepository.existsUserByEmail(user.getEmail())){
            return null;
        }

        return userRepository.save(user);
    }

    public List<User> findAll() throws Exception {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()){
            throw new NoElementsFindException("No found users");
        }
        return users;
    }

    public List<User> findConferenceMembers(Integer pageNo, Integer pageSize, String sortBy) throws Exception {
        //List<User> users = userRepository.findConferenceMembers();
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<User> users = userRepository.findConferenceMembers(pageable);

        if (users.isEmpty()){
            throw new NoElementsFindException("No members in conference");
        }

        return users.getContent();
    }

    public List<UserRoleDto> findMembersByRoles(String role) throws Exception {
        List<UserRoleDto> users = new ArrayList<>();

        if (role.equals("all")){
            users = userRepository.findConferenceMembersWithRole()
                    .stream()
                    .map(this::convertEntity)
                    .collect(Collectors.toList());
        } else {
            users = userRepository.findConferenceMembersByRole(role)
                    .stream()
                    .map(this::convertEntity)
                    .collect(Collectors.toList());
        }

        if (users.isEmpty()){
            throw new NoElementsFindException("No members in database");
        }
        return users;
    }

    public List<UserRoleDto> findMembersWithRoles() throws Exception {
        List<UserRoleDto> users = userRepository.findConferenceMembersWithRole()
                .stream()
                .map(this::convertEntity)
                .collect(Collectors.toList());

        if (users.isEmpty()){
            throw new NoElementsFindException("No members in database");
        }
        return users;
    }

    public List<User> findMembersByCountry(String country) throws Exception {
        List<User> users = userRepository.findMembersByCountry(country);

        if (users.isEmpty()){
            throw new NoElementsFindException("No members in conference");
        }
        return users;
    }

    public User findMemberWithMostPresentations() throws Exception {
        //List<User> user = userRepository.findMemberWithMostPresentations();
        User user = userRepository.findFirstByOrderByPresentationsDesc();

        if (user == null){
            throw new NoElementsFindException("No members in conference");
        }
        return user;
    }

    private UserRoleDto convertEntity(User user){
        UserRoleDto userRoleDto = new UserRoleDto();
        userRoleDto.setUserId(user.getId());
        userRoleDto.setFirstname(user.getFirstName());
        userRoleDto.setLastname(user.getLastName());
        userRoleDto.setCountry(user.getCountry());
        userRoleDto.setEmail(user.getEmail());
        userRoleDto.setRole(user.getRole().getType());
        return userRoleDto;
    }
}
