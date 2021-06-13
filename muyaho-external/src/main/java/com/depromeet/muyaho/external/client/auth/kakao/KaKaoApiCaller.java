package com.depromeet.muyaho.external.client.auth.kakao;

import com.depromeet.muyaho.external.client.auth.kakao.dto.response.KaKaoProfileResponse;

public interface KaKaoApiCaller {

    KaKaoProfileResponse getProfileInfo(String accessToken);

}
