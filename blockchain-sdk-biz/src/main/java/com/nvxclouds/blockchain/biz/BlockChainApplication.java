package com.nvxclouds.blockchain.biz;

import com.nvxclouds.common.core.annotation.NVXCloudApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/5/26 13:44
 * @Description:
 */


@NVXCloudApplication
@EnableFeignClients
@EnableScheduling
public class BlockChainApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlockChainApplication.class,args);
    }

}
