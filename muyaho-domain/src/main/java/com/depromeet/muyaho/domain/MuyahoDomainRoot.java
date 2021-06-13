package com.depromeet.muyaho.domain;

import com.depromeet.muyaho.external.MuyahoExternalRoot;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = {
    MuyahoDomainRoot.class,
    MuyahoExternalRoot.class
})
public interface MuyahoDomainRoot {
}
