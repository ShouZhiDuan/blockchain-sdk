package com.nvxclouds.blockchain.biz.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/16 11:50
 * @Description:
 */
@Getter
@Setter
public class BlockChainConfig {

    private UserConfig  userConfig;


    @Getter
    @Setter
    public static class UserConfig {
        private String keyFile;
        private String certFile;
    }

}
