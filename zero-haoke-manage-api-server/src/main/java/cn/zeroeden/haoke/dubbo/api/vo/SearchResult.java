package cn.zeroeden.haoke.dubbo.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


/**
 * 搜索框搜索结果分页存储类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {
    private Integer totalPage;
    private List<HouseData> list;
    /**
     * 装载热搜词条
     */
    private Set<String> hotWord;
}