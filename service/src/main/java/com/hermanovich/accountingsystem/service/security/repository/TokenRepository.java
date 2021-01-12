package com.hermanovich.accountingsystem.service.security.repository;

import com.hermanovich.accountingsystem.common.exception.BusinessException;
import com.hermanovich.accountingsystem.util.MessageForUser;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenRepository {

    private final Map<String, String> loginToAccessTokenCache = new ConcurrentHashMap<>();

    public void addToken(String login, String token) {
        loginToAccessTokenCache.put(login, token);
    }

    public String getToken(String login) {
        return loginToAccessTokenCache.get(login);
    }

    public Map<String, String> getMap() {
        return loginToAccessTokenCache;
    }

    public void removeToken(String login) {
        if (login == null || loginToAccessTokenCache.get(login) == null) {
            throw new BusinessException(MessageForUser.MESSAGE_USER_IS_NOT_AUTHORIZED.get());
        }
        loginToAccessTokenCache.remove(login);
    }
}
