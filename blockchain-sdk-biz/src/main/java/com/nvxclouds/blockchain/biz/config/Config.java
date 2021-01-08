package com.nvxclouds.blockchain.biz.config;

/**
 * @PackageName:com.nvxclouds.blockchain.sdk.service.impl
 * @ClassName:Config
 * @Description:
 * @Author zy
 * @Date 2020/5/28 11:26
 */
public class Config {

    public static final String server0Url = "192.168.10.21";
    public static final String server1Url = "192.168.10.22";
    public static final String server2Url = "192.168.10.23";
    public static final String server3Url = "192.168.10.24";
    public static final String server4Url = "192.168.10.25";

    public static final String grpc = "grpcs://";

    public static final String CA_APPUSER_URL = "https://" + server0Url + ":7054";
    public static final String CA_DATAPROVIDER_URL = "https://" + server1Url + ":7054";
    public static final String CA_DATAUSER_URL = "https://" + server2Url + ":7054";
    public static final String CA_REGULATOR_URL = "https://" + server3Url + ":7054";
    public static final String CA_APPPROVIDER_URL = "https://" + server4Url + ":7054";


    public static final String AppUserMSP = "AppUserMSP";
    public static final String DataProviderMSP = "DataProviderMSP";
    public static final String DataUserMSP = "DataUserMSP";
    public static final String RegulatorMSP = "RegulatorMSP";
    public static final String AppProviderMSP = "AppProviderMSP";

    public static final String APPUSER = "appuser";
    public static final String DATAPROVIDER = "dataprovider";
    public static final String DATAUSER = "datauser";
    public static final String REGULATOR = "regulator";
    public static final String APPPROVIDER = "appprovider";

    public static final String CA_APPUSER_NAME = "ca.appuser.nvxclouds.com";
    public static final String CA_DATAPROVIDER_NAME = "ca.dataprovider.nvxclouds.com";
    public static final String CA_DATAUSER_NAME = "ca.datauser.nvxclouds.com";
    public static final String CA_REGULATOR_NAME = "ca.regulator.nvxclouds.com";
    public static final String CA_APPPROVIDER_NAME = "ca.appprovider.nvxclouds.com";

    public static final String ADMIN = "admin";
    public static final String ADMIN_PASSWORD = "adminpw";


    public static final String APPUSER_USR_BASE_PATH = "crypto-config/peerOrganizations/appuser.nvxclouds.com/users/Admin@appuser.nvxclouds.com/msp";
    public static final String DATAPROVIDER_USR_BASE_PATH = "crypto-config/peerOrganizations/dataprovider.nvxclouds.com/users/Admin@dataprovider.nvxclouds.com/msp";
    public static final String DATAUSER_USR_BASE_PATH = "crypto-config/peerOrganizations/datauser.nvxclouds.com/users/Admin@datauser.nvxclouds.com/msp";
    public static final String REGULATOR_USR_BASE_PATH = "crypto-config/peerOrganizations/regulator.nvxclouds.com/users/Admin@regulator.nvxclouds.com/msp";
    public static final String APPPROVIDER_USR_BASE_PATH = "crypto-config/peerOrganizations/appprovider.nvxclouds.com/users/Admin@appprovider.nvxclouds.com/msp";


    public static final String APPUSER_TLSFILE = "crypto-config/peerOrganizations/appuser.nvxclouds.com/ca/ca.appuser.nvxclouds.com-cert.pem";
    public static final String DATAPROVIDER_TLSFILE = "crypto-config/peerOrganizations/dataprovider.nvxclouds.com/ca/ca.dataprovider.nvxclouds.com-cert.pem";
    public static final String DATAUSER_TLSFILE = "crypto-config/peerOrganizations/datauser.nvxclouds.com/ca/ca.datauser.nvxclouds.com-cert.pem";
    public static final String REGULATOR_TLSFILE = "crypto-config/peerOrganizations/regulator.nvxclouds.com/ca/ca.regulator.nvxclouds.com-cert.pem";
    public static final String APPPROVIDER_TLSFILE = "crypto-config/peerOrganizations/appprovider.nvxclouds.com/ca/ca.appprovider.nvxclouds.com-cert.pem";


    public static final String APPUSER_PER1_CAFILE = "crypto-config/peerOrganizations/appuser.nvxclouds.com/tlsca/tlsca.appuser.nvxclouds.com-cert.pem";
    public static final String DATAPROVIDER_PER1_CAFILE = "crypto-config/peerOrganizations/dataprovider.nvxclouds.com/tlsca/tlsca.dataprovider.nvxclouds.com-cert.pem";
    public static final String DATAUSER_PER1_CAFILE = "crypto-config/peerOrganizations/datauser.nvxclouds.com/tlsca/tlsca.datauser.nvxclouds.com-cert.pem";
    public static final String REGULATOR_PER1_CAFILE = "crypto-config/peerOrganizations/regulator.nvxclouds.com/tlsca/tlsca.regulator.nvxclouds.com-cert.pem";
    public static final String APPPROVIDER_PER1_CAFILE = "crypto-config/peerOrganizations/appprovider.nvxclouds.com/tlsca/tlsca.appprovider.nvxclouds.com-cert.pem";
    public static final String ORDERER_CAFILE = "crypto-config/ordererOrganizations/nvxclouds.com/tlsca/tlsca.nvxclouds.com-cert.pem";

    public static final String APPUSER_USR_ADMIN_PK = APPUSER_USR_BASE_PATH + "keystore";
    public static final String APPUSER_USR_ADMIN_CERT = APPUSER_USR_BASE_PATH + "admincerts";

    public static final String DATAPROVIDER_USR_ADMIN_PK = DATAPROVIDER_USR_BASE_PATH + "keystore";
    public static final String DATAPROVIDER_USR_ADMIN_CERT = DATAPROVIDER_USR_BASE_PATH + "admincerts";

    public static final String DATAUSER_USR_ADMIN_PK = DATAUSER_USR_BASE_PATH + "keystore";
    public static final String DATAUSER_USR_ADMIN_CERT = DATAUSER_USR_BASE_PATH + "admincerts";

    public static final String REGULATOR_USR_ADMIN_PK = REGULATOR_USR_BASE_PATH + "keystore";
    public static final String REGULATOR_USR_ADMIN_CERT = REGULATOR_USR_BASE_PATH + "admincerts";

    public static final String APPPROVIDER_USR_ADMIN_PK = APPPROVIDER_USR_BASE_PATH + "keystore";
    public static final String APPPROVIDER_USR_ADMIN_CERT = APPPROVIDER_USR_BASE_PATH + "admincerts";

    public static final String ORDERER0_URL = grpc + server0Url + ":7050";
    public static final String ORDERER1_URL = grpc + server1Url + ":7050";
    public static final String ORDERER2_URL = grpc + server2Url + ":7050";
    public static final String ORDERER3_URL = grpc + server3Url + ":7050";
    public static final String ORDERER4_URL = grpc + server4Url + ":7050";
    public static final String ORDERER0_NAME = "orderer0.nvxclouds.com";
    public static final String ORDERER1_NAME = "orderer1.nvxclouds.com";
    public static final String ORDERER2_NAME = "orderer2.nvxclouds.com";
    public static final String ORDERER3_NAME = "orderer3.nvxclouds.com";
    public static final String ORDERER4_NAME = "orderer4.nvxclouds.com";

    public static final String CHANNEL_NAME = "nvxchannel";
    public static final String CHANNEL_CONFIG_PATH = "channel-artifacts/channel.tx";

    public static final String APPUSER_PEER_0 = "peer0.appuser.nvxclouds.com";
    public static final String APPUSER_PEER_0_URL = grpc + server0Url + ":7051";
    public static final String APPUSER_PEER_1 = "peer1.appuser.nvxclouds.com";
    public static final String APPUSER_PEER_1_URL = grpc + server4Url + ":8051";

    public static final String DATAPROVIDER_PEER_0 = "peer0.dataprovider.nvxclouds.com";
    public static final String DATAPROVIDER_PEER_0_URL = grpc + server1Url + ":7051";
    public static final String DATAPROVIDER_PEER_1 = "peer1.dataprovider.nvxclouds.com";
    public static final String DATAPROVIDER_PEER_1_URL = grpc + server0Url + ":8051";

    public static final String DATAUSER_PEER_0 = "peer0.datauser.nvxclouds.com";
    public static final String DATAUSER_PEER_0_URL = grpc + server2Url + ":7051";
    public static final String DATAUSER_PEER_1 = "peer1.datauser.nvxclouds.com";
    public static final String DATAUSER_PEER_1_URL = grpc + server1Url + ":8051";

    public static final String REGULATOR_PEER_0 = "peer0.regulator.nvxclouds.com";
    public static final String REGULATOR_PEER_0_URL = grpc + server3Url + ":7051";
    public static final String REGULATOR_PEER_1 = "peer1.regulator.nvxclouds.com";
    public static final String REGULATOR_PEER_1_URL = grpc + server2Url + ":8051";

    public static final String APPPROVIDER_PEER_0 = "peer0.appprovider.nvxclouds.com";
    public static final String APPPROVIDER_PEER_0_URL = grpc + server4Url + ":7051";
    public static final String APPPROVIDER_PEER_1 = "peer1.appprovider.nvxclouds.com";
    public static final String APPPROVIDER_PEER_1_URL = grpc + server3Url + ":8051";

    public static final String CHAINCODE_ROOT_DIR = "chaincode";

    public static final String CHAINCODE_TOBCC_NAME = "tobcc";
    public static final String CHAINCODE_TOCCC_NAME = "toccc";

    public static final String CHAINCODE_TOBCC_PATH = "github.com/chaincode/tobcc";
    public static final String CHAINCODE_TOCCC_PATH = "github.com/chaincode/toccc";

    public static final String CHAINCODE_TOBCC_VERSION = "1.0";
    public static final String CHAINCODE_TOCCC_VERSION = "1.0";

    public static final String EVENT_HUB_PROT0 = ":7053";
    public static final String EVENT_HUB_PROT1 = ":8053";
}