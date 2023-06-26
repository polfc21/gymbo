package com.behabits.gymbo.application.jwt;

import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import org.springframework.stereotype.Service;

@Service
public class JwtDecoder {

    public byte[] getKeyBytes(String secret) {
        return this.getBase64Decoder().decode(secret);
    }

    private Decoder<String, byte[]> getBase64Decoder() {
        return Decoders.BASE64;
    }

}
