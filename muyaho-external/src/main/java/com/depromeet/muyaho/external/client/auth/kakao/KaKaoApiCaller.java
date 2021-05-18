package com.depromeet.muyaho.external.client.auth.kakao;

import com.depromeet.muyaho.external.client.auth.kakao.dto.response.KaKaoUserInfoResponse;

public interface KaKaoApiCaller {

    KaKaoUserInfoResponse getKaKaoUserProfileInfo(String accessToken);

}
