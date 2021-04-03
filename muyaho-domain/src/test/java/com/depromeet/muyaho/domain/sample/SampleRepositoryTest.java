package com.depromeet.muyaho.domain.sample;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SampleRepositoryTest {

	@Autowired
	private SampleRepository sampleRepository;

	@AfterEach
	void cleanUp() {
		sampleRepository.deleteAll();
	}

	@Test
	void findSampleById() {
		// given
		String name = "무야호";
		Sample sample = sampleRepository.save(Sample.of(name));

		// when
		Sample findSample = sampleRepository.findSampleById(sample.getId());

		// then
		assertThat(findSample.getName()).isEqualTo(name);
	}

}
