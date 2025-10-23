package com.zhuxi.fileModule.infrastructure.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.region.Region;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author zhuxi
 */

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CosClientConfig {

    private final QCloudConfig qCloudConfig;
    private COSClient cosClient;


    @Bean
    public COSClient getCosClient(){
        BasicCOSCredentials cred = new BasicCOSCredentials(qCloudConfig.getSecretId(), qCloudConfig.getSecretKey());
        Region region = new Region(qCloudConfig.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient client = new COSClient(cred, clientConfig);
        this.cosClient = client;
        return client;
    }


    @PreDestroy
    public void destroy(){
        if (cosClient != null){
            try {
                cosClient.shutdown();
                log.info("cosClient shutdown");
            } catch (Exception e) {
                log.error("cosClient shutdown error\n :{}", e.getMessage());
            }finally {
                cosClient = null;
            }
        }
    }
}
