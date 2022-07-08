package cn.zeroeden.haoke.dubbo.api.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Zero
 * @Description 描述此类
 */
@Configuration
@Data
@PropertySource("classpath:mock-data.properties")
@ConfigurationProperties(prefix = "mock")
public class MockConfig {
    private String indexMenu;
    private String indexInfo;
    private String indexFaq;
    private String indexHouse;
    private String infosList1;
    private String infosList2;
    private String infosList3;
    private String my;
}
