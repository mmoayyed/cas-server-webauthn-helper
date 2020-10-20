package com.yubico.core;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.yubico.webauthn.data.ByteArray;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import lombok.NonNull;

public class DefaultSessionManager implements SessionManager {
    private final Cache<ByteArray, ByteArray> sessionIdsToUsers = newCache();

    private final Cache<ByteArray, ByteArray> usersToSessionIds = newCache();

    private static <K, V> Cache<K, V> newCache() {
        return CacheBuilder.newBuilder()
            .maximumSize(100L)
            .expireAfterAccess(5L, TimeUnit.MINUTES)
            .build();
    }

    public ByteArray createSession(@NonNull ByteArray userHandle) throws ExecutionException {
        if (userHandle == null)
            throw new NullPointerException("userHandle is marked non-null but is null");
        ByteArray sessionId = (ByteArray)this.usersToSessionIds.get(userHandle, () -> SessionManager.generateRandom(32));
        this.sessionIdsToUsers.put(sessionId, userHandle);
        return sessionId;
    }

    public Optional<ByteArray> getSession(@NonNull ByteArray token) {
        if (token == null)
            throw new NullPointerException("token is marked non-null but is null");
        return Optional.ofNullable((ByteArray)this.sessionIdsToUsers.getIfPresent(token));
    }

    public boolean isSessionForUser(@NonNull ByteArray claimedUserHandle, @NonNull ByteArray token) {
        if (claimedUserHandle == null)
            throw new NullPointerException("claimedUserHandle is marked non-null but is null");
        if (token == null)
            throw new NullPointerException("token is marked non-null but is null");
        Objects.requireNonNull(claimedUserHandle);
        return ((Boolean)getSession(token).<Boolean>map(claimedUserHandle::equals).orElse(Boolean.valueOf(false))).booleanValue();
    }

    public boolean isSessionForUser(@NonNull ByteArray claimedUserHandle, @NonNull Optional<ByteArray> token) {
        if (claimedUserHandle == null)
            throw new NullPointerException("claimedUserHandle is marked non-null but is null");
        if (token == null)
            throw new NullPointerException("token is marked non-null but is null");
        return ((Boolean)token.<Boolean>map(t -> Boolean.valueOf(isSessionForUser(claimedUserHandle, t))).orElse(Boolean.valueOf(false))).booleanValue();
    }
}
