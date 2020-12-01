package com.example.transfer.config;

import com.example.transfer.data.entity.Account;
import com.example.transfer.data.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class AccountConfig {
    public static final int AccountCount = 10;
    public static final int DefaultPoint = 100000;

    @Autowired
    private AccountRepository accountRepository;

    @PostConstruct
    private void init() {
        List<Account> accounts = new ArrayList<>();
        for (int i = 1; i <= AccountCount; i++) {
            accounts.add(new Account(i, DefaultPoint));
        }
        accountRepository.saveAll(accounts);
    }
}
