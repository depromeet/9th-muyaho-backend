package com.depromeet.muyaho.external.apple;

import com.depromeet.muyaho.config.exception.ValidationException;
import com.depromeet.muyaho.external.apple.dto.response.IdTokenPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class AppleTokenDecoderImpl implements AppleTokenDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public IdTokenPayload getUserInfoFromToken(String idToken) {
        String payload = idToken.split("\\.")[1];
        String decodedPayload = new String(Base64.getDecoder().decode(payload));
        try {
            return objectMapper.readValue(decodedPayload, IdTokenPayload.class);
        } catch (IOException e) {
            throw new ValidationException(String.format("잘못된 토큰 (%s) 입니다", idToken));
        }
    }

}
