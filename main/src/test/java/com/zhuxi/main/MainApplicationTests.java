package com.zhuxi.main;

import com.zhuxi.user.module.domain.user.service.UserCacheService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
class MainApplicationTests {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    void contextLoads() {
        ConcurrentHashMap<Object, Object> d = new ConcurrentHashMap<>();

    }


}
