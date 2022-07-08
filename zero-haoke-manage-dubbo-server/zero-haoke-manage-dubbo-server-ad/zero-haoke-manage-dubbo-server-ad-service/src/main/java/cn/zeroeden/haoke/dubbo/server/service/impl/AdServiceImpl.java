package cn.zeroeden.haoke.dubbo.server.service.impl;

import cn.zeroeden.haoke.dubbo.server.pojo.Ad;
import cn.zeroeden.haoke.dubbo.server.service.AdService;
import cn.zeroeden.haoke.dubbo.server.service.BaseServiceImpl;
import cn.zeroeden.haoke.dubbo.server.vo.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;

@Service
public class AdServiceImpl extends BaseServiceImpl implements AdService {
    @Override
    public PageInfo<Ad> queryAdList(Ad ad, Integer page, Integer pageSize) {
        QueryWrapper<Ad> adQueryWrapper = new QueryWrapper<>();
        // 根据时间排序
        adQueryWrapper.orderByDesc("updated");
        adQueryWrapper.eq("type",ad.getType());
        IPage iPage = super.queryPageList(adQueryWrapper, page, pageSize);
        return new PageInfo<Ad>(Long.valueOf(iPage.getTotal()).intValue(), page, pageSize, iPage.getRecords());
    }
}