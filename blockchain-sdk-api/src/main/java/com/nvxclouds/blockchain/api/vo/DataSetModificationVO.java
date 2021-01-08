package com.nvxclouds.blockchain.api.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 14:21
 * @Description:
 */
@Getter
@Setter
public class DataSetModificationVO extends DataProviderVO<DataSetModificationVO.DataSetModificationInfoVO>{
    @Getter
    @Setter
    public static class DataSetModificationInfoVO extends CommonVO{
        private String dataNodeID;
        private String nodeName;
        private String datasetID;
        private String status;
        private String txTime;
        private String messageHash;
    }
}
