package com.depromeet.muyaho.external.client.auth.apple;

import com.depromeet.muyaho.external.client.auth.apple.dto.response.IdTokenPayload;

public interface AppleTokenDecoder {

    IdTokenPayload getUserInfoFromToken(String idToken);

}
