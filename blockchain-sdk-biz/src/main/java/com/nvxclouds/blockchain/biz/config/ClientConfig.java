package com.nvxclouds.blockchain.biz.config;

import lombok.Builder;
import lombok.Data;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/22 10:33
 * @Description:
 */
@Data
@Builder
public class ClientConfig {
    private String url;
    private String tslFile;
    private String host;
    private String eventHubPort;
}
