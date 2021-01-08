package com.nvxclouds.blockchain.biz.config;

import lombok.Builder;
import lombok.Data;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/22 10:27
 * @Description:
 */
@Data
@Builder
public class UserConfig {

    private String name;

    private String affiliation;

    private String mspId;

}
