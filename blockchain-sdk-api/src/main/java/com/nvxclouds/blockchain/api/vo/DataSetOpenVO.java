package com.nvxclouds.blockchain.api.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/6/19 14:01
 * @Description:
 */
@Getter
@Setter
public class DataSetOpenVO extends DataProviderVO<DataSetOpenVO.DataSetOpenInfoVO> {
    @Getter
    @Setter
    public static class DataSetOpenInfoVO extends CommonVO{
        private String dataNodeID;
        private String nodeName;
        private String datasetID;
        private String status;
        private String txTime;
        private String messageHash;
    }
}
