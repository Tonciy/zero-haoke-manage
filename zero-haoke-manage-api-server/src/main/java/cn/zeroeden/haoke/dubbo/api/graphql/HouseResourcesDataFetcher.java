package cn.zeroeden.haoke.dubbo.api.graphql;

import cn.zeroeden.haoke.dubbo.api.service.HouseResourcesService;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Zero
 * @Description 描述此类
 */
@Component
public class HouseResourcesDataFetcher implements MyDataFetcher {
    @Autowired
    private HouseResourcesService houseResourcesService;

    @Override
    public String fieldName() {
        return "HouseResources";
    }

    @Override
    public Object dataFetcher(DataFetchingEnvironment environment) {
        Long id = environment.getArgument("id");
        return this.houseResourcesService.queryById(id);
    }
}
