package peaksoft.instogram.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.instogram.dto.auth.request.SignInRequest;
import peaksoft.instogram.dto.auth.request.SignUpRequest;
import peaksoft.instogram.dto.auth.response.AuthResponse;
import peaksoft.instogram.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApi {
    private final AuthService authService;
    @PostMapping("/signUp")
    public AuthResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }

    @PostMapping("signIn")
    public AuthResponse signIn(@RequestBody SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }

}
