package com.ozf.laiyw.manage.app.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expire}")
    private long expire;
    @Value("${jwt.header}")
    private String header;

    public String getHeader() {
        return header;
    }

    public JwtUtils() {
    }

    public JwtUtils(String secret, long expire, String header) {
        this.secret = secret;
        this.expire = expire;
        this.header = header;
    }

    /**
     * 生成Token
     *
     * @param userCredentials
     * @return
     * @throws Exception
     */
    public String createToken(String userCredentials) throws Exception {
        // 签发时间
        Date issuedDate = new Date();
        // 过期时间
        Date expiresDate = new Date(issuedDate.getTime() + expire * 1000);
        //Header
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", "JWT");
        map.put("alg", "HS256");

        JWTCreator.Builder builder = JWT.create()
                .withHeader(map)
                .withIssuedAt(issuedDate)
                .withExpiresAt(expiresDate);
        //写入用户ID
        if (StringUtils.isNotEmpty(userCredentials)) {
            builder.withSubject(userCredentials);
        }
        return builder.sign(Algorithm.HMAC256(secret));
    }

    /**
     * 校验Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public DecodedJWT verifyToken(String token) throws Exception {
        if (StringUtils.isEmpty(token))
            return null;
        try {
            return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public boolean isTokenExpired(Date expiresDate) {
        return expiresDate.before(new Date());
    }

}
