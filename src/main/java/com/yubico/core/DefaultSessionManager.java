package com.yubico.core;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.yubico.webauthn.data.ByteArray;
import lombok.NonNull;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class DefaultSessionManager implements SessionManager {
    private Cache<ByteArray, ByteArray> sessionIdsToUsers = newCache();

    private Cache<ByteArray, ByteArray> usersToSessionIds = newCache();

    private static <K, V> Cache<K, V> newCache() {
        return CacheBuilder.newBuilder()
            .maximumSize(100L)
            .expireAfterAccess(5L, TimeUnit.MINUTES)
            .build();
    }

    @Override
    public ByteArray createSession(@NonNull ByteArray userHandle) throws ExecutionException {
        if (userHandle == null) {
            throw new NullPointerException("userHandle is marked non-null but is null");
        }
        ByteArray sessionId = this.usersToSessionIds.get(userHandle, () -> SessionManager.generateRandom(32));
        this.sessionIdsToUsers.put(sessionId, userHandle);
        return sessionId;
    }

    @Override
    public Optional<ByteArray> getSession(@NonNull ByteArray token) {
        if (token == null) {
            throw new NullPointerException("token is marked non-null but is null");
        }
        return Optional.ofNullable(this.sessionIdsToUsers.getIfPresent(token));
    }

}
