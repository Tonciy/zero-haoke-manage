package cn.zeroeden.haoke.dubbo.api.service;

import cn.zeroeden.haoke.dubbo.api.vo.HouseData;
import cn.zeroeden.haoke.dubbo.api.vo.SearchResult;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Zero
 * @Description 描述此类
 */
@Service
public class SearchService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 默认每页 10 条数据，注意这个不能由前端控制
     */
    public static final Integer ROWS = 10;

    /**
     * 根据房子名称关键词 查询房源信息
     *
     * @param keyWord
     * @param page
     * @return
     */
    public SearchResult search(String keyWord, Integer page) {
        PageRequest pageRequest = PageRequest.of(page - 1, ROWS); //设置分页参数

        //  构造查询条件
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyWord, "title", "title.pinyin").operator(Operator.AND))
                .withPageable(pageRequest)
                .withHighlightFields(new HighlightBuilder.Field("title")) // 设置高亮
                .build();

        // 自定义结果高亮封装--默认不会对查询字段进行高亮封装
        AggregatedPage<HouseData> housePage =
                this.elasticsearchTemplate.queryForPage(searchQuery,
                        HouseData.class, new SearchResultMapper() {
                            @Override
                            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                                List<T> result = new ArrayList<>();
                                if (response.getHits().totalHits == 0) {
                                    return new AggregatedPageImpl<>
                                            (Collections.emptyList(), pageable, 0L);
                                }
                                for (SearchHit searchHit : response.getHits()) {
// 通过反射写入数据到对象中
                                    T obj = (T) ReflectUtils.newInstance(clazz);
                                    try {
//写入id
                                        FieldUtils.writeField(obj, "id",
                                                searchHit.getId(), true);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                    Map<String, Object> sourceAsMap =
                                            searchHit.getSourceAsMap();
                                    for (Map.Entry<String, Object> entry :
                                            sourceAsMap.entrySet()) {
                                        Field field = FieldUtils.getField(clazz,
                                                entry.getKey(), true);
                                        if (null == field) {
                                            continue;
                                        }
                                        try {
                                            FieldUtils.writeField(obj, entry.getKey(),
                                                    entry.getValue(), true);
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    // 处理高亮
                                    for (Map.Entry<String, HighlightField>
                                            stringHighlightFieldEntry : searchHit.getHighlightFields().entrySet()) {
                                        try {
                                            Text[] fragments =
                                                    stringHighlightFieldEntry.getValue().fragments();
                                            StringBuilder sb = new StringBuilder();
                                            for (Text fragment : fragments) {
                                                sb.append(fragment.toString());
                                            }
                                            FieldUtils.writeField(obj,
                                                    stringHighlightFieldEntry.getKey(), sb.toString(), true);
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    result.add(obj);
                                }
                                return new AggregatedPageImpl<>(result, pageable,
                                        response.getHits().totalHits);
                            }
                        });
        return new SearchResult(housePage.getTotalPages(),
                housePage.getContent(), null);
    }
}



