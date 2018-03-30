package com.dheeraj.distsession.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisConfiguration {

   	private String host;

   	private Integer port;
   
   	private Long idleTimeToInvalidateAuthToken;
   
   	private String signingKey;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Long getIdleTimeToInvalidateAuthToken() {
		return idleTimeToInvalidateAuthToken;
	}

	public void setIdleTimeToInvalidateAuthToken(Long idleTimeToInvalidateAuthToken) {
		this.idleTimeToInvalidateAuthToken = idleTimeToInvalidateAuthToken;
	}

	public String getSigningKey() {
		return signingKey;
	}

	public void setSigningKey(String signingKey) {
		this.signingKey = signingKey;
	}

	@Bean
	public JedisPool getPool(){
		return new JedisPool(this.getHost(), this.getPort());
	}
}
