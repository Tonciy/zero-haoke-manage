package cn.zeroeden.haoke.dubbo.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 搜索框搜索结果封装实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "haoke", type = "house", createIndex = false)
public class HouseData {
    @Id
    private String id;
    private String title;
    private String rent;
    private String floor;
    private String image;
    private String orientation;
    private String houseType;
    private String rentMethod;
    private String time;
}