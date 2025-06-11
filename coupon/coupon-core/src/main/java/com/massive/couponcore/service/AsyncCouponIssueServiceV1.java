package com.massive.couponcore.service;

import com.massive.couponcore.component.DistributeLockExecutor;
import com.massive.couponcore.repository.redis.RedisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AsyncCouponIssueServiceV1 {

	private final RedisRepository redisRepository;
	private final DistributeLockExecutor distributeLockExecutor;
	private final ObjectMapper objectMapper = new ObjectMapper();

	public void issue(long couponId, long userId) {
		String key = "issue:request:sortedset:couponId=%s".formatted(couponId);
		redisRepository.zAdd(key, String.valueOf(userId), System.currentTimeMillis());
	}

}