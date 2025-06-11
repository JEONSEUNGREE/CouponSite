package com.massive.couponcore.service;

import static com.massive.couponcore.exception.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.massive.couponcore.exception.CouponIssueException;
import com.massive.couponcore.model.Coupon;
import com.massive.couponcore.model.CouponIssue;
import com.massive.couponcore.repository.mysql.CouponIssueJpaRepository;
import com.massive.couponcore.repository.mysql.CouponIssueRepository;
import com.massive.couponcore.repository.mysql.CouponJpaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CouponIssueService {

	private final CouponJpaRepository couponJpaRepository;

	private final CouponIssueJpaRepository couponIssueJpaRepository;

	private final CouponIssueRepository couponIssueRepository;

	@Transactional
	public void issue(long couponId, long userId) {
		// 단일 어플리케이션 가정하여 사용할때
		// 트랜잭션 시작 aop
		// 락 획득
		// synchronized (this) {
		// 			Coupon coupon = findCoupon(couponId);
		// 			coupon.issue();
		// 			saveCouponIssue(couponId, userId);
		// 		}
		// 락 반납
		// 커밋
		// 트랜잭션 끝
		// 1번 요청이 락을 반납하고 트랜잭션을 커밋하기때문에 그전에 요청이 온 2번째가 커밋전 데이터를 읽어버림
		// 따라서 락을 거는 위치가 중요하다. 트랜잭션 시작전에 락을 거는 순서로 가야한다.
		// 따라서 상위 메서드에 락을 걸어야한다.
		synchronized (this) {
			Coupon coupon = findCoupon(couponId);
			coupon.issue();
			saveCouponIssue(couponId, userId);
		}
	}

	@Transactional(readOnly = true)
	public Coupon findCoupon(long couponId) {
		return couponJpaRepository.findById(couponId).orElseThrow(() -> {
			throw new CouponIssueException(COUPON_NOT_EXIST, "쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId));
		});
	}

	@Transactional
	public CouponIssue saveCouponIssue(long couponId, long userId) {
		checkAlreadyIssue(couponId, userId);
		CouponIssue issue = CouponIssue.builder()
			.couponId(couponId)
			.userId(userId)
			.build();
		return couponIssueJpaRepository.save(issue);
	}

	private void checkAlreadyIssue(long couponId, long userId) {
		CouponIssue issue = couponIssueRepository.findFirstCouponIssue(couponId, userId);
		if (issue != null) {
			throw new CouponIssueException(DUPLICATED_COUPON_ISSUE,
				"이미 발글된 쿠폰입니다. user_id: %s, coupon_id: %s".formatted(userId, couponId));
		}
	}
}
