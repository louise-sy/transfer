package com.example.transfer.service;

import com.example.transfer.data.entity.Account;
import com.example.transfer.data.mapper.AccountMapper;
import com.example.transfer.data.mapper.TradeResult;
import com.example.transfer.vo.AccountTransfer;
import com.example.transfer.vo.AccountTransferResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AccountService {
    @Autowired
    private AccountMapper accountRepository;

    public List<Account> findAll() {
        return accountRepository.selectList(null);
    }

    private final Integer count = 0;

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
        if (accountRepository.inPoint(targetAccount.getId(), point) > 0) {
            accountRepository.outPoint(sourceAccount.getId(), point);
        }
    }

    public AccountTransferResult transfer2(Integer source
            , Integer target
            , Integer point) {
        try {
            Result result = transferA(source, point);
            return transferB(result, target, point, source);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return new AccountTransferResult(false, 0, new AccountTransfer(0, 0, 0), new AccountTransfer(0, 0, 0));
    }


    @Transactional
    public Result transferA(Integer source, Integer point) {

        Integer aout2 = accountRepository.out2(source, point);

        TradeResult out = accountRepository.out();

        return new Result(aout2, out);
    }

    @Transactional
    public AccountTransferResult transferB(Result result, Integer target, Integer point, Integer source) {

        TradeResult in = null;
        TradeResult out = result.getTradeResult();
        if (result.getUpdate() > 0) {

            Integer in2 = accountRepository.in2(target, point);
            if (in2 > 0) {
                in = accountRepository.out();
            }

        } else {

            Integer targetPoint = accountRepository.findPoint(target);
            return new AccountTransferResult(false,
                    0
                    , new AccountTransfer(source, out.getPoint(), out.getAfterPoint())
                    , new AccountTransfer(target, targetPoint, targetPoint)
            );
        }

        return new AccountTransferResult(true,
                point
                , new AccountTransfer(source, out.getPoint(), out.getAfterPoint())
                , new AccountTransfer(target, in.getPoint(), in.getAfterPoint())
        );


    }


//    public AccountTransferResult transfer2(
//            Integer source
//            , Integer target
//            , Integer point
//    ) {
//        int sourceBeforePoint = 0, sourceAfterPoint = 0, targetBeforePoint = 0, targetAfterPoint = 0;
//
//        TradeResult out = null;
//        TradeResult in = null;
//        Integer targetPoint = 0;
//        Integer aout2 = accountRepository.out2(source, point);
//
//        out = new TradeResult();
//        in = new TradeResult();
//        if (aout2 > 0) {
//            out = accountRepository.out();
//            Integer in2 = accountRepository.in2(target, point);
//            if (in2 > 0) {
//                in = accountRepository.out();
//            }
//
//        } else {
//            out = accountRepository.out();
//            targetPoint = accountRepository.findPoint(target);
//            count++;
//            System.out.println(count);
//            return new AccountTransferResult(false,
//                    0
//                    , new AccountTransfer(0, 0, 0)
//                    , new AccountTransfer(0, 0, 0)
//            );
//
//        }
//
//
//        return new AccountTransferResult(true,
//                point
//                , new AccountTransfer(source, out.getPoint(), out.getAfterPoint())
//                , new AccountTransfer(target, in.getPoint(), in.getAfterPoint())
//        );
//    }


    @Data
    @AllArgsConstructor
    public class Result {
        private Integer update;
        private TradeResult tradeResult;
    }

}
