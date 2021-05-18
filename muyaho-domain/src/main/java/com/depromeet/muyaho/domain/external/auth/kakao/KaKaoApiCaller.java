package com.depromeet.muyaho.domain.external.auth.kakao;

import com.depromeet.muyaho.domain.external.auth.kakao.dto.response.KaKaoUserInfoResponse;

public interface KaKaoApiCaller {

    KaKaoUserInfoResponse getKaKaoUserProfileInfo(String accessToken);

}
