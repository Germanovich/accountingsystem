package com.hermanovich.accountingsystem.controller.security.util;

import com.hermanovich.accountingsystem.util.MessageForUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Log4j2
@Component
public class TokenHelper {

    @Value("${security.key}")
    private String key;

    public String getLoginFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String generateToken(String login) {
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(Date.from(LocalDate.now()
                        .plusDays(15)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public Boolean validateToken(String token) {
        if (token == null || token.isEmpty()) {
            return Boolean.FALSE;
        }
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.warn(MessageForUser.MESSAGE_INVALID_TOKEN.get(), e);
            return Boolean.FALSE;
        }
    }
}
