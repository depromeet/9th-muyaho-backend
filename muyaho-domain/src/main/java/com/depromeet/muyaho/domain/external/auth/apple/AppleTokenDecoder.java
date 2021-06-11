package com.depromeet.muyaho.domain.external.auth.apple;

import com.depromeet.muyaho.domain.external.auth.apple.dto.response.IdTokenPayload;

public interface AppleTokenDecoder {

    IdTokenPayload getUserInfoFromToken(String idToken);

}
