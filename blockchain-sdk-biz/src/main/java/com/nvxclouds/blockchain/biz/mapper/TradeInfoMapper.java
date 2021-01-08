package com.nvxclouds.blockchain.biz.mapper;

import com.nvxclouds.blockchain.biz.domain.TradeInfo;
import com.nvxclouds.common.core.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/8/25 11:30
 * @Description: 交易详情数据操作
 */
@Repository
public interface TradeInfoMapper extends Mapper<TradeInfo> {
    //增量更新交易列表数据
    int insertTradeList(@Param("records") List<TradeInfo> records);
}
