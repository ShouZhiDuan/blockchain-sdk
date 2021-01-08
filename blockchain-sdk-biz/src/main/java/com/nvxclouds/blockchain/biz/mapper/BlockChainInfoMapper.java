package com.nvxclouds.blockchain.biz.mapper;

import com.nvxclouds.blockchain.biz.domain.BlockChainInfo;
import com.nvxclouds.common.core.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/8/24 13:56
 * @Description: 区块数据操作
 */
@Repository
public interface BlockChainInfoMapper extends Mapper<BlockChainInfo> {

    //查询最大的区块高度
    Long getMaxBlockNumber();

    //增量更新区块列表数据
    int insertBlockList(@Param("records") List<BlockChainInfo> records);


}
