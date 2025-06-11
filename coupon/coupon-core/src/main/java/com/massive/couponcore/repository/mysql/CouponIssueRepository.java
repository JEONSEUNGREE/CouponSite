package com.massive.couponcore.repository.mysql;

import static com.massive.couponcore.model.QCouponIssue.*;

import org.springframework.stereotype.Repository;

import com.massive.couponcore.model.CouponIssue;
import com.querydsl.jpa.JPQLQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CouponIssueRepository {

	private final JPQLQueryFactory queryFactory;

	public CouponIssue findFirstCouponIssue(long couponId, long userId) {
		return queryFactory.selectFrom(couponIssue)
			.where(couponIssue.couponId.eq(couponId))
			.where(couponIssue.userId.eq(userId))
			.fetchFirst();
	}
}
