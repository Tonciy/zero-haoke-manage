package cn.zeroeden.haoke.dubbo.api.controller;

import cn.zeroeden.haoke.dubbo.api.service.SearchService;
import cn.zeroeden.haoke.dubbo.api.vo.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 搜索框查询接口
 */
@RequestMapping("search")
@RestController
@CrossOrigin
@Slf4j
public class SearchController {
    @Autowired
    private SearchService searchService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping
    public SearchResult search(@RequestParam("keyWord") String keyWord,
                               @RequestParam(value = "page", defaultValue = "1")
                                       Integer page) {
        // 防止爬虫爬取全部数据 以及 防止 ES 做深度分页造成性能问题
        if (page > 100) {
            page = 1;
        }
        SearchResult search = this.searchService.search(keyWord, page);
        // 搜索框热搜词条
        String redisKey = "HAOKE_HOT_WORD";
        // 当查无数据 或者 数据量少于 1 页时装载搜索热词
        if (search.getTotalPage() <= 1) {
            Set<String> set = stringRedisTemplate.opsForZSet().reverseRange(redisKey, 0, 4);
            search.setHotWord(set);
        }
        // 处理热词--自定义热词搜索频度统计公式
        Integer count = ((Math.max(search.getTotalPage(), 1) - 1) * SearchService.ROWS) + search.getList().size();
        // 加入 Redis ，动态数据方便后面取
        this.stringRedisTemplate.opsForZSet().add(redisKey, keyWord, count);
        // 记录日志
        log.info("[Search]搜索关键字为：" + keyWord + ",结果数量为：" + count);
        return search;
    }
}