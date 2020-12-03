package com.example.transfer;

import com.example.transfer.config.AccountConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransferApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransferApplication.class, args);
        AccountConfig accountConfig =new AccountConfig();
        accountConfig.init();
    }

}
