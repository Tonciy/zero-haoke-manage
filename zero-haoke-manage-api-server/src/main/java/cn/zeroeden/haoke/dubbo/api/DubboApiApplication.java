package cn.zeroeden.haoke.dubbo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
public class DubboApiApplication {

    public static void main(String[] args) {
//      解决 Netty 依赖冲突问题(Redis 和 ElasticSearch)
        System.setProperty("es.set.netty.runtime.available.processors","false");
        SpringApplication.run(DubboApiApplication.class, args);
    }
}