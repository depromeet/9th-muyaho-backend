package com.depromeet.muyaho.domain.config.jpa;

import com.depromeet.muyaho.domain.MuyahoDomainRoot;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = {MuyahoDomainRoot.class})
@EnableJpaRepositories(basePackageClasses = {MuyahoDomainRoot.class})
@EnableJpaAuditing
public class JpaConfig {

}
