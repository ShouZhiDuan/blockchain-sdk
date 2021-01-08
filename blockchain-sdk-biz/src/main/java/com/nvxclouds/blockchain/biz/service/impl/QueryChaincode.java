package com.nvxclouds.blockchain.biz.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.nvxclouds.blockchain.biz.config.Config;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.openssl.PEMWriter;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.PrivateKey;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @PackageName:com.nvxclouds.blockchain.sdk.service.impl
 * @ClassName:QueryChaincode
 * @Description:
 * @Author zy
 * @Date 2020/5/28 11:29
 */
public class QueryChaincode {
    private static final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);
    private static final String EXPECTED_EVENT_NAME = "event";


    static String getPEMStringFromPrivateKey(PrivateKey privateKey) throws IOException {
        StringWriter pemStrWriter = new StringWriter();
        PEMWriter pemWriter = new PEMWriter(pemStrWriter);

        pemWriter.writeObject(privateKey);

        pemWriter.close();

        return pemStrWriter.toString();
    }


    public static HFCAClient enableTLS(HFCAClient hfcaclient, String uesrName, String userpasswd, String orgName, String caHsotIp) {
        final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
        enrollmentRequestTLS.addHost(caHsotIp);
        enrollmentRequestTLS.setProfile("tls");
        try {
            final Enrollment enroll = hfcaclient.enroll(uesrName, userpasswd, enrollmentRequestTLS);
            final String tlsCertPEM = enroll.getCert();
            final String tlsKeyPEM = getPEMStringFromPrivateKey(enroll.getKey());
            final Properties tlsProperties = new Properties();
            tlsProperties.put("clientKeyBytes", tlsKeyPEM.getBytes(UTF_8));
            tlsProperties.put("clientCertBytes", tlsCertPEM.getBytes(UTF_8));

        } catch (EnrollmentException | InvalidArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return hfcaclient;

    }


    public static void main(String args[]) {
        try {
            Util.cleanUp();
            UserContext adminUser = new UserContext();
            adminUser.setName(Config.ADMIN);
            adminUser.setAffiliation(Config.DATAPROVIDER);
            adminUser.setMspId(Config.DataProviderMSP);
            File f = ResourceUtils.getFile("classpath:" + Config.DATAPROVIDER_TLSFILE);
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
            Peer peer = fabClient.getInstance().newPeer(Config.DATAPROVIDER_PEER_1, Config.DATAPROVIDER_PEER_1_URL, properties1);

            EventHub eventHub = fabClient.getInstance().newEventHub("eventhub01", Config.grpc + Config.server0Url + ":7053");
            Properties orderPro = Util.gate2ePro("orderer", Config.ORDERER0_NAME);
            Orderer orderer = fabClient.getInstance().newOrderer(Config.ORDERER0_NAME, Config.ORDERER0_URL, orderPro);
            channel.addPeer(peer);
            channel.addOrderer(orderer);
            channel.initialize();
            Logger.getLogger(QueryChaincode.class.getName()).log(Level.INFO, "Querying   ...");
            //创建json字符串，传入invoke中
            JSONObject object = new JSONObject();
            object.put("Operation", "dataNodeRegistration");
            object.put("DataNodeID", "dataNode999");
            String str = object.toJSONString();
            String[] arg = new String[1];
            arg[0] = str;

            Collection<ProposalResponse> responsesQuery = channelClient.queryByChainCode(Config.CHAINCODE_TOBCC_NAME, "dataProviderQuery", arg);
            for (ProposalResponse pres : responsesQuery) {
//                System.out.println("pres====="+pres.getChaincodeActionResponsePayload());
                String stringResponse = new String(pres.getChaincodeActionResponsePayload());
                Logger.getLogger(QueryChaincode.class.getName()).log(Level.INFO, stringResponse);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
