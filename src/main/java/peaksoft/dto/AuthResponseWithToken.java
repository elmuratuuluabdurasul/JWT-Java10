package peaksoft.dto;

import peaksoft.models.Role;

public record AuthResponseWithToken(
        Long id,
        String email,

        Role role,
        String token
) {
}
