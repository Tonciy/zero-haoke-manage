package cn.zeroeden.haoke.dubbo.api.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisClusterConfig {
    @Autowired
    private ClusterConfigurationProperties clusterProperties;

    @Bean
    public RedisConnectionFactory redisConnectionfactory() {
        RedisClusterConfiguration configuration = new
                RedisClusterConfiguration(clusterProperties.getNodes());
        configuration.setMaxRedirects(clusterProperties.getMaxRedirects());
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionfactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionfactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}