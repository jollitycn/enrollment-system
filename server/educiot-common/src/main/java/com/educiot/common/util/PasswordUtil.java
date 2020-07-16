package com.educiot.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2019/8/23 17:33
 */
public final class PasswordUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(PasswordUtil.class);

    /**
     * 对明文密码进行加密：先MD5加密，然后反转MD5加密之后的字符创，再对得到的字符串进行哈希加密，得到最后数据库存储密码
     *
     * @param password 明文密码
     * @return java.lang.String 数据库存储密码（最终密码）
     * @author Pan Juncai
     * @date 2019/8/23 17:47
     */
    public static String passwordEncode(String password) {
        String encodePassword = StringUtil.MD5(password);
        return sha256HexByMd5Str(encodePassword);
    }

    /**
     * 对MD5加密的字符串进行反转，再对得到的字符串进行哈希加密
     *
     * @param passwordMd5 密码MD5加密的字符串
     * @return java.lang.String
     * @author Pan Juncai
     * @date 2019/8/23 18:04
     */
    public static String sha256HexByMd5Str(String passwordMd5) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(StringUtil.reverse(passwordMd5).getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            return no.toString(16);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.info("Exception in PasswordUtil.class thrown for incorrect algorithm: ", e);
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(passwordEncode("admin"));
    }
}
