package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.dto.UserRequest;
import peaksoft.dto.UserResponse;
import peaksoft.models.Role;
import peaksoft.models.User;
import peaksoft.repositories.UserRepository;
import peaksoft.service.UserService;

import java.security.Principal;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;
    @Override
    public UserResponse save(UserRequest userRequest) {
        User user = userRequest.build();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return UserResponse.build(user);
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest userRequest, Principal principal){
        User userForResponse = new User();
        String email = principal.getName();
        User authUser = userRepository.findByEmail(email).orElseThrow(()->
                new RuntimeException("User with Eemail " + email + " not found"));
        if(authUser.getRole().equals(Role.ADMIN)){
            userForResponse = update(userId,userRequest);
        }
        else if(authUser.getRole().equals(Role.USER)){
            if(authUser.getId().equals(userId)){
                userForResponse = update(userId,userRequest);
            }
        }
        else{
            throw new RuntimeException("sen bashka userdin maalymatyn ozgorto albaisyn");
        }
        return new UserResponse(userForResponse.getId(),
                userForResponse.getFullName(),
                userForResponse.getEmail(),
                userForResponse.getRole());
    }
    private User update(Long userId, UserRequest userRequest){
        User newUser = userRequest.build();
        User dbUser = userRepository.findById(userId).orElseThrow(()->
                new RuntimeException("User with Id " + userId + " not found"));
        dbUser.setFullName(newUser.getFullName());
        return newUser;
    }
}
