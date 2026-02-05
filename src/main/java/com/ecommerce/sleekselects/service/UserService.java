package com.ecommerce.sleekselects.service;

import com.ecommerce.sleekselects.dto.UserDto;
import com.ecommerce.sleekselects.model.User;
import com.ecommerce.sleekselects.request.RegisterRequest;

public interface UserService {
    User getUserById(Long userId);

    User getUserByEmail(String email);

    User createUser(RegisterRequest request);

    UserDto convertUserToDto(User user);
}
