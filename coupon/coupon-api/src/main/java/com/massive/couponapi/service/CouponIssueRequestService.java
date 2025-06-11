package com.massive.couponapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.massive.couponapi.dto.CouponIssueRequestDto;
import com.massive.couponcore.service.CouponIssueService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CouponIssueRequestService {

	private final CouponIssueService couponIssueService;

	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

	public void issueRequestV1(CouponIssueRequestDto requestDto) {
		couponIssueService.issue(requestDto.couponId(), requestDto.userId());
		log.info("쿠폰 발급 완료. couponId: %s, userId: %s".formatted(requestDto.couponId(), requestDto.userId()));
	}

}
