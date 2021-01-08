package com.nvxclouds.blockchain.api.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 11:41
 * @Description:
 */
@Getter
@Setter
public class DataNodeRegistrationVO extends DataProviderVO<DataNodeRegistrationVO.DataNodeRegistrationInfoVO> {

    @Getter
    @Setter
    public static class DataNodeRegistrationInfoVO extends CommonVO{
        private String dataNodeID;
        private String nodeName;
        private String txTime;
        private String messageHash;
        private String status;
        private String datasetID;
    }
}
