package com.depromeet.muyaho.domain.sample.repository;

import com.depromeet.muyaho.domain.sample.Sample;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.depromeet.muyaho.domain.sample.QSample.sample;

@RequiredArgsConstructor
public class SampleRepositoryCustomImpl implements SampleRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Sample findSampleById(Long id) {
		return queryFactory.selectFrom(sample)
				.where(
						sample.id.eq(id)
				).fetchOne();
	}

}
