package peaksoft.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.JwtService;
import peaksoft.dto.AuthRequest;
import peaksoft.dto.AuthResponseWithToken;
import peaksoft.models.User;
import peaksoft.repositories.UserRepository;
import peaksoft.service.AuthService;
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    @Override
    public AuthResponseWithToken signIn(AuthRequest authRequest) {
        User user = userRepository.findByEmail(authRequest.email()).orElseThrow(() ->
                new RuntimeException(String.format("User not found with email: " + authRequest.email())));

        String password = authRequest.password();
        String dbEncodePassword = user.getPassword();
        if (!passwordEncoder.matches(password,dbEncodePassword)) {
            throw new RuntimeException("Invalid password222");
        }
        String token = jwtService.createToken(user);

        return new AuthResponseWithToken(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                token
        );
    }
}
