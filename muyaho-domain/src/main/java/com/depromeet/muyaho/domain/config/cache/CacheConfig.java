package com.depromeet.muyaho.domain.config.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.Objects;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(Objects.requireNonNull(ehCacheCacheManager().getObject()));
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean ehCacheManageFactory = new EhCacheManagerFactoryBean();
        ehCacheManageFactory.setConfigLocation(new ClassPathResource("ehcache.xml"));
        ehCacheManageFactory.setShared(true);
        return ehCacheManageFactory;
    }

}
