package com.nvxclouds.blockchain.biz.mapper;

import com.nvxclouds.blockchain.biz.domain.InvokeInfo;
import com.nvxclouds.blockchain.biz.query.BlockchainTransactionApprovalQuery;
import com.nvxclouds.blockchain.api.vo.OrganizationTradeCountVO;
import com.nvxclouds.common.core.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvokeInfoMapper extends Mapper<InvokeInfo> {

    List<InvokeInfo> getBlockchainTransactionApprovalByPage(BlockchainTransactionApprovalQuery q);

    /**
     * 按照前1小时统计每5分钟的交易数据
     */
    Long querTradeCountGroupBy5Mins(@Param("timeStr") String timeStr);

    /**
     * 按照前12天统计每天交易折线图数据
     */
    Long querTradeCountGroup1Hour(@Param("timeStr") String timeStr);

    /**
     * 查询组织交易饼图数据
     * 1:数据提供方2:应用提供方3:数据使用方4:监管方5:APP用户
     */
    OrganizationTradeCountVO querTradeCountByType(@Param("flag") Integer flag);
}