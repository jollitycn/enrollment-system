package com.educiot.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.log4j.Log4j2;

import java.util.Date;

/**
 * @author liuhao
 * @date Created in 2020/3/2
 */
@Log4j2
public class JWTUtil {

    /*
     * 生成签名的时候使用的秘钥 secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。
     * 它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发 jwt 了。
     * 该值根据具体情况可改，此处写死只是临时举例用。
     */
    private static final String SECRET_KEY = "123456";

    /*
     * 默认过期时间： 24 小时
     */
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;
//    private static final long EXPIRE_TIME = 60 * 1000;

    /**
     * 用户登录成功后使用 HS256 算法生成 token，ttlMillis 秒后
     * 在 token 中存入用户登录的登录名 LoginUserName
     */
    public static String createToken(Long userId) {

        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        return JWT.create()
                .withClaim("userId", userId) // 附带 userId 信息
                .withExpiresAt(date)                   // 到期时间
                .sign(algorithm);                      // 创建一个新的 JWT，并使用给定的算法进行标记
    }

    /**
     * 校验 token 是否正确
     */
    public static boolean verify(String token, Long userId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            //在token中附带了userId信息
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userId", userId)
                    .build();
            // 验证 token
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }


    public static Long getUserID(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}