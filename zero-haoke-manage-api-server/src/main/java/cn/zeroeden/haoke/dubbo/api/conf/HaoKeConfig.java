package cn.zeroeden.haoke.dubbo.api.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Zero
 * @Description 描述此类
 */
@Configuration
public class HaoKeConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
