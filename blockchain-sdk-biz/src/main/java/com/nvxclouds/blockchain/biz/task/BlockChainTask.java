package com.nvxclouds.blockchain.biz.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.InvalidProtocolBufferException;
import com.nvxclouds.blockchain.biz.domain.BlockChainInfo;
import com.nvxclouds.blockchain.biz.domain.TradeInfo;
import com.nvxclouds.blockchain.biz.mapper.BlockChainInfoMapper;
import com.nvxclouds.blockchain.biz.mapper.TradeInfoMapper;
import com.nvxclouds.blockchain.biz.service.DataProviderService;
import com.nvxclouds.blockchain.biz.utils.DateUtil;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: ShouZhi@Duan
 * @Date: 2020/8/24 13:17
 * @Description: 区块链相定时任务
 */
@Slf4j
@Component
public class BlockChainTask {

    @Autowired
    private DataProviderService dataProviderService;

    @Autowired
    private BlockChainInfoMapper blockChainInfoMapper;

    @Autowired
    private TradeInfoMapper tradeInfoMapper;


    /**
     * 同步区块列表数据
     */
    //@Scheduled(fixedRate = 15000)
    //@Scheduled(initialDelay=1000, fixedDelay=60000)
    @Transactional
    @Scheduled(cron="0 0 0 * * ?")
    public void syncBlockChain() throws Exception {
        long startTime = System.currentTimeMillis();
        //log.info(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "开始执行区块列表数据同步");
        //1、先查询BlockChainInfo表中是否存在数据，存在增量更新、不存在全量更新。
        List<BlockChainInfo> blockChainInfoList = blockChainInfoMapper.selectAll();
        if(CollectionUtils.isEmpty(blockChainInfoList)){
            //======>全量新增
            List<BlockChainInfo> allBlockChainInfos = querBlockBaseInfoList(dataProviderService.getBlockChainClientHandler().init().getChannel(),null);
            int count = blockChainInfoMapper.insertList(allBlockChainInfos);
            //log.info("区块列表全量新增：" + count + "条。");
        }else {
            //======>增量新增
            Long maxBlockNumber = blockChainInfoMapper.getMaxBlockNumber();
            if(maxBlockNumber.compareTo(Long.valueOf(0)) >= 0){
                //有就更新没有就插入
                List<BlockChainInfo> detBlockChainInfos = querBlockBaseInfoList(dataProviderService.getBlockChainClientHandler().init().getChannel(),maxBlockNumber);
                if(!CollectionUtils.isEmpty(detBlockChainInfos)){
                    int count = blockChainInfoMapper.insertBlockList(detBlockChainInfos);
                    //log.info("区块列表增量新增：" + detBlockChainInfos.size() + "条。");
                }
            }else {
               //log.info("暂无区块数据更新");
            }
        }
        long endTime = System.currentTimeMillis();
        //log.info("当前批次区块列表同步耗时：" + (endTime - startTime)/1000 + "秒");
    }


    /**
     * 同步交易列表数据
     */
    @Transactional
    @Scheduled(cron="0 0 1 * * ?")
    //@Scheduled(initialDelay=30, fixedDelay=120000)
    public void syncTradeInfo() throws Exception {
        long startTime = System.currentTimeMillis();
        //log.info(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "开始执行交易列表数据同步");
        List<TradeInfo> tradeInfos = queryTransactionList(dataProviderService.getBlockChainClientHandler().init().getChannel());
        int count = tradeInfoMapper.insertTradeList(tradeInfos);
        long endTime = System.currentTimeMillis();
//        log.info("交易列表增量新增：" + count + "条。");
//        log.info("当前批次交易列表同步耗时：" + (endTime - startTime)/1000 + "秒");
    }


    private  List<BlockChainInfo> querBlockBaseInfoList(Channel channel, Long maxId) throws ProposalException, InvalidArgumentException, InvalidProtocolBufferException {
        //查询区块高度
        BlockchainInfo blockchainInfo = channel.queryBlockchainInfo();
        List<BlockChainInfo> listDTO = new ArrayList();
        if(blockchainInfo.getHeight() > 0){
                if(null != maxId){
                    if(Long.valueOf(blockchainInfo.getHeight() - 1).compareTo(maxId) >= 0){
                        //1、增量查询
                        for (long i = (blockchainInfo.getHeight() - 1); i >= maxId; i--){
                            //获取当前索引位置区块信息
                            BlockInfo blockInfo = channel.queryBlockByNumber(i);
                            listDTO.add(convertorBlockChainBaseInfoDTO(blockInfo));
                        }
                    }else {
                        return null;
                    }
                }else {
                    //2、全量查询
                    for (long i = (blockchainInfo.getHeight() - 1); i >= 0; i--){
                        //获取当前索引位置区块信息
                        BlockInfo blockInfo = channel.queryBlockByNumber(i);
                        listDTO.add(convertorBlockChainBaseInfoDTO(blockInfo));
                    }
                }
        }else {
            return null;
        }
        return listDTO;
    }

    private  BlockChainInfo convertorBlockChainBaseInfoDTO(BlockInfo blockInfo) throws InvalidProtocolBufferException {
        BlockChainInfo blockChainInfo = new BlockChainInfo();
        blockChainInfo.setBlockNumber(blockInfo.getBlockNumber());//设置区块高度
        blockChainInfo.setTransactionCount(blockInfo.getEnvelopeCount());//获取当块上交易条数
        blockChainInfo.setChannelId(blockInfo.getChannelId());//设置当前通道名称
        blockChainInfo.setDataHash(Hex.encodeHexString(blockInfo.getDataHash()));//当前块hash
        blockChainInfo.setPreviousHashID(Hex.encodeHexString(blockInfo.getPreviousHash()));//当前父hash
        return blockChainInfo;
    }


    /**
     * 解析所有交易列表
     */
    public static List<TradeInfo> queryTransactionList(Channel channel) throws InvalidArgumentException, ProposalException, IOException, ParseException {
        //envelopes[]->transactionEnvelopeInfo(对象)->transactionActionInfoArray[]->rwsetInfo(对象)->nsRwsetInfoArray[]->writeSet
        //获取所有区块信息列表
        BlockchainInfo blockchainInfo = channel.queryBlockchainInfo();
        List<TradeInfo> tradeDetailsList = new ArrayList<TradeInfo>();
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
                                                        TradeInfo tradeInfo = new TradeInfo();
                                                        //设置当前交易的区块高度
                                                        tradeDetailsObject.put("blockHeight",blockInfo.getBlockNumber());
                                                        tradeInfo.setBlockHeight(blockInfo.getBlockNumber());
                                                        //时间格式转换
                                                        if(null != tradeDetailsObject.get("TxTime")){
                                                            String dataStr = tradeDetailsObject.get("TxTime").toString();
                                                            if(StringUtils.isNotBlank(dataStr)) {
                                                                dataStr = dataStr.replaceAll("\\\"","");
                                                                //yyyyMMddHHmmss 20200521115623  202007151758
                                                                if (dataStr.length() == 14) {
                                                                    //log.info("yyyyMMddHHmmss = " + dataStr + " and length = " + dataStr.length());
                                                                    tradeDetailsObject.put("TxTime", DateUtil.format(DateUtil.parse(dataStr,"yyyyMMddHHmmss"),"yyyy-MM-dd HH:mm:ss"));
                                                                    tradeInfo.setTxTime(DateUtil.format(DateUtil.parse(dataStr,"yyyyMMddHHmmss"),"yyyy-MM-dd HH:mm:ss"));
                                                                }
                                                            }
                                                        }

                                                        //操作名称转换
                                                        String operationName = tradeDetailsObject.get("Operation").toString();
                                                        //TransactionID
                                                        tradeInfo.setTransactionID(tradeDetailsObject.getString("TransactionID"));
                                                        //MessageHash
                                                        tradeInfo.setMessageHash(tradeDetailsObject.getString("MessageHash"));
                                                        //DataNodeID
                                                        tradeInfo.setDataNodeID(tradeDetailsObject.getString("DataNodeID"));
                                                        //DatasetID
                                                        tradeInfo.setDatasetID(tradeDetailsObject.getString("DatasetID"));
                                                        //NodeName
                                                        tradeInfo.setNodeName(tradeDetailsObject.getString("NodeName"));
                                                        if(StringUtils.isNotBlank(operationName)){
                                                            /**
                                                             * 数据提供方
                                                             */
                                                            if(operationName.equals("dataNodeRegistration")){
                                                                //数据节点注册操作
                                                                tradeDetailsObject.put("Operation","数据节点注册操作");
                                                                tradeDetailsObject.put("OrganizationName","数据提供方");
                                                                tradeInfo.setOperation("数据节点注册操作");
                                                                tradeInfo.setOrganizationName("数据提供方");
                                                            }
                                                            if(operationName.equals("dataNodeModificationApply")){
                                                                //数据节点修改申请
                                                                tradeDetailsObject.put("OrganizationName","数据提供方");
                                                                tradeDetailsObject.put("Operation","数据节点修改申请操作");
                                                                tradeInfo.setOperation("数据节点修改申请操作");
                                                                tradeInfo.setOrganizationName("数据提供方");
                                                            }
                                                            if(operationName.equals("addDatasetApply")){
                                                                //增加数据集申请
                                                                tradeDetailsObject.put("Operation","增加数据集申请操作");
                                                                tradeDetailsObject.put("OrganizationName","数据提供方");
                                                                tradeInfo.setOperation("增加数据集申请操作");
                                                                tradeInfo.setOrganizationName("数据提供方");
                                                            }
                                                            if(operationName.equals("openDatasets")){
                                                                //开放数据集
                                                                tradeDetailsObject.put("Operation","开放数据集操作");
                                                                tradeDetailsObject.put("OrganizationName","数据提供方");
                                                                tradeInfo.setOperation("开放数据集操作");
                                                                tradeInfo.setOrganizationName("数据提供方");
                                                            }
                                                            if(operationName.equals("modifyDatasets")){
                                                                //修改数据集
                                                                tradeDetailsObject.put("Operation","修改数据集操作");
                                                                tradeDetailsObject.put("OrganizationName","数据提供方");
                                                                tradeInfo.setOperation("修改数据集操作");
                                                                tradeInfo.setOrganizationName("数据提供方");
                                                            }
                                                            if(operationName.equals("datasetsOffline")){
                                                                //数据集下线
                                                                tradeDetailsObject.put("Operation","数据集下线操作");
                                                                tradeDetailsObject.put("OrganizationName","数据提供方");
                                                                tradeInfo.setOperation("数据集下线操作");
                                                                tradeInfo.setOrganizationName("数据提供方");
                                                            }
                                                            if(operationName.equals("queryNodeDataResearchResults")){
                                                                //查看研究结果写入
                                                                tradeDetailsObject.put("Operation","查看研究结果写入操作");
                                                                tradeDetailsObject.put("OrganizationName","数据提供方");
                                                                tradeInfo.setOperation("查看研究结果写入操作");
                                                                tradeInfo.setOrganizationName("数据提供方");
                                                            }
                                                            /**
                                                             * 应用提供方
                                                             */
                                                            if(operationName.equals("addAppApply")){
                                                                //增加应用申请写入
                                                                tradeDetailsObject.put("Operation","增加应用申请写入操作");
                                                                tradeDetailsObject.put("OrganizationName","应用提供方");
                                                                tradeInfo.setOperation("增加应用申请写入操作");
                                                                tradeInfo.setOrganizationName("应用提供方");
                                                            }
                                                            if(operationName.equals("appOnline")){
                                                                //应用上架写入
                                                                tradeDetailsObject.put("Operation","应用上架写入操作");
                                                                tradeDetailsObject.put("OrganizationName","应用提供方");
                                                                tradeInfo.setOperation("应用上架写入操作");
                                                                tradeInfo.setOrganizationName("应用提供方");
                                                            }
                                                            if(operationName.equals("modifyAppApply")){
                                                                //修改应用申请写入
                                                                tradeDetailsObject.put("Operation","修改应用申请写入操作");
                                                                tradeDetailsObject.put("OrganizationName","应用提供方");
                                                                tradeInfo.setOperation("修改应用申请写入操作");
                                                                tradeInfo.setOrganizationName("应用提供方");
                                                            }
                                                            if(operationName.equals("queryAppUsage")){
                                                                //查看应用使用情况写入
                                                                tradeDetailsObject.put("Operation","查看应用使用情况写入操作");
                                                                tradeDetailsObject.put("OrganizationName","应用提供方");
                                                                tradeInfo.setOperation("查看应用使用情况写入操作");
                                                                tradeInfo.setOrganizationName("应用提供方");
                                                            }
                                                            if(operationName.equals("appOffline")){
                                                                //应用下架写入
                                                                tradeDetailsObject.put("Operation","应用下架写入操作");
                                                                tradeDetailsObject.put("OrganizationName","应用提供方");
                                                                tradeInfo.setOperation("应用下架写入操作");
                                                                tradeInfo.setOrganizationName("应用提供方");
                                                            }
                                                            /**
                                                             * 数据使用方
                                                             */
                                                            if(operationName.equals("createResearchProjects")){
                                                                //创建研究项目写入
                                                                tradeDetailsObject.put("Operation","创建研究项目写入操作");
                                                                tradeDetailsObject.put("OrganizationName","数据使用方");
                                                                tradeInfo.setOperation("创建研究项目写入操作");
                                                                tradeInfo.setOrganizationName("数据使用方");
                                                            }
                                                            if(operationName.equals("useDatasetApply")){
                                                                //使用数据集申请写入
                                                                tradeDetailsObject.put("Operation","使用数据集申请写入操作");
                                                                tradeDetailsObject.put("OrganizationName","数据使用方");
                                                                tradeInfo.setOperation("使用数据集申请写入操作");
                                                                tradeInfo.setOrganizationName("数据使用方");
                                                            }
                                                            if(operationName.equals("queryUserResearchResults")){
                                                                //查看研究结果写入
                                                                tradeDetailsObject.put("Operation","查看研究结果写入操作");
                                                                tradeDetailsObject.put("OrganizationName","数据使用方");
                                                                tradeInfo.setOperation("查看研究结果写入操作");
                                                                tradeInfo.setOrganizationName("数据使用方");
                                                            }
                                                            if(operationName.equals("purchaseAuthorization")){
                                                                //购买授权写入
                                                                tradeDetailsObject.put("Operation","购买授权写入操作");
                                                                tradeDetailsObject.put("OrganizationName","数据使用方");
                                                                tradeInfo.setOperation("购买授权写入操作");
                                                                tradeInfo.setOrganizationName("数据使用方");
                                                            }
                                                            if(operationName.equals("activateAuthorization")){
                                                                //激活授权写入
                                                                tradeDetailsObject.put("Operation","激活授权写入操作");
                                                                tradeDetailsObject.put("OrganizationName","数据使用方");
                                                                tradeInfo.setOperation("激活授权写入操作");
                                                                tradeInfo.setOrganizationName("数据使用方");
                                                            }
                                                            /**
                                                             * 监管方
                                                             */
                                                            if(operationName.equals("newDatasetApproval")){
                                                                //新数据集审批写入
                                                                tradeDetailsObject.put("Operation","新数据集审批写入操作");
                                                                tradeDetailsObject.put("OrganizationName","监管方");
                                                                tradeInfo.setOperation("新数据集审批写入操作");
                                                                tradeInfo.setOrganizationName("监管方");
                                                            }
                                                            if(operationName.equals("modifyDatasetApproval")){
                                                                //数据集修改审批写入
                                                                tradeDetailsObject.put("Operation","数据集修改审批写入操作");
                                                                tradeDetailsObject.put("OrganizationName","监管方");
                                                                tradeInfo.setOperation("数据集修改审批写入操作");
                                                                tradeInfo.setOrganizationName("监管方");
                                                            }
                                                            if(operationName.equals("researchProjectApproval")){
                                                                //研究项目审批写入
                                                                tradeDetailsObject.put("Operation","研究项目审批写入操作");
                                                                tradeDetailsObject.put("OrganizationName","监管方");
                                                                tradeInfo.setOperation("研究项目审批写入操作");
                                                                tradeInfo.setOrganizationName("监管方");
                                                            }
                                                            if(operationName.equals("useDatasetApproval")){
                                                                //使用数据集审批写入
                                                                tradeDetailsObject.put("Operation","使用数据集审批写入操作");
                                                                tradeDetailsObject.put("OrganizationName","监管方");
                                                                tradeInfo.setOperation("使用数据集审批写入操作");
                                                                tradeInfo.setOrganizationName("监管方");
                                                            }
                                                            if(operationName.equals("newAppApproval")){
                                                                //新应用审批写入
                                                                tradeDetailsObject.put("Operation","新应用审批写入操作");
                                                                tradeDetailsObject.put("OrganizationName","监管方");
                                                                tradeInfo.setOperation("新应用审批写入操作");
                                                                tradeInfo.setOrganizationName("监管方");
                                                            }
                                                        /*    if(operationName.equals("newAppApproval")){
                                                                //新应用审批写入
                                                                tradeDetailsObject.put("Operation","新应用审批写入操作");
                                                                tradeDetailsObject.put("OrganizationName","监管方");
                                                                tradeInfo.setOperation("新应用审批写入操作");
                                                                tradeInfo.setOrganizationName("监管方");
                                                            }*/
                                                            if(operationName.equals("modifyAppApproval")){
                                                                //应用修改审批写入
                                                                tradeDetailsObject.put("Operation","应用修改审批写入操作");
                                                                tradeDetailsObject.put("OrganizationName","监管方");
                                                                tradeInfo.setOperation("应用修改审批写入操作");
                                                                tradeInfo.setOrganizationName("监管方");
                                                            }
                                                            if(operationName.equals("queryProjectResearchResults")){
                                                                //查看研究结果写入
                                                                tradeDetailsObject.put("Operation","查看研究结果写入操作");
                                                                tradeDetailsObject.put("OrganizationName","监管方");
                                                                tradeInfo.setOperation("查看研究结果写入操作");
                                                                tradeInfo.setOrganizationName("监管方");
                                                            }
                                                            /**
                                                             * APP用户
                                                             */
                                                            if(operationName.equals("authorizationDataUpBlockChain")){
                                                                //数据授权及上链写入
                                                                tradeDetailsObject.put("Operation","数据授权及上链写入操作");
                                                                tradeDetailsObject.put("OrganizationName","APP用户");
                                                                tradeInfo.setOperation("数据授权及上链写入操作");
                                                                tradeInfo.setOrganizationName("APP用户");
                                                            }
                                                            if(operationName.equals("authorizationAndParticipationInResearch")){
                                                                //授权及参与研究写入
                                                                tradeDetailsObject.put("Operation","授权及参与研究写入操作");
                                                                tradeDetailsObject.put("OrganizationName","APP用户");
                                                                tradeInfo.setOperation("授权及参与研究写入操作");
                                                                tradeInfo.setOrganizationName("APP用户");
                                                            }
                                                        /*    if(operationName.equals("authorizationAndParticipationInResearch")){
                                                                //授权及参与研究写入
                                                                tradeDetailsObject.put("Operation","授权及参与研究写入操作");
                                                                tradeDetailsObject.put("OrganizationName","APP用户");
                                                                tradeInfo.setOperation("授权及参与研究写入操作");
                                                                tradeInfo.setOrganizationName("APP用户");
                                                            }*/
                                                            if(operationName.equals("queryDataUsage")){
                                                                //查看数据使用情况写入
                                                                tradeDetailsObject.put("Operation","查看数据使用情况写入操作");
                                                                tradeDetailsObject.put("OrganizationName","APP用户");
                                                                tradeInfo.setOperation("查看数据使用情况写入操作");
                                                                tradeInfo.setOrganizationName("APP用户");
                                                            }
                                                            if(operationName.equals("queryAppUserDataResearchResults")){
                                                                //查看研究结果写入
                                                                tradeDetailsObject.put("Operation","查看研究结果写入操作");
                                                                tradeDetailsObject.put("OrganizationName","APP用户");
                                                                tradeInfo.setOperation("查看研究结果写入操作");
                                                                tradeInfo.setOrganizationName("APP用户");
                                                            }
                                                        }
                                                        tradeDetailsList.add(tradeInfo);
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
