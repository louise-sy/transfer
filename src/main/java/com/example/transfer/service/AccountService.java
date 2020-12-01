package com.example.transfer.service;

import com.example.transfer.data.entity.Account;
import com.example.transfer.data.repository.AccountRepository;
import com.example.transfer.vo.AccountTransfer;
import com.example.transfer.vo.AccountTransferResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    private ConcurrentMap<Integer, Integer> wallets = new ConcurrentHashMap<>();

    public List<Account> findAll() {

        if(wallets!=null){
            for (Integer key : wallets.keySet()) {
                System.out.println(key + " : " + wallets.get(key));
                accountRepository.save(new Account(key, wallets.get(key)));
            }
        }

        return accountRepository.findAll();
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

        // init
        if(wallets.size()==0){
            List<Account> list=findAll();
            for(Account account:list){
                wallets.put(account.getId(),account.getPoint());
            }
        }

        Integer sourcePoint = wallets.get(source);
        Integer targetPoint = wallets.get(target);

        if(sourcePoint>0 && sourcePoint-point>=0){
            wallets.merge(target, point, Integer::sum);
            wallets.merge(source, 0-point, Integer::sum);
        }


    }

    public AccountTransferResult transfer2(
            Integer source
            , Integer target
            , Integer point
    ) {
        int sourceBeforePoint = 0, sourceAfterPoint = 0, targetBeforePoint = 0, targetAfterPoint = 0;
        // TODO 任何方式實作都可以，不限於使用 JPA

        return new AccountTransferResult(
                point
                , new AccountTransfer(source, sourceBeforePoint, sourceAfterPoint)
                , new AccountTransfer(target, targetBeforePoint, targetAfterPoint)
        );
    }

}
