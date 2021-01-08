package com.nvxclouds.blockchain.api.vo;

import lombok.*;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 13:32
 * @Description:
 */
@Getter
@Setter
public class DataSetIncreaseVO extends  DataProviderVO<DataSetIncreaseVO.DataSetIncreaseInfoVO>{

    @Getter
    @Setter
    public static class DataSetIncreaseInfoVO extends CommonVO{
        private String dataNodeID;
        private String nodeName;
        private String datasetID;
        private String status;
        private String txTime;
        private String messageHash;
    }
}
