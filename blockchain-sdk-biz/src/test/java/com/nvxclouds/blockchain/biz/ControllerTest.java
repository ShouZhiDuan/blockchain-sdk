package com.nvxclouds.blockchain.biz;

import com.alibaba.fastjson.JSON;
import com.nvxclouds.blockchain.api.query.DataNodeModificationQuery;
import com.nvxclouds.blockchain.api.vo.DataNodeModificationVO;
import com.nvxclouds.blockchain.biz.controller.DataProviderController;
import com.nvxclouds.common.base.pojo.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 2020/7/2 10:59  zhengxing.hu
 * @version 2020/7/2 10:59  1.0.0
 * @file ControllerTest
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ControllerTest {

    @Autowired
    private DataProviderController dataProviderController;

    @Test
    public void testQueryDataNodeModification() {
        DataNodeModificationQuery query  = new DataNodeModificationQuery();
        BaseResult<DataNodeModificationVO> resp = dataProviderController.queryDataNodeModification(query);
        log.info("response data : {}", JSON.toJSONString(resp));
    }
}
