package com.example.transfer.service;

import com.example.transfer.data.entity.Account;
import com.example.transfer.data.mapper.AccountMapper;

import com.example.transfer.vo.AccountTransfer;
import com.example.transfer.vo.AccountTransferResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;


@Service
public class AccountService {
    @Autowired
    private AccountMapper accountRepository;

    volatile ConcurrentHashMap<Integer,Boolean> lock = new ConcurrentHashMap();

    public List<Account> findAll() {
        return accountRepository.selectList(null);
    }



    /**
     * @param source 轉出
     * @param target 轉入
     * @param point  點數
     */
    public void transfer(
            Integer source
            , Integer target
            , Integer point
    ) {

        Account sourceAccount = accountRepository.selectById(source);
        Account targetAccount = accountRepository.selectById(target);
        if( accountRepository.inPoint(targetAccount.getId(), point) > 0){
            accountRepository.outPoint(sourceAccount.getId(), point);
        }
    }

    @Transactional
    public AccountTransferResult transfer2(
            Integer source
            , Integer target
            , Integer point
    ) {
        int sourceBeforePoint = 0, sourceAfterPoint = 0, targetBeforePoint = 0, targetAfterPoint = 0;

        Account sourceAccount = accountRepository.selectById(source);
        Account targetAccount = accountRepository.selectById(target);

//        Integer map =accountRepository.out2(source,point);
        HashMap<String,Object> a2 = accountRepository.aout2();

//        this.transfer(source,target,point);

        return new AccountTransferResult(
                point
                , new AccountTransfer(source, sourceBeforePoint, sourceAfterPoint)
                , new AccountTransfer(target, targetBeforePoint, targetAfterPoint)
        );
    }




}
