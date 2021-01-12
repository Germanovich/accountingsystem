package com.hermanovich.accountingsystem.service.scheduler;

import com.hermanovich.accountingsystem.service.security.repository.TokenRepository;
import com.hermanovich.accountingsystem.util.MessageForUser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Component
public class TokenCleanerScheduler {

    @Value("${security.key}")
    private String key;
    private final TokenRepository tokenRepository;

    @Scheduled(cron = "0 0 0/2 * * *")
    public void clearTokens() {
        log.info("Token cleaning started");
        for (Map.Entry<String, String> entry : tokenRepository.getMap().entrySet()) {
            if (Boolean.FALSE.equals(validateAccessToken(entry.getValue()))) {
                tokenRepository.removeToken(entry.getKey());
            }
        }
        log.info("Token cleanup finished");
    }

    private Boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.warn(MessageForUser.MESSAGE_INVALID_TOKEN.get(), e);
            return Boolean.FALSE;
        }
    }
}
