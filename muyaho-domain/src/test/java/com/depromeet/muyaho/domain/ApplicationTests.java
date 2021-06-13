package com.depromeet.muyaho.domain;

import com.depromeet.muyaho.external.MuyahoExternalRoot;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {
    MuyahoExternalRoot.class,
    MuyahoDomainRoot.class
})
public class ApplicationTests {

}
