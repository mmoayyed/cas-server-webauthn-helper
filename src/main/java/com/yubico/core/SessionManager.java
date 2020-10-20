package com.yubico.core;

import com.yubico.webauthn.data.ByteArray;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import lombok.NonNull;

public interface SessionManager {
    ByteArray createSession(@NonNull ByteArray paramByteArray) throws ExecutionException;

    Optional<ByteArray> getSession(@NonNull ByteArray paramByteArray);

    boolean isSessionForUser(@NonNull ByteArray paramByteArray1, @NonNull ByteArray paramByteArray2);

    boolean isSessionForUser(@NonNull ByteArray paramByteArray, @NonNull Optional<ByteArray> paramOptional);

    static ByteArray generateRandom(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return new ByteArray(bytes);
    }
}

