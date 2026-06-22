package com.sunil.stockportfolio.service;

import com.sunil.stockportfolio.requestdto.LoginRequest;
import com.sunil.stockportfolio.requestdto.RegisterRequest;
import com.sunil.stockportfolio.responsedto.LoginResponse;
import com.sunil.stockportfolio.responsedto.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
