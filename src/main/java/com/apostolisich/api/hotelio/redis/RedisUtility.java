package com.apostolisich.api.hotelio.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.apostolisich.api.hotelio.response.GetHotelListResponse;

@Service
public class RedisUtility {
	
	private static final int CACHE_DURATION = 4;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	public void save(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
		redisTemplate.expire(key, CACHE_DURATION, TimeUnit.HOURS);
	}
	
	public GetHotelListResponse find(String key) {
		Object result = redisTemplate.opsForValue().get(key);

		return result == null ? null : (GetHotelListResponse) result;
	}
	
	public void delete(String key) {
		redisTemplate.delete(key);
	}
	
}
