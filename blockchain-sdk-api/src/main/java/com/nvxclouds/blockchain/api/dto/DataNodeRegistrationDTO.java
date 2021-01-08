package com.nvxclouds.blockchain.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 11:45
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataNodeRegistrationDTO {
    private String dataNodeID;
    private String nodeName;
    private String txTime;
    private String messageHash;
}
