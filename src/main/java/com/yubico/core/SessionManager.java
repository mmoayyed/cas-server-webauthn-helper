package com.yubico.core;

import com.yubico.webauthn.data.ByteArray;
import lombok.NonNull;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface SessionManager {
    static ByteArray generateRandom(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return new ByteArray(bytes);
    }

    ByteArray createSession(@NonNull ByteArray paramByteArray) throws ExecutionException;

    Optional<ByteArray> getSession(@NonNull ByteArray paramByteArray);

    default boolean isSessionForUser(@NonNull ByteArray claimedUserHandle, @NonNull ByteArray token) {
        if (claimedUserHandle == null) {
            throw new NullPointerException("claimedUserHandle is marked non-null but is null");
        }
        if (token == null) {
            throw new NullPointerException("token is marked non-null but is null");
        }
        Objects.requireNonNull(claimedUserHandle);
        return getSession(token).map(claimedUserHandle::equals).orElse(Boolean.valueOf(false)).booleanValue();
    }

    default boolean isSessionForUser(@NonNull ByteArray claimedUserHandle, @NonNull Optional<ByteArray> token) {
        if (claimedUserHandle == null) {
            throw new NullPointerException("claimedUserHandle is marked non-null but is null");
        }
        if (token == null) {
            throw new NullPointerException("token is marked non-null but is null");
        }
        return token.map(t -> Boolean.valueOf(isSessionForUser(claimedUserHandle, t)))
            .orElse(Boolean.valueOf(false)).booleanValue();
    }
}

