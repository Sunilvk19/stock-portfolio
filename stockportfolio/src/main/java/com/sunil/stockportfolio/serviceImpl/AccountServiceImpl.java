package com.sunil.stockportfolio.serviceImpl;

import com.sunil.stockportfolio.entity.Accounts;
import com.sunil.stockportfolio.entity.User;
import com.sunil.stockportfolio.exceptionhandle.AccountNotFoundException;
import com.sunil.stockportfolio.repository.AccountRepository;
import com.sunil.stockportfolio.repository.AuthUserRepository;
import com.sunil.stockportfolio.responsedto.AccountResponse;
import com.sunil.stockportfolio.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AuthUserRepository authUserRepository;

    private AccountResponse mapToAccountResponse(Accounts account) {
        return AccountResponse.builder()
                .id(account.getId())
                .user(account.getUser())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .active(account.isActive())
                .createdAt(account.getCreatedAt())
                .build();
    }
    @Override
    public AccountResponse createAccount(Integer userId) {
       User user = authUserRepository.findById(userId)
               .orElseThrow(()-> new UsernameNotFoundException("User not found"));
       if (user.getAccounts() != null){
           throw new AccountNotFoundException("Account already exists");
       }
       Accounts account = Accounts.builder()
               .user(user)
               .balance(BigDecimal.ZERO)
               .active(false)
               .build();
       Accounts saveAccount = accountRepository.save(account);
       return mapToAccountResponse(saveAccount);
    }

    @Override
    public AccountResponse findAccountByUserId(Integer userId) {
        Accounts accounts = accountRepository.findById(userId)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return mapToAccountResponse(accounts);
    }
}
