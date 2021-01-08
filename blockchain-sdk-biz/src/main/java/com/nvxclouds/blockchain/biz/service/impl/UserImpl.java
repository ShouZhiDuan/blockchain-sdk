package com.nvxclouds.blockchain.biz.service.impl;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.identity.X509Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoPrimitives;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Set;

/**
 * @Auther: zhengxing.hu
 * @Date: 2020/5/26 13:31
 * @Description:
 */
public class UserImpl implements User {

    private String name;
    private String mspId;
    private Enrollment enrollment;

    public UserImpl(String name, String mspId, String keyFile, String certFile) throws Exception {
        this.name = name;
        this.mspId = mspId;
        this.enrollment = loadFromPemFile(keyFile, certFile);
    }

    private Enrollment loadFromPemFile(String keyFile, String certFile) throws Exception {
        ClassLoader.getSystemResource(keyFile);
        byte[] keyPem = Files.readAllBytes(Paths.get(keyFile));     //载入私钥PEM文本
        byte[] certPem = Files.readAllBytes(Paths.get(certFile));   //载入证书PEM文本
        CryptoPrimitives suite = new CryptoPrimitives();            //载入密码学套件
        PrivateKey privateKey = suite.bytesToPrivateKey(keyPem);    //将PEM文本转换为私钥对象
        return new X509Enrollment(privateKey, new String(certPem));  //创建并返回X509Enrollment对象
    }


    public String getName() {
        return this.name;
    }

    public Set<String> getRoles() {
        return null;
    }

    public String getAccount() {
        return null;
    }

    public String getAffiliation() {
        return null;
    }

    public Enrollment getEnrollment() {
        return this.enrollment;
    }

    public String getMspId() {
        return this.mspId;
    }

    public static void main(String[] args) throws IOException {
        Files.readAllBytes(Paths.get("appprovider.nvxclouds.com/ca/42e8b2f5612935ffb88c4e3ef1eb1bcb3b50d4460cd9d7e1bb676b19dcf46592_sk"));

    }
}
