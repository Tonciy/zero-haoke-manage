package cn.zeroeden.haoke.dubbo.api.service;

import cn.zeroeden.haoke.dubbo.server.api.ApiAdService;
import cn.zeroeden.haoke.dubbo.server.pojo.Ad;
import cn.zeroeden.haoke.dubbo.server.vo.PageInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import javax.jws.WebResult;

@Service
public class AdService {
    @Reference(version = "1.0.0")
    private ApiAdService apiAdService;

    public PageInfo<Ad> queryAdList(Integer type, Integer page, Integer pageSize) {
        PageInfo<Ad> adPageInfo = this.apiAdService.queryAdList(type, page,
                pageSize);
        return adPageInfo;
    }
}