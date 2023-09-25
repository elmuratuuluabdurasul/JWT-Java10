package peaksoft.dto;

import peaksoft.models.Role;
import peaksoft.models.User;

public record UserRequest(
        String fullname,
        String email,
        String password,
        Role role
) {
    public User build(){
        return  User.builder()
                .fullName(this.fullname)
                .email(this.email)
                .password(this.password)
                .role(this.role)
                .build();
    }
}
