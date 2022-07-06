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
public class HouseResourcesListDataFetcher implements MyDataFetcher {
    @Autowired
    private HouseResourcesService houseResourcesService;

    @Override
    public String fieldName() {
        return "HouseResourcesList";
    }

    @Override
    public Object dataFetcher(DataFetchingEnvironment environment) {
        Integer page = environment.getArgument("page");
        if (page == null) {
            page = 1;
        }
        Integer pageSize = environment.getArgument("pageSize");
        if (pageSize == null) {
            pageSize = 5;
        }
        return this.houseResourcesService.queryList(null, page, pageSize);
    }
}
