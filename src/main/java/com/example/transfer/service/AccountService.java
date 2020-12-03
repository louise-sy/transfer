package com.example.transfer.service;

import com.example.transfer.data.entity.Account;
import com.example.transfer.data.mapper.AccountMapper;
import com.example.transfer.data.mapper.TradeResult;
import com.example.transfer.vo.AccountTransfer;
import com.example.transfer.vo.AccountTransferResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class AccountService {
    @Autowired
    private AccountMapper accountRepository;

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


    public AccountTransferResult transfer2(
            Integer source
            , Integer target
            , Integer point
    ) {
        int sourceBeforePoint = 0, sourceAfterPoint = 0, targetBeforePoint = 0, targetAfterPoint = 0;

        TradeResult out = null;
        TradeResult in = null;
        try {
            Integer aout2= accountRepository.out2(source,point);

            out = new TradeResult();
            in = new TradeResult();
            if(aout2>0){
                out = accountRepository.out();
                Integer in2 = accountRepository.in2(target,point);
                if(in2>0){
                    in = accountRepository.out();
                }else{
                    throw new Exception();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return new AccountTransferResult(
                point
                , new AccountTransfer(source, out.getPoint(), out.getAfterPoint())
                , new AccountTransfer(target, in.getPoint(),in.getAfterPoint())
        );
    }




}
