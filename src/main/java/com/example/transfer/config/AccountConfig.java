package com.example.transfer.config;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.transfer.data.entity.Account;
import com.example.transfer.data.mapper.AccountMapper;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public class AccountConfig extends ServiceImpl<AccountMapper,Account> {
    public static final int AccountCount = 10;
    public static final int DefaultPoint = 100000;

    @PostConstruct
    public void init() {
        List<Account> accounts = new ArrayList<>();

        for (int i = 1; i <= AccountCount; i++) {
            accounts.add(new Account(i, DefaultPoint));
        }
        this.saveBatch(accounts);
    }
}
