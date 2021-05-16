package com.depromeet.muyaho.external.auth.kakao;

import com.depromeet.muyaho.external.auth.kakao.dto.response.KaKaoUserInfoResponse;

public interface KaKaoApiCaller {

    KaKaoUserInfoResponse getKaKaoUserProfileInfo(String accessToken);

}
