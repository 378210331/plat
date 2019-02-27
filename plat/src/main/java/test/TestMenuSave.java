package test;

import com.hsy.platform.plugin.PageData;
import com.hsy.platform.service.MenuService;
import org.junit.Test;

import javax.annotation.Resource;

public class TestMenuSave extends BaseTest {


    @Resource
    MenuService menuService;

    @Test
    public void save() throws Exception {
        PageData pd = new PageData();
        pd.addParam("menuId","212156");
        pd.addParam("menuName","测试");
        pd.addParam("menuType","0");
        menuService.save(pd);
    }
}
