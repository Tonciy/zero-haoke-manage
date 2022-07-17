package cn.zeroeden.haoke.dubbo.api.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zero
 * @Description 描述此类
 */
@RestController
@RequestMapping("wc")
public class WeXinController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("login")
    public Map<String, Object> wxLogin(@RequestParam("code") String code) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", 200);
        String appid = "wx700148023850c94e";
        String secret = "88ae3b881bed4b94aacd8831037d9262";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="
                + appid
                + "&secret=" + secret
                + "&js_code=" + code
                + "&grant_type=authorization_code";
        String resData = this.restTemplate.getForObject(url, String.class);
        if (StringUtils.contains(resData, "errcode")) {
            // 登录失败
            result.put("status", 500);
            result.put("msg", "登录失败");
            return result;
        }
        // 发给客户端的认证 key
        String ticket = "HAOKE_" + DigestUtils.md5Hex(resData);
        String redisKey = "WX_LOGIN_" + ticket;
        this.redisTemplate.opsForValue().set(redisKey, resData,
                Duration.ofDays(7));
        result.put("data", ticket);
        return result;
    }
}
