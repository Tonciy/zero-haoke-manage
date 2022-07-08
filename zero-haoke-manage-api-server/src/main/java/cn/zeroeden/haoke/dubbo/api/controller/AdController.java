package cn.zeroeden.haoke.dubbo.api.controller;

import cn.zeroeden.haoke.dubbo.api.service.AdService;
import cn.zeroeden.haoke.dubbo.api.vo.WebResult;
import cn.zeroeden.haoke.dubbo.server.pojo.Ad;
import cn.zeroeden.haoke.dubbo.server.vo.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zero
 * @Description 描述此类
 */
@RestController
@RequestMapping("ad")
@CrossOrigin
public class AdController {
    @Autowired
    private AdService adService;
    /**
     * 首页广告位
     * @return
     */
    @GetMapping
    public WebResult queryIndexAd() {
        PageInfo<Ad> pageInfo = this.adService.queryAdList(1, 1, 3);
        List<Ad> ads = pageInfo.getRecords();
        List<Map<String,Object>> data = new ArrayList<>();
        for (Ad ad : ads) {
            Map<String,Object> map = new HashMap<>();
            map.put("original", ad.getUrl());
            data.add(map);
        }
        return WebResult.ok(data);
    }
}
