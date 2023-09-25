package peaksoft.service;

import org.springframework.http.ResponseEntity;
import peaksoft.dto.UserRequest;
import peaksoft.dto.UserResponse;

import java.security.Principal;

public interface UserService {
    UserResponse save(UserRequest userRequest);

    UserResponse updateUser(Long userId, UserRequest userRequest, Principal principal);
}
