package cn.zeroeden.haoke.dubbo.api.service;

import cn.zeroeden.haoke.dubbo.server.api.ApiHouseResourcesService;
import cn.zeroeden.haoke.dubbo.server.pojo.HouseResources;
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
}