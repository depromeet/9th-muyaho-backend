package com.depromeet.muyaho.config.interceptor;

import com.depromeet.muyaho.config.session.MemberSession;
import com.depromeet.muyaho.config.session.SessionConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
public class LoginSessionComponent {

    private static final String BEARER_TOKEN = "Bearer ";

    private final SessionRepository<? extends Session> sessionRepository;

    public Long getMemberId(HttpServletRequest request) {
        return getMemberSession(request).getMemberId();
    }

    private MemberSession getMemberSession(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        validateHasAuthorizationHeader(header);
        return getSession(header);
    }

    private void validateHasAuthorizationHeader(String header) {
        if (header == null) {
            throw new IllegalArgumentException("세션이 존재하지 않습니다");
        }
        if (!header.startsWith(BEARER_TOKEN)) {
            throw new IllegalArgumentException(String.format("잘못된 세션 (%s) 입니다", header));
        }
    }

    private MemberSession getSession(String header) {
        Session session = sessionRepository.getSession(header.split(BEARER_TOKEN)[1]);
        if (session == null) {
            throw new IllegalArgumentException(String.format("잘못된 세션 (%s) 입니다", header));
        }
        return session.getAttribute(SessionConstants.AUTH_SESSION);
    }

}