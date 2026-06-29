package com.sunil.stockportfolio.controller;



import com.sunil.stockportfolio.requestdto.AddFoundsRequest;
import com.sunil.stockportfolio.responsedto.AccountResponse;
import com.sunil.stockportfolio.service.AccountService;
import com.sunil.stockportfolio.utility.ResponseStructure;
import com.sunil.stockportfolio.utility.RestResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final RestResponseBuilder responseBuilder;

    @PostMapping("/{userId}")
    public ResponseEntity<ResponseStructure<AccountResponse>> creatAccount(@PathVariable Integer userId){
        AccountResponse response = accountService.createAccount(userId);
        return responseBuilder.success(HttpStatus.CREATED,"Account created successfully", response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseStructure<AccountResponse>> findAccountByUserId(@PathVariable Integer userId){
        AccountResponse accountResponse = accountService.findAccountByUserId(userId);
        return responseBuilder.success(HttpStatus.FOUND,"Account found successfully", accountResponse);
    }

    @PostMapping("/{userId}/funds")
    public ResponseEntity<ResponseStructure<AccountResponse>> addFounds(@PathVariable Integer userId, @RequestBody AddFoundsRequest request){
        AccountResponse accountResponse = accountService.addFounds(userId, request);
        return responseBuilder.success(HttpStatus.ACCEPTED,"Successfully added found account", accountResponse);
    }
}
