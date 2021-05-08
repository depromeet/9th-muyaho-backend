package com.depromeet.muyaho.external.kakao;

import com.depromeet.muyaho.external.kakao.dto.response.KaKaoUserInfoResponse;

public interface KaKaoApiCaller {

    KaKaoUserInfoResponse getKaKaoUserProfileInfo(String accessToken);

}
