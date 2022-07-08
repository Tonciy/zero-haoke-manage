package cn.zeroeden.haoke.dubbo.api.graphql;

import cn.zeroeden.haoke.dubbo.api.service.AdService;
import cn.zeroeden.haoke.dubbo.api.vo.ad.index.IndexAdResult;
import cn.zeroeden.haoke.dubbo.api.vo.ad.index.IndexAdResultData;
import cn.zeroeden.haoke.dubbo.server.pojo.Ad;
import cn.zeroeden.haoke.dubbo.server.vo.PageInfo;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zero
 * @Description 描述此类
 */
@Component
public class IndexAdDataFetcher implements MyDataFetcher {
    @Autowired
    private AdService adService;

    @Override
    public String fieldName() {
        return "IndexAdList";
    }

    @Override
    public Object dataFetcher(DataFetchingEnvironment environment) {
        PageInfo<Ad> pageInfo = this.adService.queryAdList(1, 1, 3);
        List<Ad> ads = pageInfo.getRecords();
        List<IndexAdResultData> list = new ArrayList<>();
        for (Ad ad : ads) {
            list.add(new IndexAdResultData(ad.getUrl()));
        }
        return new IndexAdResult(list);
    }
}
