package peaksoft.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import peaksoft.models.Role;
import peaksoft.models.User;

import java.time.Instant;

@Service

public class JwtService {
    @Value("${app.jwt.secret}")
    private String jwtSecret;
    // create jwt
    public String createToken(User user){
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
        String token = JWT.create()
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(8460))
                .withClaim("id", user.getId())
                .withClaim("fullName", user.getFullName())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().getAuthority())
                .sign(algorithm);
        return token;
    }
    // verify jwt
    public User verifyToken(String token){
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWTToUser(decodedJWT);
    }

    private User decodedJWTToUser(DecodedJWT decodedJWT) {
        User user = new User();
        user.setId(decodedJWT.getClaim("id").asLong());
        user.setFullName(decodedJWT.getClaim("fullName").asString());
        user.setEmail(decodedJWT.getClaim("email").asString());
        user.setRole(Role.valueOf(decodedJWT.getClaim("role").asString()));
        return user;
    }
}
