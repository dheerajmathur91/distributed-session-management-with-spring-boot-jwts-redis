package com.dheeraj.distsession.util;

import com.dheeraj.distsession.constants.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.dheeraj.distsession.configuration.RedisConfiguration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisUtil {
	
	@Autowired
	private RedisConfiguration redisConfiguration;
	
	@Autowired
	private JedisPool pool;

	/**
     * Sets key with an expiry time. 
     * @param key
     * @param value
     */
    public void psetex(String key, String value) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.psetex(getRedisKey(key), redisConfiguration.getIdleTimeToInvalidateAuthToken() * 60 * 1000L, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * Deletes the provided key from redis.
     * @param key
     */
    public void del(String key) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.del(getRedisKey(key));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * Validates token by checking existence of subject in redis 
     * and equating token with the value in redis. 
     * 
     * Also, if token is valid, extend life span of token
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean validate(String key, String value) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            String redisValue = jedis.get(getRedisKey(key));
            boolean isTokenValid = (redisValue != null && redisValue.equals(value));
            if(isTokenValid){
            	jedis.psetex(getRedisKey(key), redisConfiguration.getIdleTimeToInvalidateAuthToken() * 60 * 1000L, value);
            }
            return isTokenValid;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    
    private String getRedisKey(String key){
    	return AppConstants.JWT_REDIS_PREFIX.concat(key);
    }
	
}