package com.sunil.stockportfolio.serviceImpl;

import com.sunil.stockportfolio.entity.User;
import com.sunil.stockportfolio.enums.Role;
import com.sunil.stockportfolio.exceptionhandle.EmailAlreadyExistsException;
import com.sunil.stockportfolio.exceptionhandle.PasswordMissMatchException;
import com.sunil.stockportfolio.repository.AuthUserRepository;
import com.sunil.stockportfolio.requestdto.LoginRequest;
import com.sunil.stockportfolio.requestdto.RegisterRequest;
import com.sunil.stockportfolio.responsedto.LoginResponse;
import com.sunil.stockportfolio.responsedto.UserResponse;
import com.sunil.stockportfolio.security.JwtService;
import com.sunil.stockportfolio.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthUserService implements UserService {
    private final AuthUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private UserResponse mapToResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    private User mapToUser(RegisterRequest request, User user){
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        return user;
    }


    private static LoginResponse mapToLoginResponse(String token, User user) {
        return LoginResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
    @Override
    public UserResponse register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())){
            throw new PasswordMissMatchException("Passwords don't match");
        }
        if (repository.findByEmail(request.getEmail().trim().toLowerCase()).isPresent()){
            throw new EmailAlreadyExistsException("Email already exists");
        }
        User user = new User();
        user.setRole(Role.USER);
        mapToUser(request, user);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = repository.save(user);
        return mapToResponse(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("Invalid credentials"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new PasswordMissMatchException("Password don't match");
        }
        String token = jwtService.generateJwtToken(user.getEmail());
        return mapToLoginResponse(token, user);
    }

}
