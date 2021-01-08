package com.nvxclouds.blockchain.biz.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.ImmutableMap;
import com.nvxclouds.blockchain.api.dto.BlockChainBaseInfoDTO;
import com.nvxclouds.blockchain.api.query.BaseQuery;
import com.nvxclouds.blockchain.api.support.CommonPage;
import com.nvxclouds.blockchain.biz.config.OrganizationPeerConfig;
import com.nvxclouds.blockchain.biz.domain.BlockChainInfo;
import com.nvxclouds.blockchain.biz.domain.TradeInfo;
import com.nvxclouds.blockchain.biz.mapper.BlockChainInfoMapper;
import com.nvxclouds.blockchain.biz.mapper.InvokeInfoMapper;
import com.nvxclouds.blockchain.biz.mapper.TradeInfoMapper;
import com.nvxclouds.blockchain.biz.service.DataProviderService;
import com.nvxclouds.blockchain.biz.service.FabricService;
import com.nvxclouds.blockchain.biz.utils.DateUtil;
import com.nvxclouds.blockchain.biz.utils.FabricUtil;
import com.nvxclouds.blockchain.api.vo.OrganizationTradeCountVO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hyperledger.fabric.protos.peer.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/7/6 13:22
 * @Description:
 */
@Service
public class FabricServiceImpl implements FabricService {

    @Autowired
    private DataProviderService dataProviderService;

    @Autowired
    private InvokeInfoMapper invokeInfoMapper;

    @Autowired
    private BlockChainInfoMapper blockChainInfoMapper;

    @Autowired
    private TradeInfoMapper tradeInfoMapper;


    @Override//1、查询区块数量
    public Long queryBlockChainAmount() throws Exception {
        Long  height = dataProviderService.getBlockChainClientHandler().init().getChannel().queryBlockchainInfo().getHeight();
        //Long  height = ChannelClientUtil.getBlockChainClientInit().getChannel().queryBlockchainInfo().getHeight();
        return height;
    }


    @Override//2、查询历史交易数量
    public String queryTradeAmount() throws Exception {
        //原历史交易条数查询
        /*List<JSONObject> allList = FabricUtil.queryTransactionList(dataProviderService.getBlockChainClientHandler().init().getChannel());
        //List<JSONObject> allList = FabricUtil.queryTransactionList(ChannelClientUtil.getBlockChainClientInit().getChannel());
        if(!CollectionUtils.isEmpty(allList)){
            return String.valueOf(allList.size());
        }*/
        //------以上为历史交易条数优化改造为下面逻辑
        int tradeCount = invokeInfoMapper.selectCount(null);
        return String.valueOf(tradeCount);
    }

    @Override//3、查询链码数量
    public Integer queryChainCodeAmount() throws Exception {
        List<Query.ChaincodeInfo> chainCount = dataProviderService.getBlockChainClientHandler().init().getChannel().queryInstantiatedChaincodes(dataProviderService.getBlockChainClientHandler().getPeer());
        //List<Query.ChaincodeInfo> chainCount = ChannelClientUtil.getBlockChainClientInit().getChannel().queryInstantiatedChaincodes(ChannelClientUtil.getBlockChainClientInit().getPeer());
        return CollectionUtils.isEmpty(chainCount) ? 0 : chainCount.size();
    }

    @Override//4、查询节点数量
    public Integer queryPeerAmount() {
        return 10;//目前服务器配置10个节点
    }

    @Override//5、查询节点名称列表
    public List<String> qureyPeerNameList() {
          return Arrays.asList(
                OrganizationPeerConfig.APP_PROVIDER_PEER_0,
                OrganizationPeerConfig.APP_PROVIDER_PEER_1,
                OrganizationPeerConfig.APP_USER_PEER_0,
                OrganizationPeerConfig.APP_USER_PEER_1,
                OrganizationPeerConfig.DATA_PROVIDER_PEER_0,
                OrganizationPeerConfig.DATA_PROVIDER_PEER_1,
                OrganizationPeerConfig.DATA_USER_PEER_0,
                OrganizationPeerConfig.DATA_USER_PEER_1,
                OrganizationPeerConfig.REGULATOR_PEER_0,
                OrganizationPeerConfig.REGULATOR_PEER_1);
    }

    @Override//查询平台所有区块列表
    public CommonPage queryBlockList(BaseQuery query) throws Exception {
//        List<BlockChainBaseInfoDTO>  allList =  FabricUtil.querBlockBaseInfoList(dataProviderService.getBlockChainClientHandler().init().getChannel(),null);
//        CommonPage pageList = CommonPage.getPageByAllList(allList,query.getPage(),query.getPerPage());
        /**
         * 以上为原来老区块列表查询列表实现(废弃)、实际已下面逻辑为准
         */
        PageHelper.startPage(query.getPage(),query.getPerPage());
        Example example = new Example(BlockChainInfo.class);
        example.setOrderByClause("block_number DESC");
        List<BlockChainInfo>  allList = blockChainInfoMapper.selectByExample(example);
        return CommonPage.restPage(allList);
    }


    @Override//查询区块详情
    public BlockChainBaseInfoDTO queryBlockByBlockNumber(Long blockNumber) throws Exception {
        //List<BlockChainBaseInfoDTO>  allList =  FabricUtil.querBlockBaseInfoList(dataProviderService.getBlockChainClientHandler().init().getChannel(),blockNumber);
        //return CollectionUtils.isEmpty(allList) ? new BlockChainBaseInfoDTO() : allList.get(0);
        /**
         * 以上为原来区块详情查询(废弃)，以下面逻辑为准
         */
        BlockChainInfo example = new BlockChainInfo();
        example.setBlockNumber(blockNumber);
        BlockChainInfo details = blockChainInfoMapper.selectOne(example);
        BlockChainBaseInfoDTO dto = new BlockChainBaseInfoDTO();
        if(ObjectUtils.isNotEmpty(details)){
            BeanUtils.copyProperties(details,dto);
        }
        return dto;
    }

    @Override//分页查询某个块下的所有交易列表
    public CommonPage queryTransactionListByBlockNumber(BaseQuery query) throws Exception {
      /*  //查询全部交易列表
        List<JSONObject> allList = FabricUtil.queryTransactionList(dataProviderService.getBlockChainClientHandler().init().getChannel());
        //List<JSONObject> allList = FabricUtil.queryTransactionList(ChannelClientUtil.getBlockChainClientInit().getChannel());
        if(!CollectionUtils.isEmpty(allList)){
             List<JSONObject> pageList = allList.stream().filter(obj -> Long.valueOf(obj.get("blockHeight").toString()).equals(query.getBlockNumber())).collect(Collectors.toList());
             if(!CollectionUtils.isEmpty(pageList)){
                  CommonPage pageData = CommonPage.getPageByAllList(pageList,query.getPage(),query.getPerPage());
                  return pageData;
             }
        }*/
        /**
         * 以上为老逻辑(废弃)，以下面为准
         */
        PageHelper.startPage(query.getPage(),query.getPerPage());
        Long  blockNumber = query.getBlockNumber();
        TradeInfo example = new TradeInfo();
        example.setBlockHeight(blockNumber);
        List<TradeInfo> list = tradeInfoMapper.select(example);
        return  CommonPage.restPage(list);
    }




    @Override//分页查询区块全部交易
    public CommonPage queryTransactionList(BaseQuery query) throws Exception {
        /*List<JSONObject> allList = FabricUtil.queryTransactionList(dataProviderService.getBlockChainClientHandler().init().getChannel());
        CommonPage pageList = CommonPage.getPageByAllList(allList,query.getPage(),query.getPerPage());
        return pageList;*/
        /**
         * 以上为老交易列表查询逻辑(废弃)，以下面逻辑为准
         */
       PageHelper.startPage(query.getPage(),query.getPerPage());
       Example example = new Example(TradeInfo.class);
       example.setOrderByClause("TxTime DESC");
       List<TradeInfo> list = tradeInfoMapper.selectByExample(example);
       return CommonPage.restPage(list);
    }








    @Override//查询交易详情
    public Object queryTransactionDetails(String transactionId) throws Exception {
    /*    //获取所有交易列表
        List<JSONObject> allList = FabricUtil.queryTransactionList(dataProviderService.getBlockChainClientHandler().init().getChannel());
        //List<JSONObject> allList = FabricUtil.queryTransactionList(ChannelClientUtil.getBlockChainClientInit().getChannel());
        for (JSONObject jsonObject : allList) {
            if(jsonObject.get("TransactionID").equals(transactionId)){
                *//**
                 *     "Status": "String；状态值",
                 *     "OrganizationName": "String；组织名称",
                 *     "blockHeight": "String；区块高度",
                 *     "NodeName": "String；节点名称",
                 *     "TxTime": "String；审批时间；格式：2020-01-01 00:00:00",
                 *     "Operation": "String；操作名称",
                 *     "MessageHash": "String；hash值",
                 *     "DataNodeID": "String；节点ID",
                 *     "DatasetID": "String；数据集ID",
                 *     "TransactionID": "String；TransactionID"
                 *//*
                   List<Map<String,String>> list = new ArrayList<>();
                   if(jsonObject.containsKey("Status")){
                       list.add(ImmutableMap.of("name", "状态", "value", jsonObject.get("Status").toString()));
                   }
                   if(jsonObject.containsKey("OrganizationName")){
                       list.add(ImmutableMap.of("name", "组织", "value", jsonObject.get("OrganizationName").toString()));
                   }
                   if(jsonObject.containsKey("blockHeight")){
                       list.add(ImmutableMap.of("name", "区块高度", "value", jsonObject.get("blockHeight").toString()));
                   }
                   if(jsonObject.containsKey("NodeName")){
                       list.add(ImmutableMap.of("name", "节点名称", "value", jsonObject.get("NodeName").toString()));
                   }
                   if(jsonObject.containsKey("TxTime")){
                       list.add(ImmutableMap.of("name", "交易时间", "value", jsonObject.get("TxTime").toString()));
                   }
                   if(jsonObject.containsKey("Operation")){
                       list.add(ImmutableMap.of("name", "操作名称", "value", jsonObject.get("Operation").toString()));
                   }
                   if(jsonObject.containsKey("MessageHash")){
                       list.add(ImmutableMap.of("name", "hash值", "value", jsonObject.get("MessageHash").toString()));
                   }
                   if(jsonObject.containsKey("DataNodeID")){
                       list.add(ImmutableMap.of("name", "节点ID", "value", jsonObject.get("DataNodeID").toString()));
                   }
                   if(jsonObject.containsKey("DatasetID")){
                       list.add(ImmutableMap.of("name", "数据集ID", "value", jsonObject.get("DatasetID").toString()));
                   }
                   if(jsonObject.containsKey("TransactionID")){
                       list.add(ImmutableMap.of("name", "交易ID", "value", jsonObject.get("TransactionID").toString()));
                   }
                   return  list;
            }
        }
        return new ArrayList<>();*/


        /**
         * 以上为交易详情查询就逻辑(废弃)、以下面逻辑稳准
         */
        TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setTransactionID(transactionId);
        TradeInfo tradeInfoDetails = tradeInfoMapper.selectOne(tradeInfo);
        if(ObjectUtils.isEmpty(tradeInfoDetails)){
            return new ArrayList<>();
        }
        List<Map<String,Object>> list = new ArrayList<>();
        if(ObjectUtils.isNotEmpty(tradeInfoDetails)){
            list.add(ImmutableMap.of("name", "状态", "value", StringUtils.isBlank(tradeInfoDetails.getStatus()) ? "" : tradeInfoDetails.getStatus()));
            list.add(ImmutableMap.of("name", "组织", "value", StringUtils.isBlank(tradeInfoDetails.getOrganizationName()) ? "" : tradeInfoDetails.getOrganizationName()));
            list.add(ImmutableMap.of("name", "区块高度", "value",ObjectUtils.isEmpty(tradeInfoDetails.getBlockHeight()) ? "" : tradeInfoDetails.getBlockHeight()));
            list.add(ImmutableMap.of("name", "节点名称", "value", StringUtils.isBlank(tradeInfoDetails.getNodeName()) ? "" : tradeInfoDetails.getNodeName()));
            list.add(ImmutableMap.of("name", "交易时间", "value", StringUtil.isEmpty(tradeInfoDetails.getTxTime()) ? "" : tradeInfoDetails.getTxTime()));
            list.add(ImmutableMap.of("name", "操作名称", "value", StringUtils.isBlank(tradeInfoDetails.getOperation()) ? "" : tradeInfoDetails.getOperation()));
            list.add(ImmutableMap.of("name", "hash值", "value", StringUtils.isBlank(tradeInfoDetails.getMessageHash()) ? "" : tradeInfoDetails.getMessageHash()));
            list.add(ImmutableMap.of("name", "节点ID", "value", StringUtils.isBlank(tradeInfoDetails.getDataNodeID()) ?  "" : tradeInfoDetails.getDataNodeID()));
            list.add(ImmutableMap.of("name", "数据集ID", "value", StringUtils.isBlank(tradeInfoDetails.getDatasetID()) ? "" : tradeInfoDetails.getDatasetID()));
            list.add(ImmutableMap.of("name", "交易ID", "value", StringUtils.isBlank(tradeInfoDetails.getTransactionID()) ?  "" : tradeInfoDetails.getTransactionID()));
            return  list;
        }else {
            return list;
        }

    }

    @Override//查询不同组织下的交易数量
    public List<OrganizationTradeCountVO> queryTradeCountGroupByOrganization() throws Exception {
       /* 原组织饼图数据查询服务：现在优化弃用
        return FabricUtil.queryTradeCountGroupByOrganization(dataProviderService.getBlockChainClientHandler().init().getChannel());
        */
       List<OrganizationTradeCountVO> list = new ArrayList<>();
       list.add(invokeInfoMapper.querTradeCountByType(1));
       list.add(invokeInfoMapper.querTradeCountByType(2));
       list.add(invokeInfoMapper.querTradeCountByType(3));
       list.add(invokeInfoMapper.querTradeCountByType(4));
       list.add(invokeInfoMapper.querTradeCountByType(5));
       return  list;
    }


    /**
     *
     * @param time 当前时间戳
     * @return
     */
    @Override//按照前1小时统计每5分钟的交易数据
    public List<Long> querTradeCountGroupBy5Mins(Long time) {
        List<Long> list = new ArrayList<>();
        //查询当前前5分钟的数据
        for(int i = 1; i <= 12; i++){
            String timeStr = DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
            Long count = invokeInfoMapper.querTradeCountGroupBy5Mins(timeStr);
            list.add(null == count ? 0 : count);
            time = time - 5*60*1000;
        }
        Collections.reverse(list);
        return list;
    }

    /**
     *
     * @param time 当前日期时间
     * @return 返回前12小时的统计数量
     */
    @Override
    public List<Long> querTradeCountGroup1Hour(Long time) {
        List<Long> list = new ArrayList<>();
        //查询当前前5分钟的数据
        for(int i = 1; i <= 12; i++){
            String timeStr = DateUtil.format(new Date(time),"yyyy-MM-dd HH:mm:ss");
            Long count = invokeInfoMapper.querTradeCountGroup1Hour(timeStr);
            list.add(null == count ? 0 : count);
            time = time - 60*60*1000;
        }
        Collections.reverse(list);
        return list;
    }
}
