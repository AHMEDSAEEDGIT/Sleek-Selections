package com.ecommerce.sleekselects.controller;

import com.ecommerce.sleekselects.dto.UserDto;
import com.ecommerce.sleekselects.model.User;
import com.ecommerce.sleekselects.response.ApiResponse;
import com.ecommerce.sleekselects.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success", userDto));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
