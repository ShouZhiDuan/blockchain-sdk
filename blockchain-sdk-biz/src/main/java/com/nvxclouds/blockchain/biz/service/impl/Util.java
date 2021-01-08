package com.nvxclouds.blockchain.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nvxclouds.blockchain.api.query.DataNodeModificationQuery;
import com.nvxclouds.blockchain.api.query.PageQuery;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.sdk.exception.CryptoException;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @PackageName:com.nvxclouds.blockchain.sdk.service.impl
 * @ClassName:Util
 * @Description:
 * @Author zy
 * @Date 2020/5/28 11:32
 */
@Slf4j
public class Util {
    public static void writeUserContext(UserContext userContext) throws Exception {
        String directoryPath = "users/" + userContext.getAffiliation();
        String filePath = directoryPath + "/" + userContext.getName() + ".ser";
        File directory = new File(directoryPath);
        if (!directory.exists())
            directory.mkdirs();

        FileOutputStream file = new FileOutputStream(filePath);
        ObjectOutputStream out = new ObjectOutputStream(file);

        // Method for serialization of object
        out.writeObject(userContext);

        out.close();
        file.close();
    }


    public static UserContext readUserContext(String affiliation, String username) throws Exception {
        String filePath = "users/" + affiliation + "/" + username + ".ser";
        File file = new File(filePath);
        if (file.exists()) {
            // Reading the object from a file
            FileInputStream fileStream = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileStream);

            // Method for deserialization of object
            UserContext uContext = (UserContext) in.readObject();

            in.close();
            fileStream.close();
            return uContext;
        }
        return null;
    }


    public static CAEnrollment getEnrollment(String keyFolderPath, String keyFileName, String certFolderPath,
                                             String certFileName)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, CryptoException {
        PrivateKey key = null;
        String certificate = null;
        InputStream isKey = null;
        BufferedReader brKey = null;

        try {

            isKey = new FileInputStream(keyFolderPath + File.separator + keyFileName);
            brKey = new BufferedReader(new InputStreamReader(isKey));
            StringBuilder keyBuilder = new StringBuilder();

            for (String line = brKey.readLine(); line != null; line = brKey.readLine()) {
                if (line.indexOf("PRIVATE") == -1) {
                    keyBuilder.append(line);
                }
            }

            certificate = new String(Files.readAllBytes(Paths.get(certFolderPath, certFileName)));

            byte[] encoded = DatatypeConverter.parseBase64Binary(keyBuilder.toString());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            KeyFactory kf = KeyFactory.getInstance("EC");
            key = kf.generatePrivate(keySpec);
        } finally {
            isKey.close();
            brKey.close();
        }

        CAEnrollment enrollment = new CAEnrollment(key, certificate);
        return enrollment;
    }

    public static void cleanUp() {
        String directoryPath = "users";
        File directory = new File(directoryPath);
        deleteDirectory(directory);
    }

    public static boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }

        // either file or an empty directory
        Logger.getLogger(Util.class.getName()).log(Level.INFO, "Deleting - " + dir.getName());
        return dir.delete();
    }

    @SneakyThrows
    static public Properties gate2ePro(String type, String name) {
        String orgName = name.substring(name.indexOf(".") + 1);
        Properties pro = new Properties();
        String path = "crypto-config/" + type + "Organizations/" + orgName + "/" + type + "s/" + name + "/tls" + "/server.crt";
        InputStream inputStream = Util.class.getClassLoader().getResourceAsStream(path);
        String substring = path.substring(path.lastIndexOf("."));
        Path tmpFilePath = Files.createTempFile(UUID.randomUUID().toString(), substring);
        log.info("tmpFilePath =======》{}", tmpFilePath);
        assert inputStream != null;
        Files.copy(inputStream, tmpFilePath, StandardCopyOption.REPLACE_EXISTING);
        pro.setProperty("pemFile", tmpFilePath.toString());
        pro.setProperty("hostnameOverride", name);
        pro.setProperty("sslProvider", "openSSL");
        pro.setProperty("negotiationType", "TLS");
        return pro;

    }

    public static void handlePageParam(JSONObject param, PageQuery pageQuery) {
        Optional.ofNullable(pageQuery.getStartTime()).ifPresent(p -> param.put("StartTime", p));
        Optional.ofNullable(pageQuery.getEndTime()).ifPresent(p -> param.put("EndTime", p));
        Optional.of(pageQuery.getPage()).ifPresent(p -> param.put("Page", String.valueOf(p)));
        Optional.of(pageQuery.getPerPage()).ifPresent(p -> param.put("PerPage", String.valueOf(p)));
    }

    @SneakyThrows
    public static <T> void handleParam(JSONObject param, T t) {
        Class<?> clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object o = field.get(t);
            String type = field.getType().getName();
            Optional.ofNullable(field.get(t)).ifPresent(p -> {
                String property = upperCaseFist(field.getName());
                if (type.equals("java.util.List")){
                    param.put(property, JSON.toJSON(o));
                } else {
                    param.put(property, o);
                }
            });
        }
    }


    private static String upperCaseFist(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.replace(0, 1, str.substring(0, 1).toUpperCase());
        return stringBuilder.toString();
    }


    public static void main(String[] args) {
        DataNodeModificationQuery query = new DataNodeModificationQuery();
        query.setDataNodeID("dataNodeId");
        JSONObject jsonObject = new JSONObject();
        handleParam(jsonObject, query);
        System.out.println("resp -> " + jsonObject.toString());


    }
}
