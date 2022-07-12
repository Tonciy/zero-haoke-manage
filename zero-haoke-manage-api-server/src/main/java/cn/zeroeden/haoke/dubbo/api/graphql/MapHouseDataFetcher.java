package cn.zeroeden.haoke.dubbo.api.graphql;

import cn.zeroeden.haoke.dubbo.api.service.MongoHouseService;
import cn.zeroeden.haoke.dubbo.api.vo.map.MapHouseDataResult;
import cn.zeroeden.haoke.dubbo.api.vo.map.MapHouseXY;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapHouseDataFetcher implements MyDataFetcher {
    @Autowired
    private MongoHouseService mongoHouseService;
    @Override
    public String fieldName() {
        return "MapHouseData";
    }

    @Override
    public Object dataFetcher(DataFetchingEnvironment environment) {
        Float lng = ((Double)environment.getArgument("lng")).floatValue();
        Float lat = ((Double)environment.getArgument("lat")).floatValue();
        Integer zoom = environment.getArgument("zoom");
        System.out.println("lng->" + lng + ",lat->" + lat + ",zoom->" + zoom);
        return this.mongoHouseService.queryHouseData(lng,lat,zoom);
    }
}