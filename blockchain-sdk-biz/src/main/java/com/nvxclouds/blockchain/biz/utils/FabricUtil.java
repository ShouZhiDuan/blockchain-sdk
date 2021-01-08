package com.nvxclouds.blockchain.biz.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.google.protobuf.InvalidProtocolBufferException;
import com.nvxclouds.blockchain.api.dto.BlockChainBaseInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.hyperledger.fabric.protos.ledger.rwset.kvrwset.KvRwset;
import org.hyperledger.fabric.sdk.BlockInfo;
import org.hyperledger.fabric.sdk.BlockchainInfo;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.TxReadWriteSetInfo;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.util.CollectionUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/7/8 10:39
 * @Description: 操作Fabric相关工具类
 */
@Slf4j
public class FabricUtil {

    /**
     * 统计每个组织下的交易数量
     */
    public static List<Map<String,String>> queryTradeCountGroupByOrganization(Channel channel) throws InvalidArgumentException, ProposalException, ParseException, IOException {
        //查询所有的交易数据
        List<JSONObject> allTradeList = queryTransactionList(channel);
        if(CollectionUtils.isEmpty(allTradeList)){
            //组装对象
            Map<String, String> dataProviderCountMap = ImmutableMap.of("organizationName", "数据提供方", "tradeCount", String.valueOf(0));
            Map<String, String> appProviderCountMap = ImmutableMap.of("organizationName", "应用提供方", "tradeCount", String.valueOf(0));
            Map<String, String> dataUsedCountMap = ImmutableMap.of("organizationName", "数据使用方", "tradeCount", String.valueOf(0));
            Map<String, String> supervisionCountMap = ImmutableMap.of("organizationName", "监管方", "tradeCount", String.valueOf(0));
            Map<String, String> appCountMap = ImmutableMap.of("organizationName", "APP用户", "tradeCount", String.valueOf(0));
            //返回结果集
            return Arrays.asList(dataProviderCountMap,appProviderCountMap,dataUsedCountMap,supervisionCountMap,appCountMap);
        }else {
            //如果存在交易数据则进行统计
            Integer dataProviderCount = allTradeList.stream().filter(trade -> (trade.get("OrganizationName").toString().equals("数据提供方"))).collect(Collectors.toList()).size();
            Integer appProviderCount = allTradeList.stream().filter(trade -> (trade.get("OrganizationName").toString().equals("应用提供方"))).collect(Collectors.toList()).size();
            Integer dataUsedCount = allTradeList.stream().filter(trade -> (trade.get("OrganizationName").toString().equals("数据使用方"))).collect(Collectors.toList()).size();
            Integer supervisionCount = allTradeList.stream().filter(trade -> (trade.get("OrganizationName").toString().equals("监管方"))).collect(Collectors.toList()).size();
            Integer appCount = allTradeList.stream().filter(trade -> (trade.get("OrganizationName").toString().equals("APP用户"))).collect(Collectors.toList()).size();
            //组装对象
            Map<String, String> dataProviderCountMap = ImmutableMap.of("organizationName", "数据提供方", "tradeCount", String.valueOf(dataProviderCount));
            Map<String, String> appProviderCountMap = ImmutableMap.of("organizationName", "应用提供方", "tradeCount", String.valueOf(appProviderCount));
            Map<String, String> dataUsedCountMap = ImmutableMap.of("organizationName", "数据使用方", "tradeCount", String.valueOf(dataUsedCount));
            Map<String, String> supervisionCountMap = ImmutableMap.of("organizationName", "监管方", "tradeCount", String.valueOf(supervisionCount));
            Map<String, String> appCountMap = ImmutableMap.of("organizationName", "APP用户", "tradeCount", String.valueOf(appCount));
            //返回结果集
//            if(!channel.isShutdown()){
//                channel.shutdown(true);
//            }
            return Arrays.asList(dataProviderCountMap,appProviderCountMap,dataUsedCountMap,supervisionCountMap,appCountMap);
        }
    }


    /**
     * 查询区块基本信息列表
     * 区块高度、当前区块下有多少条交易、父Hash、当前Hash
     */
    public static List<BlockChainBaseInfoDTO>  querBlockBaseInfoList(Channel channel,Long blockNumber) throws ProposalException, InvalidArgumentException, InvalidProtocolBufferException {
        //查询区块高度
        BlockchainInfo blockchainInfo = channel.queryBlockchainInfo();
        List<BlockChainBaseInfoDTO> listDTO = new ArrayList<BlockChainBaseInfoDTO>();
        if(blockchainInfo.getHeight() > 0){
            if(null == blockNumber){
                for (long i = (blockchainInfo.getHeight() - 1); i >= 0; i--){
                    //获取当前索引位置区块信息
                    BlockInfo blockInfo = channel.queryBlockByNumber(i);
                    listDTO.add(convertorBlockChainBaseInfoDTO(channel,null,blockInfo));
                }
            }else {
                //查询具体某一个块的详情数据
                BlockInfo blockInfo = channel.queryBlockByNumber(blockNumber);
                listDTO.add(convertorBlockChainBaseInfoDTO(channel,blockNumber,blockInfo));
            }
//            if(!channel.isShutdown()){
//                channel.shutdown(true);
//            }
            return listDTO;
        }
//        if(!channel.isShutdown()){
//            channel.shutdown(true);
//        }
        return listDTO;
    }

     public static BlockChainBaseInfoDTO convertorBlockChainBaseInfoDTO(Channel channel, Long blockNumber, BlockInfo blockInfo) throws InvalidProtocolBufferException {
             BlockChainBaseInfoDTO blockChainBaseInfoDTO = new BlockChainBaseInfoDTO();
             blockChainBaseInfoDTO.setBlockNumber(blockInfo.getBlockNumber());//设置区块高度
             blockChainBaseInfoDTO.setTransactionCount(blockInfo.getEnvelopeCount());//获取当块上交易条数
             blockChainBaseInfoDTO.setChannelId(blockInfo.getChannelId());//设置当前通道名称
             blockChainBaseInfoDTO.setDataHash(Hex.encodeHexString(blockInfo.getDataHash()));//当前块hash
             blockChainBaseInfoDTO.setPreviousHashID(Hex.encodeHexString(blockInfo.getPreviousHash()));//当前父hash
//             if(!channel.isShutdown()){
//                 channel.shutdown(true);
//             }
             return blockChainBaseInfoDTO;
     }

    /**
     * 解析所有交易列表
     */
    public static List<JSONObject> queryTransactionList(Channel channel) throws InvalidArgumentException, ProposalException, IOException, ParseException {
        //envelopes[]->transactionEnvelopeInfo(对象)->transactionActionInfoArray[]->rwsetInfo(对象)->nsRwsetInfoArray[]->writeSet
        //获取所有区块信息列表
        BlockchainInfo blockchainInfo = channel.queryBlockchainInfo();
        List<JSONObject> tradeDetailsList = new ArrayList<JSONObject>();
        if(blockchainInfo.getHeight() > 0){
            for (long i = (blockchainInfo.getHeight() - 1); i >= 0; i--){
                //获取当前索引位置区块信息
                BlockInfo blockInfo = channel.queryBlockByNumber(i);
                JSONObject blockInfoContainTransactions = execBlockInfo(blockInfo);
                String envelopesStr = blockInfoContainTransactions.get("envelopes").toString();
                if(StringUtils.isNoneBlank(envelopesStr)){
                      JSONArray transactionEnvelopeInfoArray = JSONArray.parseArray(envelopesStr);
                      if(null != transactionEnvelopeInfoArray){
                          //1、envelopes[]层
                          for (int m = 0; m < transactionEnvelopeInfoArray.size(); m++){
                              JSONObject transactionEnvelopeInfoJsonObject = transactionEnvelopeInfoArray.getJSONObject(m);
                              if(null == transactionEnvelopeInfoJsonObject){
                                  continue;
                              }
                              //2、transactionEnvelopeInfo->Object
                              JSONObject transactionEnvelopeInfoObject = (JSONObject) transactionEnvelopeInfoJsonObject.get("transactionEnvelopeInfo");
                              if(null == transactionEnvelopeInfoObject){
                                  continue;
                              }
                              //3、transactionActionInfoArray[]
                              JSONArray transactionActionInfoArray = null;
                               if(null == transactionEnvelopeInfoObject.get("transactionActionInfoArray")){
                                   continue;
                               }else {
                                   transactionActionInfoArray = JSONArray.parseArray(transactionEnvelopeInfoObject.get("transactionActionInfoArray").toString());
                               }
                              if(null != transactionActionInfoArray){
                                  for (int n = 0; n < transactionActionInfoArray.size(); n++){
                                      JSONObject transactionActionInfoObject = transactionActionInfoArray.getJSONObject(n);
                                      //4、rwsetInfo->Object
                                      JSONObject rwsetInfoObject = (JSONObject) transactionActionInfoObject.get("rwsetInfo");
                                      //5、nsRwsetInfoArray[]
                                      JSONArray  nsRwsetInfoArray = JSONArray.parseArray(rwsetInfoObject.get("nsRwsetInfoArray").toString());
                                      if(null != nsRwsetInfoArray){
                                          for (int z = 0; z < nsRwsetInfoArray.size(); z++){
                                              JSONObject nsRwsetInfoObject = nsRwsetInfoArray.getJSONObject(z);
                                              //6、writeSet[]
                                              JSONArray writeSetArray = JSONArray.parseArray(nsRwsetInfoObject.get("writeSet").toString());
                                              if(null != writeSetArray){
                                                  for (int y = 0; y < writeSetArray.size(); y++){
                                                      JSONObject writeSetObject = writeSetArray.getJSONObject(y);
                                                      String key =  writeSetObject.get("key").toString();
                                                      //System.out.println("key=" + key);
                                                      if(StringUtils.isNoneBlank(key) && key.contains("{\"") && key.contains("\"}")){
                                                          int frontIndex = key.indexOf("{\"");
                                                          int endIndex = key.lastIndexOf("\"}") + 2;
                                                          String tradeDetailsStr = key.substring(frontIndex,endIndex+1);
                                                          //System.out.println(tradeDetailsStr);
                                                          JSONObject tradeDetailsObject =JSONObject.parseObject(tradeDetailsStr);
                                                          //设置当前交易的区块高度
                                                          tradeDetailsObject.put("blockHeight",blockInfo.getBlockNumber());

                                                          //时间格式转换
                                                          if(null != tradeDetailsObject.get("TxTime")){
                                                              String dataStr = tradeDetailsObject.get("TxTime").toString();
                                                              if(StringUtils.isNotBlank(dataStr)) {
                                                                      dataStr = dataStr.replaceAll("\\\"","");
                                                                      //yyyyMMddHHmmss 20200521115623  202007151758
                                                                      if (dataStr.length() == 14) {
                                                                          log.info("yyyyMMddHHmmss = " + dataStr + " and length = " + dataStr.length());
                                                                          tradeDetailsObject.put("TxTime", DateUtil.format(DateUtil.parse(dataStr,"yyyyMMddHHmmss"),"yyyy-MM-dd HH:mm:ss"));
                                                                      }
                                                              }
                                                          }

                                                          //操作名称转换
                                                          String operationName = tradeDetailsObject.get("Operation").toString();
                                                          if(StringUtils.isNotBlank(operationName)){
                                                              /**
                                                               * 数据提供方
                                                               */
                                                              if(operationName.equals("dataNodeRegistration")){
                                                                  //数据节点注册操作
                                                                  tradeDetailsObject.put("Operation","数据节点注册操作");
                                                                  tradeDetailsObject.put("OrganizationName","数据提供方");
                                                              }
                                                              if(operationName.equals("dataNodeModificationApply")){
                                                                  //数据节点修改申请
                                                                  tradeDetailsObject.put("Operation","数据节点修改申请操作");
                                                                  tradeDetailsObject.put("OrganizationName","数据提供方");
                                                              }
                                                              if(operationName.equals("addDatasetApply")){
                                                                  //增加数据集申请
                                                                  tradeDetailsObject.put("Operation","增加数据集申请操作");
                                                                  tradeDetailsObject.put("OrganizationName","数据提供方");
                                                              }
                                                              if(operationName.equals("openDatasets")){
                                                                  //开放数据集
                                                                  tradeDetailsObject.put("Operation","开放数据集操作");
                                                                  tradeDetailsObject.put("OrganizationName","数据提供方");
                                                              }
                                                              if(operationName.equals("modifyDatasets")){
                                                                  //修改数据集
                                                                  tradeDetailsObject.put("Operation","修改数据集操作");
                                                                  tradeDetailsObject.put("OrganizationName","数据提供方");
                                                              }
                                                              if(operationName.equals("datasetsOffline")){
                                                                  //数据集下线
                                                                  tradeDetailsObject.put("Operation","数据集下线操作");
                                                                  tradeDetailsObject.put("OrganizationName","数据提供方");
                                                              }
                                                              if(operationName.equals("queryNodeDataResearchResults")){
                                                                  //查看研究结果写入
                                                                  tradeDetailsObject.put("Operation","查看研究结果写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","数据提供方");
                                                              }
                                                              /**
                                                               * 应用提供方
                                                               */
                                                              if(operationName.equals("addAppApply")){
                                                                  //增加应用申请写入
                                                                  tradeDetailsObject.put("Operation","增加应用申请写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","应用提供方");
                                                              }
                                                              if(operationName.equals("appOnline")){
                                                                  //应用上架写入
                                                                  tradeDetailsObject.put("Operation","应用上架写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","应用提供方");
                                                              }
                                                              if(operationName.equals("modifyAppApply")){
                                                                  //修改应用申请写入
                                                                  tradeDetailsObject.put("Operation","修改应用申请写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","应用提供方");
                                                              }
                                                              if(operationName.equals("queryAppUsage")){
                                                                  //查看应用使用情况写入
                                                                  tradeDetailsObject.put("Operation","查看应用使用情况写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","应用提供方");
                                                              }
                                                              if(operationName.equals("appOffline")){
                                                                  //应用下架写入
                                                                  tradeDetailsObject.put("Operation","应用下架写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","应用提供方");
                                                              }
                                                              /**
                                                               * 数据使用方
                                                               */
                                                              if(operationName.equals("createResearchProjects")){
                                                                  //创建研究项目写入
                                                                  tradeDetailsObject.put("Operation","创建研究项目写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","数据使用方");
                                                              }
                                                              if(operationName.equals("useDatasetApply")){
                                                                  //使用数据集申请写入
                                                                  tradeDetailsObject.put("Operation","使用数据集申请写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","数据使用方");
                                                              }
                                                              if(operationName.equals("queryUserResearchResults")){
                                                                  //查看研究结果写入
                                                                  tradeDetailsObject.put("Operation","查看研究结果写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","数据使用方");
                                                              }
                                                              if(operationName.equals("purchaseAuthorization")){
                                                                  //购买授权写入
                                                                  tradeDetailsObject.put("Operation","购买授权写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","数据使用方");
                                                              }
                                                              if(operationName.equals("activateAuthorization")){
                                                                  //激活授权写入
                                                                  tradeDetailsObject.put("Operation","激活授权写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","数据使用方");
                                                              }
                                                              /**
                                                               * 监管方
                                                               */
                                                              if(operationName.equals("newDatasetApproval")){
                                                                  //新数据集审批写入
                                                                  tradeDetailsObject.put("Operation","新数据集审批写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","监管方");
                                                              }
                                                              if(operationName.equals("modifyDatasetApproval")){
                                                                  //数据集修改审批写入
                                                                  tradeDetailsObject.put("Operation","数据集修改审批写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","监管方");
                                                              }
                                                              if(operationName.equals("researchProjectApproval")){
                                                                  //研究项目审批写入
                                                                  tradeDetailsObject.put("Operation","研究项目审批写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","监管方");
                                                              }
                                                              if(operationName.equals("useDatasetApproval")){
                                                                  //使用数据集审批写入
                                                                  tradeDetailsObject.put("Operation","使用数据集审批写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","监管方");
                                                              }
                                                              if(operationName.equals("newAppApproval")){
                                                                  //新应用审批写入
                                                                  tradeDetailsObject.put("Operation","新应用审批写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","监管方");
                                                              }
                                                              if(operationName.equals("newAppApproval")){
                                                                  //新应用审批写入
                                                                  tradeDetailsObject.put("Operation","新应用审批写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","监管方");
                                                              }
                                                              if(operationName.equals("modifyAppApproval")){
                                                                  //应用修改审批写入
                                                                  tradeDetailsObject.put("Operation","应用修改审批写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","监管方");
                                                              }
                                                              if(operationName.equals("queryProjectResearchResults")){
                                                                  //查看研究结果写入
                                                                  tradeDetailsObject.put("Operation","查看研究结果写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","监管方");
                                                              }
                                                              /**
                                                               * APP用户
                                                               */
                                                              if(operationName.equals("authorizationDataUpBlockChain")){
                                                                  //数据授权及上链写入
                                                                  tradeDetailsObject.put("Operation","数据授权及上链写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","APP用户");
                                                              }
                                                              if(operationName.equals("authorizationAndParticipationInResearch")){
                                                                  //授权及参与研究写入
                                                                  tradeDetailsObject.put("Operation","授权及参与研究写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","APP用户");
                                                              }
                                                              if(operationName.equals("authorizationAndParticipationInResearch")){
                                                                  //授权及参与研究写入
                                                                  tradeDetailsObject.put("Operation","授权及参与研究写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","APP用户");
                                                              }
                                                              if(operationName.equals("queryDataUsage")){
                                                                  //查看数据使用情况写入
                                                                  tradeDetailsObject.put("Operation","查看数据使用情况写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","APP用户");
                                                              }
                                                              if(operationName.equals("queryAppUserDataResearchResults")){
                                                                  //查看研究结果写入
                                                                  tradeDetailsObject.put("Operation","查看研究结果写入操作");
                                                                  tradeDetailsObject.put("OrganizationName","APP用户");
                                                              }
                                                          }
                                                          tradeDetailsList.add(tradeDetailsObject);
                                                      }
                                                  }
                                              }
                                          }
                                      }

                                  }
                              }
                          }
                      }
                }

            }
        }
//        if(!channel.isShutdown()){
//            channel.shutdown(true);
//        }
        return tradeDetailsList;
    }





    /**
     * 解析区块信息对象
     * @param blockInfo 区块信息对象
     */
    public static JSONObject execBlockInfo(BlockInfo blockInfo) throws IOException, InvalidArgumentException {
        final long blockNumber = blockInfo.getBlockNumber();
        JSONObject blockJson = new JSONObject();
        blockJson.put("blockNumber", blockNumber);
        blockJson.put("dataHash", Hex.encodeHexString(blockInfo.getDataHash()));
        blockJson.put("channelId", blockInfo.getChannelId());
        blockJson.put("previousHashID", Hex.encodeHexString(blockInfo.getPreviousHash()));
        //blockJson.put("calculatedBlockHash", Hex.encodeHexString(SDKUtils.calculateBlockHash(org.getClient(), blockNumber, blockInfo.getPreviousHash(), blockInfo.getDataHash())));
        blockJson.put("envelopeCount", blockInfo.getEnvelopeCount());
        blockJson.put("envelopes", getEnvelopeJsonArray(blockInfo, blockNumber));
        return blockJson;
    }


    /**
     * 解析区块包
     */
    public static JSONArray getEnvelopeJsonArray(BlockInfo blockInfo, long blockNumber) throws UnsupportedEncodingException, InvalidProtocolBufferException {
        JSONArray envelopeJsonArray = new JSONArray();
        for (BlockInfo.EnvelopeInfo info : blockInfo.getEnvelopeInfos()) {
            JSONObject envelopeJson = new JSONObject();
            envelopeJson.put("channelId", info.getChannelId());
            envelopeJson.put("transactionID", info.getTransactionID());
            envelopeJson.put("validationCode", info.getValidationCode());
            //envelopeJson.put("timestamp", FORMAT.format(new Date(info.getTimestamp().getTime())));
            envelopeJson.put("timestamp", DateUtil.format(new Date(info.getTimestamp().getTime()),"yyyy-MM-dd HH:mm:ss"));
            envelopeJson.put("type", info.getType());
            envelopeJson.put("createId", info.getCreator().getId());
            envelopeJson.put("createMSPID", info.getCreator().getMspid());
            envelopeJson.put("isValid", info.isValid());
            envelopeJson.put("nonce", Hex.encodeHexString(info.getNonce()));
            //查询交易
            if (info.getType() == BlockInfo.EnvelopeType.TRANSACTION_ENVELOPE) {
                BlockInfo.TransactionEnvelopeInfo txeInfo = (BlockInfo.TransactionEnvelopeInfo) info;
                JSONObject transactionEnvelopeInfoJson = new JSONObject();
                int txCount = txeInfo.getTransactionActionInfoCount();
                transactionEnvelopeInfoJson.put("txCount", txCount);
                transactionEnvelopeInfoJson.put("isValid", txeInfo.isValid());
                transactionEnvelopeInfoJson.put("validationCode", txeInfo.getValidationCode());
                transactionEnvelopeInfoJson.put("transactionActionInfoArray", getTransactionActionInfoJsonArray(txeInfo, txCount));
                envelopeJson.put("transactionEnvelopeInfo", transactionEnvelopeInfoJson);
            }
            envelopeJsonArray.add(envelopeJson);
        }
        return envelopeJsonArray;
    }

    /**
     * 解析交易请求集合
     */
    public static JSONArray getTransactionActionInfoJsonArray(BlockInfo.TransactionEnvelopeInfo txeInfo, int txCount) throws UnsupportedEncodingException, InvalidProtocolBufferException {
        JSONArray transactionActionInfoJsonArray = new JSONArray();
        for (int i = 0; i < txCount; i++) {
            BlockInfo.TransactionEnvelopeInfo.TransactionActionInfo txInfo = txeInfo.getTransactionActionInfo(i);
            int endorsementsCount = txInfo.getEndorsementsCount();
            int chaincodeInputArgsCount = txInfo.getChaincodeInputArgsCount();
            JSONObject transactionActionInfoJson = new JSONObject();
            transactionActionInfoJson.put("responseStatus", txInfo.getResponseStatus());
            transactionActionInfoJson.put("responseMessageString", printableString(new String(txInfo.getResponseMessageBytes(), "UTF-8")));
            transactionActionInfoJson.put("endorsementsCount", endorsementsCount);
            transactionActionInfoJson.put("chaincodeInputArgsCount", chaincodeInputArgsCount);
            transactionActionInfoJson.put("status", txInfo.getProposalResponseStatus());
            transactionActionInfoJson.put("payload", printableString(new String(txInfo.getProposalResponsePayload(), "UTF-8")));

//            log.debug("Transaction action " + i + " has response status " + txInfo.getResponseStatus());
//            log.debug("Transaction action " + i + " has response message bytes as string: " + printableString(new String(txInfo.getResponseMessageBytes(), "UTF-8")));
//            log.debug("Transaction action " + i + " has endorsements " + endorsementsCount);

            transactionActionInfoJson.put("endorserInfoArray", getEndorserInfoJsonArray(txInfo, endorsementsCount));

//            log.debug("Transaction action " + i + " has " + chaincodeInputArgsCount + " chaincode input arguments");

            transactionActionInfoJson.put("argArray", getArgJSONArray(i, txInfo, chaincodeInputArgsCount));

//            log.debug("Transaction action " + i + " proposal response status: " + txInfo.getProposalResponseStatus());
//            log.debug("Transaction action " + i + " proposal response payload: " + printableString(new String(txInfo.getProposalResponsePayload())));

            TxReadWriteSetInfo rwsetInfo = txInfo.getTxReadWriteSet();
            JSONObject rwsetInfoJson = new JSONObject();
            if (null != rwsetInfo) {
                int nsRWsetCount = rwsetInfo.getNsRwsetCount();
                rwsetInfoJson.put("nsRWsetCount", nsRWsetCount);
//                log.debug("Transaction action " + i + " has " + nsRWsetCount + " name space read write sets");
                rwsetInfoJson.put("nsRwsetInfoArray", getNsRwsetInfoJsonArray(rwsetInfo));
            }
            transactionActionInfoJson.put("rwsetInfo", rwsetInfoJson);
            transactionActionInfoJsonArray.add(transactionActionInfoJson);
        }
        return transactionActionInfoJsonArray;
    }

    /**
     * 解析参数
     */
    public static JSONArray getArgJSONArray(int i, BlockInfo.TransactionEnvelopeInfo.TransactionActionInfo txInfo, int chaincodeInputArgsCount) throws UnsupportedEncodingException {
        JSONArray argJsonArray = new JSONArray();
        for (int z = 0; z < chaincodeInputArgsCount; ++z) {
            argJsonArray.add(printableString(new String(txInfo.getChaincodeInputArgs(z), "UTF-8")));
//            log.debug("Transaction action " + i + " has chaincode input argument " + z + "is: " + printableString(new String(txInfo.getChaincodeInputArgs(z), "UTF-8")));
        }
        return argJsonArray;
    }

    /**
     * 解析背书信息
     */
    public static JSONArray getEndorserInfoJsonArray(BlockInfo.TransactionEnvelopeInfo.TransactionActionInfo txInfo, int endorsementsCount) {
        JSONArray endorserInfoJsonArray = new JSONArray();
        for (int n = 0; n < endorsementsCount; ++n) {
            BlockInfo.EndorserInfo endorserInfo = txInfo.getEndorsementInfo(n);
            String signature = Hex.encodeHexString(endorserInfo.getSignature());
            String id = endorserInfo.getId();
            String mspId = endorserInfo.getMspid();
            JSONObject endorserInfoJson = new JSONObject();
            endorserInfoJson.put("signature", signature);
            endorserInfoJson.put("id", id);
            endorserInfoJson.put("mspId", mspId);

//            log.debug("Endorser " + n + " signature: " + signature);
//            log.debug("Endorser " + n + " id: " + id);
//            log.debug("Endorser " + n + " mspId: " + mspId);
            endorserInfoJsonArray.add(endorserInfoJson);
        }
        return endorserInfoJsonArray;
    }


    /**
     * 解析读写集集合
     */
    public static JSONArray getNsRwsetInfoJsonArray(TxReadWriteSetInfo rwsetInfo) throws InvalidProtocolBufferException, UnsupportedEncodingException {
        JSONArray nsRwsetInfoJsonArray = new JSONArray();
        for (TxReadWriteSetInfo.NsRwsetInfo nsRwsetInfo : rwsetInfo.getNsRwsetInfos()) {
            final String namespace = nsRwsetInfo.getNamespace();
            KvRwset.KVRWSet rws = nsRwsetInfo.getRwset();
            JSONObject nsRwsetInfoJson = new JSONObject();

            nsRwsetInfoJson.put("readSet", getReadSetJSONArray(rws, namespace));
            nsRwsetInfoJson.put("writeSet", getWriteSetJSONArray(rws, namespace));
            nsRwsetInfoJsonArray.add(nsRwsetInfoJson);
        }
        return nsRwsetInfoJsonArray;
    }

    /**
     * 解析读集
     */
    public static JSONArray getReadSetJSONArray(KvRwset.KVRWSet rws, String namespace) {
        JSONArray readJsonArray = new JSONArray();
        int rs = -1;
        for (KvRwset.KVRead readList : rws.getReadsList()) {
            rs++;
            String key = readList.getKey();
            long readVersionBlockNum = readList.getVersion().getBlockNum();
            long readVersionTxNum = readList.getVersion().getTxNum();
            JSONObject readInfoJson = new JSONObject();
            readInfoJson.put("namespace", namespace);
            readInfoJson.put("readSetIndex", rs);
            readInfoJson.put("key", key);
            readInfoJson.put("readVersionBlockNum", readVersionBlockNum);
            readInfoJson.put("readVersionTxNum", readVersionTxNum);
            readInfoJson.put("chaincode_version", String.format("[%s : %s]", readVersionBlockNum, readVersionTxNum));
            readJsonArray.add(readInfoJson);
//            log.debug("Namespace " + namespace + " read set " + rs + " key " + key + " version [" + readVersionBlockNum + " : " + readVersionTxNum + "]");
        }
        return readJsonArray;
    }


    /**
     * 解析写集
     */
    public static JSONArray getWriteSetJSONArray(KvRwset.KVRWSet rws, String namespace) throws UnsupportedEncodingException {
        JSONArray writeJsonArray = new JSONArray();
        int rs = -1;
        for (KvRwset.KVWrite writeList : rws.getWritesList()) {
            rs++;
            String key = writeList.getKey();
            String valAsString = printableString(new String(writeList.getValue().toByteArray(), "UTF-8"));
            JSONObject writeInfoJson = new JSONObject();
            writeInfoJson.put("namespace", namespace);
            writeInfoJson.put("writeSetIndex", rs);
            writeInfoJson.put("key", key);
            writeInfoJson.put("value", valAsString);
//            log.debug("Namespace " + namespace + " write set " + rs + " key " + key + " has value " + valAsString);
            writeJsonArray.add(writeInfoJson);
        }
        return writeJsonArray;
    }

    public static String printableString(final String string) {
        int maxLogStringLength = 64;
        if (string == null || string.length() == 0) {
            return string;
        }
        String ret = string.replaceAll("[^\\p{Print}]", "?");
        ret = ret.substring(0, Math.min(ret.length(), maxLogStringLength)) + (ret.length() > maxLogStringLength ? "..." : "");
        return ret;
    }



}
