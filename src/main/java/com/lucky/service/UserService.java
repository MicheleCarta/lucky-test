package com.lucky.service;

import com.lucky.service.dto.UserDTO;
import com.lucky.service.dto.UserResponse;
import com.lucky.service.exceptions.UserNotFoundException;
import com.lucky.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void saveUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.email()).isPresent()) {
            throw new UserNotFoundException("User already exist with email " + userDTO.email());
        }
        User user = UserMapper.INSTANCE.userFromUserDTO(userDTO);
        userRepository.save(user);
    }

    public void updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        existingUser.setFirstName(userDTO.firstName());
        existingUser.setLastName(userDTO.lastName());
        existingUser.setDob(userDTO.dob());

        userRepository.save(existingUser);
    }

    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper.INSTANCE::userToUserDTO)
                .orElseThrow(() -> new UserNotFoundException("No User found for email " + email));
    }

    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No User found for id " + id));

        UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(user);

        int age = userDTO.age();
        return new UserResponse(user.getId(), userDTO, age);
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(user);
                    int age = userDTO.age(); // Calculate age based on the date of birth
                    return new UserResponse(user.getId(), userDTO, age); // Create UserResponse
                })
                .collect(Collectors.toList());
    }

    public void deleteUsers() {
        userRepository.deleteAll();
    }

    public void deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
    }


}
