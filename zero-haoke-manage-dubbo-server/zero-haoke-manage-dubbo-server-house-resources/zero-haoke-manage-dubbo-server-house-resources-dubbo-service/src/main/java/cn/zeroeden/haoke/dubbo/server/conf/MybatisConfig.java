package cn.zeroeden.haoke.dubbo.server.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("cn.zeroeden.haoke.dubbo.server.mapper")
@Configuration
public class MybatisConfig {
}