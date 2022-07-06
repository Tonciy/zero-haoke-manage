package cn.zeroeden.haoke.dubbo.api.graphql;

import graphql.schema.DataFetchingEnvironment;

/**
 * @author Zero
 * @Description 描述此类
 */
public interface MyDataFetcher {
    /**
     * 查询名称
     *
     * @return
     */
    String fieldName();
    /**
     * 具体实现数据查询的逻辑
     *
     * @param environment
     * @return
     */
    Object dataFetcher(DataFetchingEnvironment environment);
}
