package peaksoft.service;

import peaksoft.dto.AuthRequest;
import peaksoft.dto.AuthResponseWithToken;

public interface AuthService {
    AuthResponseWithToken signIn(AuthRequest authRequest);
}
