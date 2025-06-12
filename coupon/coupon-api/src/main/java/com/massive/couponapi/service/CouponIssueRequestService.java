package com.massive.couponapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.massive.couponapi.dto.CouponIssueRequestDto;
import com.massive.couponcore.component.DistributeLockExecutor;
import com.massive.couponcore.service.AsyncCouponIssueServiceV1;
import com.massive.couponcore.service.CouponIssueService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CouponIssueRequestService {

	private final CouponIssueService couponIssueService;

	private final DistributeLockExecutor distributeLockExecutor;

	private final AsyncCouponIssueServiceV1 asyncCouponIssueServiceV1;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

	public void issueRequestV1(CouponIssueRequestDto requestDto) {
		couponIssueService.issue(requestDto.couponId(), requestDto.userId());
		log.info("쿠폰 발급 완료. couponId: %s, userId: %s".formatted(requestDto.couponId(), requestDto.userId()));
	}

	public void issueRequestV2(CouponIssueRequestDto requestDto) {
		distributeLockExecutor.execute("lock_" + requestDto.couponId(), 10000, 10000,
			() -> couponIssueService.issue(requestDto.couponId(), requestDto.userId()));

		log.info("쿠폰 발급 완료. couponId: %s, userId: %s".formatted(requestDto.couponId(), requestDto.userId()));
	}


	public void asyncIssueRequestV1(CouponIssueRequestDto requestDto) {
		asyncCouponIssueServiceV1.issue(requestDto.couponId(), requestDto.userId());
	}

}
