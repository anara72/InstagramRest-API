package peaksoft.instogram.service;

import org.springframework.stereotype.Service;
import peaksoft.instogram.dto.auth.response.AuthResponse;
import peaksoft.instogram.dto.auth.request.SignInRequest;
import peaksoft.instogram.dto.auth.request.SignUpRequest;



@Service
public interface AuthService {

    AuthResponse signUp(SignUpRequest signUpRequest);

    AuthResponse signIn(SignInRequest signInRequest);

}
