package com.dheeraj.distsession.util;

import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.dheeraj.distsession.configuration.RedisConfiguration;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	private static RedisConfiguration propertiesConfig;
	
	@Autowired
	RedisUtil redisUtil;
	
	@Autowired 
	RedisConfiguration redisConfiguration;
	
	@PostConstruct
    public void init() {
		JwtUtil.propertiesConfig = redisConfiguration;
    }

	/**
	 * Generate token for the provided subject using signingKey defined in properties
	 * If any user remains idle for more the specified time, his token will auto expire
     * and user will need to login again.
	 * @param subject 
	 * @return
	 */
    public String generateToken(String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, propertiesConfig.getSigningKey());

        String token = builder.compact();

        redisUtil.psetex(subject, token);
        
        return token;
    }

    /**
     * Validates provided token against keys present in Redis
     * @param token
     * @return
     */
    public String validateToken(String token){
        if(token == null) {
            return null;
        }

        String subject = Jwts.parser().setSigningKey(propertiesConfig.getSigningKey()).parseClaimsJws(token).getBody().getSubject();
        if (!redisUtil.validate(subject, token)) {
            return null;
        }

        return subject;
    }

    /**
     * Deletes the subject from Redis based on the provided token. Thus ending users session. 
     * Token provided will no longer be active. 
     * @param token
     */
    public void invalidateToken(String token) {
    	String subject = Jwts.parser().setSigningKey(propertiesConfig.getSigningKey()).parseClaimsJws(token).getBody().getSubject();
    	redisUtil.del((String) subject);
    }
}
