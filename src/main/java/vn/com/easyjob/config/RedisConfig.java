package vn.com.easyjob.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

//        // Sử dụng StringRedisSerializer để serialize key
//        template.setKeySerializer(new StringRedisSerializer());
//
//        // Sử dụng GenericJackson2JsonRedisSerializer để serialize giá trị (value)
//        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // Serializer cho key (sử dụng String cho key)
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // Serializer cho value của opsForValue (sử dụng JSON cho value)
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // Serializer cho value của Hash (sử dụng JSON cho giá trị Hash)
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        // Các thiết lập khác nếu cần
        template.afterPropertiesSet(); // Khởi tạo sau khi cấu hình xong

        return template;
    }
}