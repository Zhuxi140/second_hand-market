package com.zhuxi.main;

import com.zhuxi.user.module.domain.user.service.UserCacheService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MainApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(MainApplicationTests.class);
    private final UserCacheService cache;

    @Autowired
    public MainApplicationTests(UserCacheService cache) {
        this.cache = cache;
    }

    @Test
    void contextLoads() {
    }


}
