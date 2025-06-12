package com.massive.couponcore.repository.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class RedisRepository {

	private final RedisTemplate<String, String> redisTemplate;

	public Boolean zAdd(String key, String value, double score) {
		return redisTemplate.opsForZSet().addIfAbsent(key, value, score);
	}

	public Long sAdd(String key, String value) {
		return redisTemplate.opsForSet().add(key, value);
	}

	public Long sCard(String key) {
		return redisTemplate.opsForSet().size(key);
	}

	public Boolean sIsMember(String key, String value) {
		return redisTemplate.opsForSet().isMember(key, value);
	}

	public Long rPush(String key, String value) {
		return redisTemplate.opsForList().rightPush(key, value);
	}

	public String lIndex(String key, long index) {
		return redisTemplate.opsForList().index(key, index);
	}

	public String lPop(String key) {
		return redisTemplate.opsForList().leftPop(key);
	}

	public Long lSize(String key) {
		return redisTemplate.opsForList().size(key);
	}

}