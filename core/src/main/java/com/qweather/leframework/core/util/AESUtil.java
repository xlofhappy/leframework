package com.qweather.leframework.core.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * AES 加解密工具
 * Created by xiaole on 16/8/10
 *
 * @author xiaole
 */
public class AESUtil {

    /**
     * 加密
     *
     * @param sSrc 源数据
     * @param sKey 加密 key
     *
     * @return String
     */
    public static String encrypt(String sSrc, String sKey) {
        try {
            if ( sKey == null ) {
                return null;
            }
            // 判断Key是否为16位
            if ( sKey.length() != 16 ) {
                return null;
            }
            byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
            //"算法/模式/补码方式"
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher        cipher   = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));
            //此处使用BASE64做转码功能，同时能起到2次加密的作用。
            return new String(java.util.Base64.getEncoder().encode(encrypted), StandardCharsets.UTF_8);
        } catch ( Exception e ) {
            return null;
        }
    }

    /**
     * 解密
     *
     * @param sSrc 加密后的字符串
     * @param sKey 加密的 key
     *
     * @return 加密前的字符串
     */
    public static String decrypt(String sSrc, String sKey) {
        try {
            // 判断Key是否正确
            // 判断Key是否为16位
            if ( sKey == null || sKey.length() != 16 ) {
                return "";
            }
            byte[]        raw      = sKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher        cipher   = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            //先用base64解密
            byte[] encrypted1 = java.util.Base64.getDecoder().decode(sSrc.getBytes(StandardCharsets.UTF_8));
            try {
                byte[] original = cipher.doFinal(encrypted1);
                return new String(original, StandardCharsets.UTF_8);
            } catch ( Exception e ) {
                return null;
            }
        } catch ( Exception ignored ) {}
        return "";
    }

}
