package cn.zeroeden.haoke.dubbo.server.api;

import cn.zeroeden.haoke.dubbo.server.pojo.HouseResources;
import cn.zeroeden.haoke.dubbo.server.vo.PageInfo;

public interface ApiHouseResourcesService {
    /**
     * @param houseResources
     * @return -1:输入的参数不符合要求，0：数据插入数据库失败，1：成功
     */
    int saveHouseResources(HouseResources houseResources);

    /**
     * 分页查询房源列表
     *
     * @param page           当前页
     * @param pageSize       页面大小
     * @param queryCondition 查询条件
     * @return
     **/
    PageInfo<HouseResources> queryHouseResourcesList(int page, int pageSize, HouseResources queryCondition);

    /**
     * 根据id查找房源数据
     *
     * @param id
     * @return
     */
    HouseResources queryHouseResourcesById(Long id);

    /**
     * 根据id修改房源信息
     * @param houseResources
     * @return
     */
    boolean updateHouseResources(HouseResources houseResources);
}