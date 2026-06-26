package com.sunil.stockportfolio.service;

import com.sunil.stockportfolio.responsedto.AccountResponse;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    AccountResponse createAccount(Integer userId);

    AccountResponse findAccountByUserId(Integer userId);
}
