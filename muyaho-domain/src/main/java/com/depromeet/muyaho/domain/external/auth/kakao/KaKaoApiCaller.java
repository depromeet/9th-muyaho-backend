package com.depromeet.muyaho.domain.external.auth.kakao;

import com.depromeet.muyaho.domain.external.auth.kakao.dto.response.KaKaoProfileResponse;

public interface KaKaoApiCaller {

    KaKaoProfileResponse getProfileInfo(String accessToken);

}
