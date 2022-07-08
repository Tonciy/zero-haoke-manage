package cn.zeroeden.haoke.dubbo.server.service;

import cn.zeroeden.haoke.dubbo.server.pojo.Ad;
import cn.zeroeden.haoke.dubbo.server.vo.PageInfo;

public interface AdService {
    PageInfo<Ad> queryAdList(Ad ad, Integer page, Integer pageSize);
}