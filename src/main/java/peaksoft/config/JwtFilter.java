package peaksoft.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import peaksoft.models.User;
import peaksoft.repositories.UserRepository;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor

public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring("Bearer ".length());
            User user = null;
            try {
                user = jwtService.verifyToken(token);
            }catch (Exception e){
                response.sendError(401,"Invalid token");
            }
            User realUser = userRepository.findById(user.getId()).orElse(null);
            if(realUser == null){
                response.sendError(404,"User not found");
                return;
            }
            SecurityContextHolder.getContext()
                    .setAuthentication(
                            new UsernamePasswordAuthenticationToken(
                                    realUser.getEmail(),null,
                                    Collections.singletonList(realUser.getRole())
                            )
                    );
        }
        filterChain.doFilter(request,response);

    }
}

