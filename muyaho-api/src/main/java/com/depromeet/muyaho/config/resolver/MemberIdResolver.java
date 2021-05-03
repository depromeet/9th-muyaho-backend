package com.depromeet.muyaho.config.resolver;

import com.depromeet.muyaho.config.interceptor.Auth;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class MemberIdResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.getParameterAnnotation(MemberId.class) != null;
        boolean isMatchType = parameter.getParameterType().equals(Long.class);
        if (hasAnnotation && parameter.getMethodAnnotation(Auth.class) == null) {
            throw new IllegalArgumentException("인증이 필요한 컨트롤러 입니다. Auth 어노테이션을 붙여주세요.");
        }
        return hasAnnotation && isMatchType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return webRequest.getAttribute("memberId", 0);
    }

}
