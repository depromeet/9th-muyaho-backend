package com.depromeet.muyaho.external.client.auth.kakao;

import com.depromeet.muyaho.external.client.auth.kakao.dto.response.KaKaoProfileResponse;
import com.depromeet.muyaho.external.client.auth.kakao.dto.response.KaKaoSignOutResponse;

public interface KaKaoApiCaller {

    KaKaoProfileResponse getProfileInfo(String accessToken);

    KaKaoSignOutResponse signOut(String memberUid);

}
