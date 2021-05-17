package com.depromeet.muyaho.config.interceptor;

import com.depromeet.muyaho.config.session.SessionConstants;
import com.depromeet.muyaho.exception.UnAuthorizedException;
import com.depromeet.muyaho.config.session.MemberSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        Session session = getSession(header);
        return session.getAttribute(SessionConstants.AUTH_SESSION);
    }

    private Session getSession(String header) {
        if (!(StringUtils.hasText(header) && header.startsWith(BEARER_TOKEN))) {
            throw new UnAuthorizedException(String.format("잘못된 세션 (%s) 입니다", header));
        }
        Session session = sessionRepository.getSession(header.split(BEARER_TOKEN)[1]);
        if (session == null) {
            throw new UnAuthorizedException(String.format("잘못된 세션 (%s) 입니다", header));
        }
        return session;
    }

}
