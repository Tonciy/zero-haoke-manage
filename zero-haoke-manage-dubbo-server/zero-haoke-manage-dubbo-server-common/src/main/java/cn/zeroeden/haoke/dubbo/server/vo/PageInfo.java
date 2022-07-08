package cn.zeroeden.haoke.dubbo.server.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author Zero
 * @Description 描述此类
 */
@Data
@AllArgsConstructor
public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = 9089045214197881837L;
    /**
     * 总条数
     */
    private Integer total;
    /**
     * 当前页
     */
    private Integer pageNum;
    /**
     * 一页显示的大小
     */
    private Integer pageSize;
    /**
     * 数据列表
     */
    private List<T> records = Collections.emptyList();
}
