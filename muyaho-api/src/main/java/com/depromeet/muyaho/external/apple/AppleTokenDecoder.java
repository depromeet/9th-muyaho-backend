package com.depromeet.muyaho.external.apple;

import com.depromeet.muyaho.external.apple.dto.response.IdTokenPayload;

public interface AppleTokenDecoder {

    IdTokenPayload getUserInfoFromToken(String idToken);

}
