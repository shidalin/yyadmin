/*
 * Copyright 2017-2018 the original author(https://github.com/wj596)
 *
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */
package com.yonyou.yyadmin.shiro;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.compression.DefaultCompressionCodecResolver;
import io.jsonwebtoken.lang.Assert;

/**
 * 安全加密相关工具类
 *
 * @author wangjie (https://github.com/wj596)
 * @date 2016年6月31日
 */
public abstract class CryptoUtil {

    // HMAC 加密算法名称
    public static final String HMAC_MD5 = "HmacMD5";// 128位
    public static final String HMAC_SHA1 = "HmacSHA1";// 126
    public static final String HMAC_SHA256 = "HmacSHA256";// 256
    public static final String HMAC_SHA512 = "HmacSHA512";// 512
    //JWT密钥
    private static final String JWT_SECRET_KEY = "=abcdefg1234567890";
    //签发人
    private static final String ISSURE_KEY = "yyadmin";

    public static final String PERMISSION_KEY = "user_permission";

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static CompressionCodecResolver CODECRESOLVER = new DefaultCompressionCodecResolver();

    /**
     * JWT签发令牌
     * <p>
     * //	 * @param id 令牌ID
     *
     * @param subject     用户ID
     *                    //     * @param ISSURE_KEY      签发人
     * @param period      有效时间(毫秒)
     *                    //     * @param roles       访问主张-角色
     * @param permissions 访问主张-权限
     *                    //     * @param algorithm   加密算法(SignatureAlgorithm是enum)
     * @return json web token
     */
    public static String issueJwt(String subject, Long period, Set<String> permissions) {

        // 当前时间戳(精确到毫秒)
        long currentTimeMillis = System.currentTimeMillis();
        // 秘钥
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(JWT_SECRET_KEY);
        JwtBuilder jwt = Jwts.builder();
        jwt.setId(UUID.randomUUID().toString());
        // 用户名
        jwt.setSubject(subject);
        // 签发者
        jwt.setIssuer(ISSURE_KEY);
        // 签发时间
        jwt.setIssuedAt(new Date(currentTimeMillis));
        // 有效时间
        if (null != period) {
            Date expiration = new Date(currentTimeMillis + period);
            jwt.setExpiration(expiration);
        }
        // 访问主张-角色
//        if (null != roles && !"".equals(roles)) jwt.claim("roles", roles);
        // 访问主张-权限
        if (null != permissions) jwt.claim(PERMISSION_KEY, permissions);
        jwt.compressWith(CompressionCodecs.DEFLATE);
        jwt.signWith(SignatureAlgorithm.HS256, secretKeyBytes);
        return jwt.compact();
    }


    /**
     * 失效jwt
     *
     * @return
     * @throws ExpiredJwtException,SignatureException,Exception token已过期,签名校验失败,其它错误
     */
    public static String disabledJWT(String jwt) throws ExpiredJwtException, SignatureException, Exception {
        Claims claims = parseJWT(jwt);
        return issueJwt(claims.getSubject(), 0L, null);
    }

    /**
     * 解析JWT字符串
     *
     * @param jwt
     * @return claims, 包括公告声明, 自定义声明
     * @throws ExpiredJwtException,SignatureException,Exception token已过期,签名校验失败,其它错误
     */
    public static Claims parseJWT(String jwt) throws ExpiredJwtException, SignatureException, Exception {
        // 秘钥,算法存储在header
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(JWT_SECRET_KEY);
        return Jwts.parser()
                .setSigningKey(secretKeyBytes)
                .parseClaimsJws(jwt)
                .getBody();
    }

    /**
     * 字节数组转字符串
     *
     * @param bytes 字节数组
     * @return 字符串
     */
    private static String byte2HexStr(byte[] bytes) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; bytes != null && n < bytes.length; n++) {
            stmp = Integer.toHexString(bytes[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    /**
     * 解析JWT的Payload
     */
    public static String parseJwtPayload(String jwt){
        Assert.hasText(jwt, "JWT String argument cannot be null or empty.");
        String base64UrlEncodedHeader = null;
        String base64UrlEncodedPayload = null;
        String base64UrlEncodedDigest = null;
        int delimiterCount = 0;
        StringBuilder sb = new StringBuilder(128);
        for (char c : jwt.toCharArray()) {
            if (c == '.') {
                CharSequence tokenSeq = io.jsonwebtoken.lang.Strings.clean(sb);
                String token = tokenSeq!=null?tokenSeq.toString():null;

                if (delimiterCount == 0) {
                    base64UrlEncodedHeader = token;
                } else if (delimiterCount == 1) {
                    base64UrlEncodedPayload = token;
                }

                delimiterCount++;
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        if (delimiterCount != 2) {
            String msg = "JWT strings must contain exactly 2 period characters. Found: " + delimiterCount;
            throw new MalformedJwtException(msg);
        }
        if (sb.length() > 0) {
            base64UrlEncodedDigest = sb.toString();
        }
        if (base64UrlEncodedPayload == null) {
            throw new MalformedJwtException("JWT string '" + jwt + "' is missing a body/payload.");
        }
        // =============== Header =================
        Header header = null;
        CompressionCodec compressionCodec = null;
        if (base64UrlEncodedHeader != null) {
            String origValue = TextCodec.BASE64URL.decodeToString(base64UrlEncodedHeader);
            Map<String, Object> m = readValue(origValue);
            if (base64UrlEncodedDigest != null) {
                header = new DefaultJwsHeader(m);
            } else {
                header = new DefaultHeader(m);
            }
            compressionCodec = CODECRESOLVER.resolveCompressionCodec(header);
        }
        // =============== Body =================
        String payload;
        if (compressionCodec != null) {
            byte[] decompressed = compressionCodec.decompress(TextCodec.BASE64URL.decode(base64UrlEncodedPayload));
            payload = new String(decompressed, io.jsonwebtoken.lang.Strings.UTF_8);
        } else {
            payload = TextCodec.BASE64URL.decodeToString(base64UrlEncodedPayload);
        }
        return payload;
    }

    public static Map<String, Object> readValue(String val) {
        try {
            return MAPPER.readValue(val, Map.class);
        } catch (IOException e) {
            throw new MalformedJwtException("Unable to read JSON value: " + val, e);
        }
    }
}