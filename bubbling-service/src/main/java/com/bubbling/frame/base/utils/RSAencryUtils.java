package com.bubbling.frame.base.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @title: rsa数据加密
 * @Author yangwen
 * @Date: 2021-04-10 9:31
 * @ignore
 */
public class RSAencryUtils {
    public static ConcurrentHashMap<String, String> rsaMap=new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, Long> rsaTimeOutMap=new ConcurrentHashMap<>();
    public static void main(String[] args) {
        //生成公钥和私钥
        String[] strings = genKeyPair();
        //加密字符串
        String message = "df723820";
        System.out.println("随机生成的公钥为:" + strings[0]);
        System.out.println("随机生成的私钥为:" + strings[1]);
        String messageEn = encrypt(message, strings[0]);
        System.out.println("加密后的字符串为:" + messageEn);
        String messageDe = decrypt(messageEn, strings[1]);
        System.out.println("还原后的字符串为:" + messageDe);
    }

    //随机生成密钥对
    public static String[] genKeyPair() {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = null;

        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 初始化密钥对生成器，密钥大小为96-1024位
        assert keyPairGen != null;
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 返回公钥和私钥
        String[] rtn=new String[2];
        rtn[0]=publicKeyString;//0表示公钥
        rtn[1]=privateKeyString;//1表示私钥
        return rtn;
    }

    /** RSA公钥加密
     * @param str  加密字符串
     * @param publicKey  公钥
     * @return  密文
     */
    public static String encrypt(String str, String publicKey) {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = null;
        String outStr = null;

        try {
            pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        } catch (InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //RSA加密
        return outStr;
    }

    /**  RSA私钥解密
     * @param str   加密字符串
     * @param privateKey  私钥
     * @return  铭文
     */
    public static String decrypt(String str, String privateKey) {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = null;
        //RSA解密
        Cipher cipher = null;
        String outStr = null;

        try {
            priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            outStr = new String(cipher.doFinal(inputByte));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return outStr;
    }
}
