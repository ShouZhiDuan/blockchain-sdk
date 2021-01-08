package com.nvxclouds.blockchain.api.vo;

import lombok.*;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 13:18
 * @Description:
 */
@Getter
@Setter
public class DataNodeModificationVO extends DataProviderVO<DataNodeModificationVO.DataNodeModificationInfoVO>{

    @Getter
    @Setter
    public static class DataNodeModificationInfoVO extends CommonVO{
        private String dataNodeID;
        private String nodeName;
        private String txTime;
        private String messageHash;
        private String status;
        private String datasetID;
    }
}
