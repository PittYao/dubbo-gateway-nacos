package com.fanyao.web.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fanyao.common.core.util.JWTUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class AuthApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void testJwt() throws Exception {
        //密钥
        //创建map
        Map<String, String> map = new HashMap<>();
        map.put("username", "rayfoo");
        //颁发token
        String token = JWTUtil.getToken(map);
        System.out.println(token);

        //解析token
        DecodedJWT verify = JWTUtil.verify(token);
        System.out.println(verify.getClaim("username").asString());
    }

}
