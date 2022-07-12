package cn.zeroeden.haoke.dubbo.api.controller;

import cn.zeroeden.haoke.dubbo.api.conf.MockConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Zero
 * @Description 描述此类
 */
@RestController
@RequestMapping("mock")
@CrossOrigin
public class MockController {
    @Autowired
    private MockConfig mockConfig;

    /**
     * 菜单
     *
     * @return
     */
    @GetMapping("index/menu")
    @CrossOrigin
    public String indexMenu() {
        String indexMenu = this.mockConfig.getIndexMenu();
        System.out.println(indexMenu);
        return indexMenu;
    }

    /**
     * 首页资讯
     *
     * @return
     */
    @GetMapping("index/info")
    public String indexInfo() {
        String indexInfo = this.mockConfig.getIndexInfo();
//        System.out.println(indexInfo);
        return indexInfo;
    }

    /**
     * 首页问答
     *
     * @return
     */
    @GetMapping("index/faq")
    public String indexFaq() {
        return this.mockConfig.getIndexFaq();
    }

    /**
     * 首页房源信息
     *
     * @return
     */
    @GetMapping("index/house")
    public String indexHouse() {
        System.out.println(this.mockConfig.getIndexHouse());
        return this.mockConfig.getIndexHouse();
    }

    /**
     * 查询资讯
     *
     * @param type
     * @return
     */
    @GetMapping("infos/list")
    public String infosList(@RequestParam("type") Integer type) {
        switch (type) {
            case 1:
                return this.mockConfig.getInfosList1();
            case 2:
                return this.mockConfig.getInfosList2();
            case 3:
                return this.mockConfig.getInfosList3();
        }
        return this.mockConfig.getInfosList1();
    }

    /**
     * 我的中心
     *
     * @return
     */
    @GetMapping("my/info")
    public String myInfo() {
        return this.mockConfig.getMy();
    }
}
