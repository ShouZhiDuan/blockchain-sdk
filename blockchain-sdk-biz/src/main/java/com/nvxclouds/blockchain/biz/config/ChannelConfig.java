package com.nvxclouds.blockchain.biz.config;

import lombok.Builder;
import lombok.Data;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/22 13:35
 * @Description:
 */
@Data
@Builder
public class ChannelConfig {
    private String channelName;
    private String peer;
    private String peerUrl;
    private String orderName;
    private String orderUrl;
    private String server;

}
