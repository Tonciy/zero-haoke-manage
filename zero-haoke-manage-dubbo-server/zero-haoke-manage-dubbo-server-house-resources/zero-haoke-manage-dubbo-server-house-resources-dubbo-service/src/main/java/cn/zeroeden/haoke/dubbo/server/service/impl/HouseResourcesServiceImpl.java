package cn.zeroeden.haoke.dubbo.server.service.impl;

import cn.zeroeden.haoke.dubbo.server.pojo.HouseResources;
import cn.zeroeden.haoke.dubbo.server.service.BaseServiceImpl;
import cn.zeroeden.haoke.dubbo.server.service.HouseResourcesService;
import cn.zeroeden.haoke.dubbo.server.vo.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service //这是一个Spring的服务
@Transactional //开启事务
public class HouseResourcesServiceImpl extends BaseServiceImpl<HouseResources> implements HouseResourcesService {
    @Override
    public int saveHouseResources(HouseResources houseResources) {
        // 编写校验逻辑，如果教研不通过，返回-1
        if (StringUtils.isBlank(houseResources.getTitle())) {
            return -1;
        }
        // 其他校验以及逻辑省略 ……
        return super.save(houseResources);
    }

    @Override
    public PageInfo<HouseResources> queryHouseResourcesList(int page, int pageSize, HouseResources queryCondition) {
        QueryWrapper<HouseResources> queryWrapper = new QueryWrapper<>(queryCondition);
        // 根据时间降序排序
        queryWrapper.orderByDesc("updated");
        IPage iPage = super.queryPageList(queryWrapper, page, pageSize);
        return new PageInfo<HouseResources>
                (Long.valueOf(iPage.getTotal()).intValue(), page, pageSize, iPage.getRecords());
    }

    @Override
    public HouseResources queryHouseResourcesById(Long id) {
        return super.queryById(id);
    }

    @Override
    public boolean updateHouseResources(HouseResources houseResources) {
        return super.update(houseResources) == 1;
    }
}