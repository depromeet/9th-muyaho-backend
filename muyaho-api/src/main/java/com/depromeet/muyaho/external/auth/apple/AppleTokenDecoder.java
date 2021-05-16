package com.depromeet.muyaho.external.auth.apple;

import com.depromeet.muyaho.external.auth.apple.dto.response.IdTokenPayload;

public interface AppleTokenDecoder {

    IdTokenPayload getUserInfoFromToken(String idToken);

}
