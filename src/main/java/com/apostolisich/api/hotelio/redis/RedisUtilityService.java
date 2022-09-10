package com.apostolisich.api.hotelio.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.apostolisich.api.hotelio.response.GetHotelListResponse;

@Service
public class RedisUtilityService {
	
	private static final int CACHE_HOURS_DURATION = 4;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	public void save(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
		redisTemplate.expire(key, CACHE_HOURS_DURATION, TimeUnit.HOURS);
	}
	
	public GetHotelListResponse findHotelListResponseByKey(String key) {
		if(redisTemplate.hasKey(key)) {
			return (GetHotelListResponse) redisTemplate.opsForValue().get(key);
		}

		return null;
	}
	
	public void delete(String key) {
		redisTemplate.delete(key);
	}
	
}
