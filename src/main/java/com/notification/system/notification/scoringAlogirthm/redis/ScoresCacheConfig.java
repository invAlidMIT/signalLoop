package com.notification.system.notification.scoringAlogirthm.redis;

import com.notification.system.notification.scoringAlogirthm.dto.ScoresConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class ScoresCacheConfig {

    private final RedisTemplate<String,Object> redisTemplate;

    @Value("${app.redis.key.scores_config}")
    private String SCORES_CONFIG;

    public ScoresConfig getScoresConfig(){
        return (ScoresConfig)
                redisTemplate.opsForValue().get(SCORES_CONFIG);
    }

    public void setScoresConfig(ScoresConfig scoresConfig){
        redisTemplate.opsForValue().set(SCORES_CONFIG,
                scoresConfig, Duration.ofHours(1));
    }

}
