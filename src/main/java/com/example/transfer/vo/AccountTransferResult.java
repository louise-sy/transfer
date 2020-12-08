package com.example.transfer.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class AccountTransferResult {

    private final Boolean status;
    // 交易點數
    private final Integer point;
    // 轉出
    private final AccountTransfer source;
    // 轉入
    private final AccountTransfer target;
}
