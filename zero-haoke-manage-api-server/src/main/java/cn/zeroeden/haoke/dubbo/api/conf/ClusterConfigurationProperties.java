package cn.zeroeden.haoke.dubbo.api.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Zero
 * @Description 描述此类
 */
@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
@Data
public class ClusterConfigurationProperties {
    private List<String> nodes;
    private Integer maxRedirects;

}