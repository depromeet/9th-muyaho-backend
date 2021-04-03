package com.depromeet.muyaho.domain.sample;

import com.depromeet.muyaho.domain.sample.repository.SampleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample, Long>, SampleRepositoryCustom {

}
