package cn.zeroeden.haoke.dubbo.api.service;

import cn.zeroeden.haoke.dubbo.api.vo.Pagination;
import cn.zeroeden.haoke.dubbo.api.vo.TableResult;
import cn.zeroeden.haoke.dubbo.server.api.ApiHouseResourcesService;
import cn.zeroeden.haoke.dubbo.server.pojo.HouseResources;
import cn.zeroeden.haoke.dubbo.server.vo.PageInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class HouseResourcesService {
    @Reference(version = "1.0.0")
    private ApiHouseResourcesService apiHouseResourcesService;

    public boolean save(HouseResources houseResources) {
        int result =
                this.apiHouseResourcesService.saveHouseResources(houseResources);
        return result == 1;
    }

    public TableResult<HouseResources> queryList(HouseResources houseResources, Integer currentPage, Integer pageSize) {
        PageInfo<HouseResources> pageInfo = this.apiHouseResourcesService
                .queryHouseResourcesList(currentPage, pageSize, houseResources);
        return new TableResult<HouseResources>(pageInfo.getRecords()
                , new Pagination(currentPage, pageSize, pageInfo.getTotal()));
    }

    public HouseResources queryById(Long id) {
        return apiHouseResourcesService.queryHouseResourcesById(id);
    }

    public boolean update(HouseResources houseResources) {
        return this.apiHouseResourcesService.updateHouseResources(houseResources);
    }
}