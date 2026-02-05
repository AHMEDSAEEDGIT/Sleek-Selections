package com.ecommerce.sleekselects.service;

import com.ecommerce.sleekselects.dto.UserDto;
import com.ecommerce.sleekselects.exception.AlreadyExistsException;
import com.ecommerce.sleekselects.exception.ResourceNotFoundException;
import com.ecommerce.sleekselects.model.Role;
import com.ecommerce.sleekselects.model.User;
import com.ecommerce.sleekselects.repository.RoleRepository;
import com.ecommerce.sleekselects.repository.UserRepository;
import com.ecommerce.sleekselects.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email :" + email));
    }

    @Override
    public User createUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException(request.getEmail() + " already exists!");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        Role userRole = roleRepository.findByName("ROLE_CUSTOMER").orElseGet(() -> {
            Role newRole = new Role("ROLE_CUSTOMER");
            return roleRepository.save(newRole);
        });

        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));

        return userRepository.save(user);
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
