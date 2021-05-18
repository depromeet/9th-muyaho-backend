package com.depromeet.muyaho.api.config.session;

import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@EnableJdbcHttpSession(maxInactiveIntervalInSeconds = 60 * 60 * 24 * 30)
public class HttpSessionConfig {

}
