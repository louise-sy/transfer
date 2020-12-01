package com.example.transfer.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class AccountTransferResult {
    // 交易點數
    private Integer point;
    // 轉出
    private AccountTransfer source;
    // 轉入
    private AccountTransfer target;
}
