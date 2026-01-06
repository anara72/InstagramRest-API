package peaksoft.instogram.service.Impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import peaksoft.instogram.config.jwt.JwtService;
import peaksoft.instogram.dto.auth.request.SignInRequest;
import peaksoft.instogram.dto.auth.request.SignUpRequest;
import peaksoft.instogram.dto.auth.response.AuthResponse;
import peaksoft.instogram.entity.User;
import peaksoft.instogram.enums.Role;
import peaksoft.instogram.repository.UserRepository;
import peaksoft.instogram.service.AuthService;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.findUserByEmail(signUpRequest.email()).isPresent()) {
            throw new BadCredentialsException(String.format("User with email %s already exists", signUpRequest.email()));
        }
        User user = User.builder().userName(signUpRequest.userName()).password(passwordEncoder.encode(signUpRequest.password())).email(signUpRequest.email()).phoneNumber(signUpRequest.phoneNumber()).role(signUpRequest.role()).followerCount(0).followingCount(0).build();
        User savedUser = userRepository.save(user);
        String tokenUser = jwtService.generateToken(savedUser);
        return AuthResponse.builder().id(savedUser.getId()).token(tokenUser).role(savedUser.getRole()).build();
    }

    public AuthResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.findUserByEmail(signInRequest.email()).orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            throw new BadCredentialsException(String.format("Passwords do not match"));
        }
        String token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .id(user.getId())
                .token(token)
                .role(user.getRole())
                .build();
    }

    //init method
    @PostConstruct
    private void saveAdmin(){
        peaksoft.instogram.entity.User user = new peaksoft.instogram.entity.User();
        user.setUserName("admin");
        user.setPassword(passwordEncoder.encode("admin1111"));
        user.setEmail("admin@gmail.com");
        user.setPhoneNumber("+996900000000");
        user.setRole(Role.ADMIN);
        if(!userRepository.existsUserByEmail(user.getEmail())){
            userRepository.save(user);
        }
    }
}


