package com.my.demo.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

/**
 * @author
 * Created on 2024-12-02
 */
@Slf4j
public class JwtDemo {

    public static void main(String[] args) {
        Date expiredAt = new Date(System.currentTimeMillis() + 1800 * 1000); // 有效时间，此处示例代表当前时间+1800s(30min)
        Date notBefore = new Date(System.currentTimeMillis() - 50 * 1000); //开始生效的时间，此处示例代表当前时间-5秒
        String ak = "akakakak";
        String sk = "sksksksk";
        Algorithm algo = Algorithm.HMAC256(sk);
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        String sign = JWT.create().
                withClaim("zhang","guozhao")
                .withIssuer(ak)
                .withHeader(header).withExpiresAt(expiredAt).withNotBefore(notBefore)
                .sign(algo);

        log.info("sign: {}",sign);

        log.info("--------------------");

        DecodedJWT decode = JWT.decode(sign);
        String token = decode.getToken();
        log.info("token: {}",token);


        String header2 = decode.getHeader();
        log.info("header: {}",header2);
        byte[] decode1 = Base64.getUrlDecoder().decode(header2);
        log.info("header decoder:{}", new String(decode1, StandardCharsets.UTF_8));

        String payload = decode.getPayload();
        log.info("payload: {}",payload);

        byte[] payload1 = Base64.getUrlDecoder().decode(payload);
        log.info("payload decoder", payload1);

        String signature = decode.getSignature();
        log.info("signature: {}",signature);


        Date expiresAt = decode.getExpiresAt();
        log.info("expiresAt: {}", expiresAt.getTime()>System.currentTimeMillis());
        List<String> audience = decode.getAudience();
        log.info("audience: {}", audience);
        String algorithm = decode.getAlgorithm();
        log.info("algorithm: {}", algorithm);
        Map<String, Claim> claims = decode.getClaims();
        log.info("claims: {}", claims);

    }

}
