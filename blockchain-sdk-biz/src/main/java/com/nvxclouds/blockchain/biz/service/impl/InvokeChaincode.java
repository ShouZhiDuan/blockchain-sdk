package com.nvxclouds.blockchain.biz.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.nvxclouds.blockchain.biz.config.Config;
import org.apache.commons.io.IOUtils;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.ChaincodeResponse.Status;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @PackageName:com.nvxclouds.blockchain.sdk.service.impl
 * @ClassName:InvokeChaincode
 * @Description:
 * @Author zy
 * @Date 2020/5/28 11:31
 */
public class InvokeChaincode {
    private static final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);
    private static final String EXPECTED_EVENT_NAME = "event";

    public static void main(String args[]) {
        try {
            Util.cleanUp();
            UserContext adminUser = new UserContext();
            adminUser.setName(Config.ADMIN);
            adminUser.setAffiliation(Config.DATAPROVIDER);
            adminUser.setMspId(Config.DataProviderMSP);
            File f = ResourceUtils.getFile( "classpath:" + Config.DATAPROVIDER_TLSFILE);
            String certficate = new String(IOUtils.toByteArray(new FileInputStream(f)), "UTF-8");
            Properties properties = new Properties();
            properties.put("pemBytes", certficate.getBytes());
            properties.setProperty("pemFile", f.getAbsolutePath());
            properties.setProperty("allowAllHostNames", "true");
            CAClient caclient = new CAClient(Config.DATAPROVIDER, Config.CA_DATAPROVIDER_URL, properties);
            caclient.setAdminUserContext(adminUser);
            adminUser = caclient.enrollAdminUserTLS("admin", "adminpw");

            FabricClient fabClient = new FabricClient(adminUser);

            ChannelClient channelClient = fabClient.createChannelClient(Config.CHANNEL_NAME);
            Channel channel = channelClient.getChannel();
            Properties properties1 = Util.gate2ePro("peer", Config.DATAPROVIDER_PEER_1);
            Peer appuserPeer = fabClient.getInstance().newPeer(Config.DATAPROVIDER_PEER_1, Config.DATAPROVIDER_PEER_1_URL, properties1);
            Properties properties2 = Util.gate2ePro("peer", Config.DATAPROVIDER_PEER_1);
            Peer dataproviderPeer2 = fabClient.getInstance().newPeer(Config.DATAPROVIDER_PEER_0, Config.DATAPROVIDER_PEER_1_URL, properties2);
            EventHub eventHub = fabClient.getInstance().newEventHub("eventhub01", Config.grpc + Config.server0Url + ":7053", properties1);
//			EventHub eventHub2 = fabClient.getInstance().newEventHub("eventhub02", "grpcs://"+Config.server0Url+":9053");
            Properties orderPro = Util.gate2ePro("orderer", Config.ORDERER0_NAME);
            Orderer orderer = fabClient.getInstance().newOrderer(Config.ORDERER0_NAME, Config.ORDERER0_URL, orderPro);
            channel.addPeer(appuserPeer);
//            channel.addPeer(dataproviderPeer2);//背书策略为OR的话不需要添加 peer2
            channel.addEventHub(eventHub);
            channel.addOrderer(orderer);
            channel.initialize();

            TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
            ChaincodeID ccid = ChaincodeID.newBuilder().setName(Config.CHAINCODE_TOBCC_NAME).build();
            request.setChaincodeID(ccid);
            request.setFcn("dataProviderInvoke");
            //创建json字符串，传入invoke中
            JSONObject object = new JSONObject();
            object.put("Operation", "dataNodeRegistration");
            object.put("DataNodeID", "dataNode999");
            object.put("NodeName", "333");
            object.put("TxTime", "20200521115623");
            object.put("MessageHash", "hash333");

            String str = object.toJSONString();
            String[] arg = new String[1];
            arg[0] = str;
            request.setArgs(arg);
            request.setProposalWaitTime(1000);

            Map<String, byte[]> tm2 = new HashMap<>();
            tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8));
            tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8));
            tm2.put("result", ":)".getBytes(UTF_8));
            tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA);
            request.setTransientMap(tm2);
            Collection<ProposalResponse> responses = channelClient.sendTransactionProposal(request);
            for (ProposalResponse res : responses) {
                Status status = res.getStatus();
                Logger.getLogger(InvokeChaincode.class.getName()).log(Level.INFO, "Invoked on " + Config.CHAINCODE_TOCCC_NAME + ". Status - " + status);
            }
//            BlockEvent.TransactionEvent trEvent = channel.sendTransaction(responses).get();
            System.out.println("----");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
