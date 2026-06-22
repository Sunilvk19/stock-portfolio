package com.sunil.stockportfolio.controller;

import com.sunil.stockportfolio.requestdto.LoginRequest;
import com.sunil.stockportfolio.requestdto.RegisterRequest;
import com.sunil.stockportfolio.responsedto.LoginResponse;
import com.sunil.stockportfolio.responsedto.UserResponse;
import com.sunil.stockportfolio.service.UserService;
import com.sunil.stockportfolio.utility.ResponseStructure;
import com.sunil.stockportfolio.utility.RestResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserService userService;
    private final RestResponseBuilder responseBuilder;

    @PostMapping("/register")
    public ResponseEntity<ResponseStructure<UserResponse>> register(@RequestBody RegisterRequest request){
        UserResponse response = userService.register(request);
        return responseBuilder.success(HttpStatus.CREATED,"User is register successfully",response);
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<LoginResponse>> login(@RequestBody LoginRequest request){
        LoginResponse loginResponse = userService.login(request);
        return responseBuilder.success(HttpStatus.OK,"User is login successfully", loginResponse);
    }

//    @GetMapping("/get")
//    public ResponseEntity<String> get(){
//        System.out.println("getrun");
//        return new ResponseEntity<>("Succes get",HttpStatus.OK);
//    }

}
