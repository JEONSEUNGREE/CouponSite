package com.massive.couponcore.service;

import static com.massive.couponcore.exception.ErrorCode.*;
import static com.massive.couponcore.util.CouponRedisUtils.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.massive.couponcore.component.DistributeLockExecutor;
import com.massive.couponcore.exception.CouponIssueException;
import com.massive.couponcore.repository.redis.RedisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.massive.couponcore.repository.redis.dto.CouponIssueRequest;
import com.massive.couponcore.repository.redis.dto.CouponRedisEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AsyncCouponIssueServiceV1 {

	private final RedisRepository redisRepository;
	private final DistributeLockExecutor distributeLockExecutor;
	private final CouponCacheService couponCacheService;
	private final CouponIssueRedisService couponIssueRedisService;
	private final ObjectMapper objectMapper = new ObjectMapper();

	// public void issue(long couponId, long userId) {
	// 	String key = "issue:request:sortedset:couponId=%s".formatted(couponId);
	// 	redisRepository.zAdd(key, String.valueOf(userId), System.currentTimeMillis());
	// }


	public void issue(long couponId, long userId) {
		CouponRedisEntity coupon = couponCacheService.getCouponCache(couponId);
		coupon.checkIssuableCoupon();
		distributeLockExecutor.execute("lock_%s".formatted(couponId), 3000, 3000, () -> {
			couponIssueRedisService.checkCouponIssueQuantity(coupon, userId);
			issueRequest(couponId, userId);
		});
	}

	private void issueRequest(long couponId, long userId) {
		CouponIssueRequest issueRequest = new CouponIssueRequest(couponId, userId);
		try {
			String value = objectMapper.writeValueAsString(issueRequest);
			redisRepository.sAdd(getIssueRequestKey(couponId), String.valueOf(userId));
			redisRepository.rPush(getIssueRequestQueueKey(), value);
		} catch (JsonProcessingException e) {
			throw new CouponIssueException(FAIL_COUPON_ISSUE_REQUEST, "input: %s".formatted(issueRequest));
		}
	}

}